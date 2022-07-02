package com.norcane.zen.config.model;

import com.norcane.zen.data.Mergeable;
import com.norcane.zen.meta.SemVer;

import java.util.List;

import javax.validation.constraints.NotEmpty;
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
                        @NotEmpty List<String> templates,
                        @NotEmpty List<String> sources)
    implements Mergeable<AppConfig>, AppConfigBuilder.With {

    @Override
    public AppConfig merge(AppConfig that) {
        return new AppConfig(latter(this.baseVersion(), that.baseVersion()),
                             concatLists(this.templates(), that.templates()),
                             concatLists(this.sources(), that.sources()));
    }
}