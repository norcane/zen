package com.norcane.zen.config.model;

import com.norcane.zen.data.Mergeable;
import com.norcane.zen.meta.SemVer;

import javax.validation.constraints.NotNull;

import io.soabase.recordbuilder.core.RecordBuilder;

/**
 * Represents application configuration.
 */
@RecordBuilder()
@RecordBuilder.Options(addClassRetainedGenerated = true, inheritComponentAnnotations = false)
public record AppConfig(@NotNull SemVer baseVersion)
    implements Mergeable<AppConfig>, AppConfigBuilder.With {

    @Override
    public AppConfig merge(AppConfig other) {
        return new AppConfig(other.baseVersion() != null ? other.baseVersion() : this.baseVersion());
    }
}