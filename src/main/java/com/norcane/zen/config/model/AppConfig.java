package com.norcane.zen.config.model;

import com.norcane.zen.data.Mergeable;
import com.norcane.zen.meta.SemVer;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.soabase.recordbuilder.core.RecordBuilder;

import static com.norcane.zen.data.MergeStrategies.concatLists;
import static com.norcane.zen.data.MergeStrategies.latter;

/**
 * Represents application configuration.
 */
@RecordBuilder()
@RecordBuilder.Options(addClassRetainedGenerated = true, inheritComponentAnnotations = false)
public record AppConfig(@NotNull SemVer baseVersion,
                        @NotNull List<TemplateMapping> templateMappings)
    implements Mergeable<AppConfig>, AppConfigBuilder.With {

    @Override
    public AppConfig merge(AppConfig other) {
        return new AppConfig(latter(this.baseVersion(), other.baseVersion()),
                             concatLists(this.templateMappings(), other.templateMappings()));
    }
}