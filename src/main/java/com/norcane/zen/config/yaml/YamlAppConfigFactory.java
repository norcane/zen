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

import java.io.Reader;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class YamlAppConfigFactory implements AppConfigFactory {

    private static final String FILE_EXTENSION = "yaml";

    @Override
    public String fileExtension() {
        return FILE_EXTENSION;
    }

    @Override
    public SemVer minCompatibleVersion(Reader source, String sourceName) {
        final VersionWrapper wrapper;
        try {
            wrapper = objectMapper().readValue(source, VersionWrapper.class);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ConfigParseException(sourceName, t);
        }

        if (wrapper.version() == null) {
            throw new MissingConfigVersionException(sourceName);
        }

        return wrapper.version();
    }

    @Override
    public AppConfig parse(Reader source, String sourceName) {
        try {
            final ObjectMapper objectMapper = objectMapper();
            return objectMapper.readValue(source, AppConfig.class);
        } catch (Throwable t) {
            throw new ConfigParseException(sourceName, t);
        }
    }

    private ObjectMapper objectMapper() {
        final SimpleModule module = new SimpleModule()
            .addDeserializer(SemVer.class, new SemVerDeserializer());

        return new ObjectMapper(new YAMLFactory())
            .registerModule(module)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    protected record VersionWrapper(SemVer version) {
    }
}
