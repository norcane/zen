package com.norcane.zen.config;

import com.norcane.zen.config.model.AppConfig;
import com.norcane.zen.meta.SemVer;

import java.io.Reader;

/**
 * Factory class for loading application configuration from given source format (e.g. {@code YAML} files).
 */
public interface AppConfigFactory {

    /**
     * File extension used for given configuration source format (e.g. {@code .yaml}.
     *
     * @return file extension
     */
    String fileExtension();

    /**
     * Returns minimum version of this application that is compatible with given configuration. This is needed to check whether to be loaded configuration is
     * compatible and can be loaded without any issues.
     *
     * @param source configuration source
     * @param sourceName name of configuration source
     * @return minimum compatibility version
     * @throws com.norcane.zen.config.exception.MissingConfigVersionException configuration version not found in source
     * @throws com.norcane.zen.config.exception.ConfigParseException error parsing configuration source
     */
    SemVer minCompatibleVersion(Reader source, String sourceName);

    /**
     * Parses application configuration from given source.
     *
     * @param source configuration source
     * @param sourceName name of configuration source
     * @return parsed configuration
     * @throws com.norcane.zen.config.exception.ConfigParseException error parsing configuration source
     */
    AppConfig parse(Reader source, String sourceName);
}