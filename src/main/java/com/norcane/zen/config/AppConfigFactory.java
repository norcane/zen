package com.norcane.zen.config;

import com.norcane.zen.meta.SemVer;

/**
 * Factory class for loading application configuration (see {@link AppConfig}).
 */
public interface AppConfigFactory {

    /**
     * Format of configuration (e.g. {@code YAML}).
     *
     * @return format of configuration
     */
    String getConfigFormat();

    /**
     * Source of the configuration (e.g. path to the {@code YAML} file).
     *
     * @return source of the configuration
     */
    String getConfigSource();

    /**
     * Returns minimum version of this application that is compatible with given configuration. This is needed to check whether to be loaded configuration is
     * compatible and can be loaded without any issues.
     *
     * @return minimum compatibility version
     */
    SemVer getMinCompatibleVersion();

    /**
     * Parses application configuration..
     *
     * @return parsed {@link AppConfig}
     * @throws com.norcane.zen.config.exception.ConfigParseException error parsing configuration
     */
    AppConfig parse();

}