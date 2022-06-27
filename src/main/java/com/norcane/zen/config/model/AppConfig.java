package com.norcane.zen.config.model;

import com.norcane.zen.data.Mergeable;
import com.norcane.zen.meta.SemVer;

import javax.validation.constraints.NotNull;

/**
 * Represents application configuration.
 */
public record AppConfig(@NotNull SemVer baseVersion) implements Mergeable<AppConfig> {

    @Override
    public AppConfig merge(AppConfig other) {
        return new AppConfig(other.baseVersion() != null ? other.baseVersion() : this.baseVersion());
    }
}