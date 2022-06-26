package com.norcane.zen.config.yaml;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.norcane.zen.config.AppConfigFactory;
import com.norcane.zen.config.exception.ConfigParseException;
import com.norcane.zen.config.exception.MissingConfigVersionException;
import com.norcane.zen.config.model.AppConfig;
import com.norcane.zen.config.yaml.mapper.YamlAppConfigMapper;
import com.norcane.zen.config.yaml.model.VersionWrapper;
import com.norcane.zen.config.yaml.model.YamlAppConfig;
import com.norcane.zen.io.Resource;
import com.norcane.zen.meta.SemVer;

import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class YamlAppConfigFactory implements AppConfigFactory {

    private static final Logger logger = Logger.getLogger(YamlAppConfigFactory.class);

    private static final String FILE_EXTENSION = "yaml";

    private final YamlAppConfigMapper mapper;

    @Inject
    public YamlAppConfigFactory(YamlAppConfigMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public String fileExtension() {
        return FILE_EXTENSION;
    }

    @Override
    public SemVer minCompatibleVersion(Resource resource) {
        final VersionWrapper wrapper;
        try {
            wrapper = objectMapper().readValue(resource.readAsString(), VersionWrapper.class);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new ConfigParseException(resource.getPath(), t);
        }

        if (wrapper.minCompatibleVersion() == null) {
            throw new MissingConfigVersionException(resource.getPath());
        }

        return wrapper.minCompatibleVersion();
    }

    @Override
    public AppConfig parse(Resource resource) {
        logger.debug("Parsing YAML configuration from %s".formatted(resource.getPath()));

        try {
            final ObjectMapper objectMapper = objectMapper();
            return mapper.map(objectMapper.readValue(resource.readAsString(), YamlAppConfig.class));
        } catch (Throwable t) {
            throw new ConfigParseException(resource.getPath(), t);
        }
    }

    private ObjectMapper objectMapper() {
        final SimpleModule module = new SimpleModule()
            .addDeserializer(SemVer.class, new SemVerDeserializer());

        return new ObjectMapper(new YAMLFactory())
            .registerModule(module)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
