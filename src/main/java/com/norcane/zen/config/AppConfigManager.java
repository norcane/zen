package com.norcane.zen.config;


import com.norcane.zen.base.Memoizable;
import com.norcane.zen.config.exception.InvalidConfigurationException;
import com.norcane.zen.config.exception.MultipleConfigFilesFoundException;
import com.norcane.zen.config.exception.NoConfigFileFoundException;
import com.norcane.zen.config.model.AppConfig;
import com.norcane.zen.config.model.AppConfigRef;
import com.norcane.zen.meta.ProductInfo;
import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.ResourceManager;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

@ApplicationScoped
public class AppConfigManager implements Memoizable {

    private static final String DEFAULT_CONFIG_PATH = "classpath:/config/default-config.yaml";
    private static final String CONFIG_FILE_NAME = ProductInfo.NAME;

    private final Instance<AppConfigFactory> factories;
    private final ResourceManager resourceManager;
    private final Validator validator;

    private AppConfigRef defaultConfigRef;
    private AppConfigRef userConfigRef;
    private AppConfigRef finalConfigRef;

    @Inject
    public AppConfigManager(final Instance<AppConfigFactory> factories,
                            final ResourceManager resourceManager,
                            final Validator validator) {

        this.factories = Objects.requireNonNull(factories);
        this.resourceManager = Objects.requireNonNull(resourceManager);
        this.validator = Objects.requireNonNull(validator);
    }

    public AppConfigRef defaultConfigRef() {
        if (this.defaultConfigRef == null) {
            final String defaultConfigExtension = DEFAULT_CONFIG_PATH.substring(DEFAULT_CONFIG_PATH.lastIndexOf(".") + 1);
            final Resource resource = resourceManager.resource(DEFAULT_CONFIG_PATH);

            final AppConfig config = factories
                .stream()
                .filter(factory -> factory.fileExtension().equals(defaultConfigExtension))
                .map(factory -> factory.parse(resource))
                .findAny()
                .orElseThrow();

            this.defaultConfigRef = new AppConfigRef(config, resource);
        }

        return this.defaultConfigRef;
    }

    public AppConfigRef userConfigRef() {
        if (this.userConfigRef == null) {
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
            final Resource resource = resourceManager.resource(userConfigPath(factory.fileExtension()));

            // TODO config compatibility check

            this.userConfigRef = new AppConfigRef(factory.parse(resource), resource);
        }

        return this.userConfigRef;
    }

    public AppConfigRef finalConfigRef() {
        if (this.finalConfigRef == null) {
            final AppConfigRef defaultConfigRef = defaultConfigRef();
            final AppConfigRef userConfigRef = userConfigRef();
            final AppConfig mergedConfig = defaultConfigRef.config().merge(userConfigRef.config());

            final Set<ConstraintViolation<AppConfig>> violations = validator.validate(mergedConfig);
            if (!violations.isEmpty()) {
                throw new InvalidConfigurationException(violations);
            }

            this.finalConfigRef = new AppConfigRef(mergedConfig, userConfigRef.resource());
        }

        return this.finalConfigRef;
    }

    protected String userConfigPath(final String extension) {
        final String currentWorkingDir = System.getProperty("user.dir");

        return currentWorkingDir + File.separator + CONFIG_FILE_NAME + "." + extension;
    }

    @Override
    public void resetMemoizedState() {
        this.defaultConfigRef = null;
        this.userConfigRef = null;
        this.finalConfigRef = null;
    }
}
