package com.norcane.zen.config.yaml.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.norcane.zen.meta.SemVer;

public record YamlAppConfig(
    @JsonProperty("min-compatible-version") SemVer minCompatibleVersion
) {
}
