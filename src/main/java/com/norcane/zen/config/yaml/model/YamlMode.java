package com.norcane.zen.config.yaml.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public enum YamlMode {
    @JsonProperty("add") ADD,
    @JsonProperty("drop") DROP,
    @JsonProperty("replace") REPLACE,
}
