package com.norcane.zen.support;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class LanguageSupportManager {

    private final Instance<LanguageSupport> supports;

    @Inject
    public LanguageSupportManager(final Instance<LanguageSupport> supports) {
        this.supports = Objects.requireNonNull(supports);
    }

    public Set<String> managedFileExtensions() {
        return supports.stream()
            .map(LanguageSupport::fileExtensions)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
    }

    public Set<String> managedLanguageNames() {
        return supports.stream()
            .map(LanguageSupport::name)
            .collect(Collectors.toSet());
    }
}
