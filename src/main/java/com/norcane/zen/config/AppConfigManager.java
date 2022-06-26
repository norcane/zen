package com.norcane.zen.config;


import com.norcane.zen.config.exception.InvalidConfigurationException;
import com.norcane.zen.config.exception.MultipleConfigFilesFoundException;
import com.norcane.zen.config.exception.NoConfigFileFoundException;
import com.norcane.zen.config.model.AppConfig;
import com.norcane.zen.meta.ProductInfo;
import com.norcane.zen.ui.Console;

import org.jboss.logging.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

@ApplicationScoped
public class AppConfigManager {

    private static final Logger logger = Logger.getLogger(AppConfigManager.class);

    private static final String DEFAULT_CONFIG_PATH = "/config/default-config.yaml";
    private static final String CONFIG_FILE_NAME = ProductInfo.NAME;

    private final Instance<AppConfigFactory> factories;
    private final Console console;
    private final Validator validator;

    private AppConfig defaultConfig;
    private AppConfig userConfig;
    private AppConfig finalConfig;

    @Inject
    public AppConfigManager(final Instance<AppConfigFactory> factories,
                            final Console console,
                            final Validator validator) {

        this.factories = Objects.requireNonNull(factories);
        this.console = Objects.requireNonNull(console);
        this.validator = Objects.requireNonNull(validator);
    }

    public AppConfig defaultConfig() {
        if (defaultConfig == null) {
            final String defaultConfigExtension = DEFAULT_CONFIG_PATH.substring(DEFAULT_CONFIG_PATH.lastIndexOf(".") + 1);
            final InputStreamReader source = new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(DEFAULT_CONFIG_PATH)));

            defaultConfig = factories
                .stream()
                .filter(factory -> factory.fileExtension().equals(defaultConfigExtension))
                .map(factory -> factory.parse(source, DEFAULT_CONFIG_PATH))
                .findAny()
                .orElseThrow();
        }

        return defaultConfig;
    }

    public AppConfig userConfig() {
        if (userConfig == null) {
            final List<AppConfigFactory> availableFactories = factories
                .stream()
                .filter(factory -> new File(userConfigPath(factory.fileExtension())).isFile())
                .toList();

            // check that there is exactly one config file
            if (availableFactories.size() == 0) {
                final List<String> possibleConfigFilePaths = factories
                    .stream()
                    .map(factory -> userConfigPath(factory.fileExtension()))
                    .toList();

                throw new NoConfigFileFoundException(possibleConfigFilePaths);
            } else if (availableFactories.size() > 1) {
                final List<String> foundConfigFilePaths = availableFactories
                    .stream()
                    .map(factory -> userConfigPath(factory.fileExtension()))
                    .toList();

                throw new MultipleConfigFilesFoundException(foundConfigFilePaths);
            }

            final AppConfigFactory factory = availableFactories.get(0);
            final File configFile = new File(userConfigPath(factory.fileExtension()));

            // TODO config compatibility check

            try {
                userConfig = factory.parse(new FileReader(configFile), configFile.getAbsolutePath());
                console.print("Loaded user configuration file: " + configFile.getAbsolutePath());
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        return userConfig;
    }

    public AppConfig finalConfig() {
        if (finalConfig == null) {
            final AppConfig defaultConfig = defaultConfig();
            final AppConfig userConfig = userConfig();
            final AppConfig mergedConfig = defaultConfig.merge(userConfig);

            // FIXME validate config
            final Set<ConstraintViolation<AppConfig>> violations = validator.validate(mergedConfig);
            if (!violations.isEmpty()) {
                throw new InvalidConfigurationException(violations);
            }

            finalConfig = mergedConfig;
        }

        return finalConfig;
    }

    protected String userConfigPath(final String extension) {
        final String currentWorkingDir = System.getProperty("user.dir");

        return currentWorkingDir + File.separator + CONFIG_FILE_NAME + "." + extension;
    }
}
