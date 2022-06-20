package com.norcane.zen.config.yaml;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.norcane.zen.config.AppConfig;
import com.norcane.zen.config.AppConfigFactory;
import com.norcane.zen.config.exception.ConfigParseException;
import com.norcane.zen.config.exception.MissingConfigVersionException;
import com.norcane.zen.meta.SemVer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class YamlAppConfigFactory implements AppConfigFactory {

    private static final String CONFIG_FORMAT = "YAML";
    private static final String FILENAME = "zen.yaml";

    private static final File configFile = new File(System.getProperty("user.dir"), FILENAME);

    private SemVer configVersion;

    @Override
    public String getConfigFormat() {
        return CONFIG_FORMAT;
    }

    @Override
    public String getConfigSource() {
        return configFile.toString();
    }

    @Override
    public SemVer getMinCompatibleVersion() {
        if (configVersion == null) {
            final VersionObject versionObject;
            try {
                final ObjectMapper objectMapper = objectMapper();

                versionObject = objectMapper.readValue(configFileReader(), VersionObject.class);
            } catch (Throwable t) {
                t.printStackTrace();
                throw new ConfigParseException(getConfigSource(), t);
            }

            if (versionObject.version() == null) {
                throw new MissingConfigVersionException(getConfigSource());
            }
            configVersion = versionObject.version();
        }

        return configVersion;
    }

    @Override
    public AppConfig parse() {
        try {
            final ObjectMapper objectMapper = objectMapper();
            return objectMapper.readValue(configFileReader(), AppConfig.class);
        } catch (Throwable t) {
            throw new ConfigParseException(getConfigSource(), t);
        }
    }

    protected Reader configFileReader() {
        try {
            return new FileReader(configFile);
        } catch (IOException e) {
            throw new ConfigParseException(configFile.toString(), e);
        }
    }

    private ObjectMapper objectMapper() {

        final SimpleModule module = new SimpleModule()
            .addDeserializer(SemVer.class, new SemVerDeserializer());

        return new ObjectMapper(new YAMLFactory())
            .registerModule(module)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public record VersionObject(SemVer version) {

    }
}
