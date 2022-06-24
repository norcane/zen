package com.norcane.zen.config.yaml.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.norcane.zen.config.data.AppConfig;
import com.norcane.zen.meta.SemVer;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record YamlAppConfig(
    @JsonProperty("min-compatible-version") SemVer minCompatibleVersion
) implements AppConfig {
}
