package com.norcane.zen.config.model;

import com.norcane.zen.resource.Resource;

import io.soabase.recordbuilder.core.RecordBuilder;

/**
 * Reference to the {@link AppConfig} object, that also holds the {@link Resource} used to load the configuration.
 *
 * @param config   application configuration
 * @param resource resource used to load the configuration
 */
@RecordBuilder()
@RecordBuilder.Options(addClassRetainedGenerated = true, inheritComponentAnnotations = false)
public record AppConfigRef(AppConfig config, Resource resource) {
}
