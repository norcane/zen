package com.norcane.zen.config.yaml.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record YamlTemplateMapping(@JsonProperty("sources") List<String> sources,
                                  @JsonProperty("templates") List<String> templates) {
}
