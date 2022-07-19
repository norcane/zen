package com.norcane.zen.source;

import com.norcane.zen.base.Memoizable;
import com.norcane.zen.config.AppConfigManager;
import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.ResourceManager;
import com.norcane.zen.support.LanguageSupportManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class SourceCodeManager implements Memoizable {

    private final AppConfigManager appConfigManager;
    private final LanguageSupportManager languageSupportManager;
    private final ResourceManager resourceManager;

    private final List<SourceCode> sourceCodes = new ArrayList<>();

    @Inject
    public SourceCodeManager(final AppConfigManager appConfigManager,
                             final LanguageSupportManager languageSupportManager,
                             final ResourceManager resourceManager) {

        this.appConfigManager = Objects.requireNonNull(appConfigManager);
        this.languageSupportManager = Objects.requireNonNull(languageSupportManager);
        this.resourceManager = Objects.requireNonNull(resourceManager);
    }

    public List<String> sourceCodePaths() {
        return Collections.unmodifiableList(appConfigManager.finalConfig().sources());
    }

    public List<SourceCode> sourceCodes() {
        if (this.sourceCodes.isEmpty()) {
            final Set<String> languageNames = languageSupportManager.managedLanguageNames();
            final Predicate<Resource> filter = resource -> languageNames.contains(resource.type());

            sourceCodePaths().stream()
                .map(sourcePath -> resourceManager.resources(sourcePath, filter))
                .flatMap(Collection::stream)
                .map(languageSupportManager::analyze)
                .forEach(sourceCodes::add);
        }

        return Collections.unmodifiableList(this.sourceCodes);
    }

    @Override
    public void resetMemoizedState() {
        this.sourceCodes.clear();
    }
}
