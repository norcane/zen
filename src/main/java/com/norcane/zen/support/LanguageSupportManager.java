package com.norcane.zen.support;

import com.norcane.zen.resource.Resource;
import com.norcane.zen.source.SourceCode;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class LanguageSupportManager {

    private final Map<String, LanguageSupport> resourceTypeToSupport;
    private final Map<String, LanguageSupport> languageToSupport;

    @Inject
    public LanguageSupportManager(final Instance<LanguageSupport> supports) {
        Objects.requireNonNull(supports);

        this.resourceTypeToSupport = resourceTypeToSupport(supports);
        this.languageToSupport = languageToSupport(supports);
    }

    public SourceCode analyze(final Resource resource) {
        final LanguageSupport support = resourceTypeToSupport.get(resource.type());
        if (support == null) {
            throw new IllegalArgumentException("Unsupported resource type '%s': %s".formatted(resource.type(), resource));
        }

        return support.analyze(resource);
    }

    public Set<String> managedResourceTypes() {
        return resourceTypeToSupport.keySet();
    }

    public Set<String> managedLanguageNames() {
        return languageToSupport.keySet();
    }

    private static Map<String, LanguageSupport> resourceTypeToSupport(final Instance<LanguageSupport> supports) {
        return supports.stream()
            .flatMap(support -> support.resourceTypes().stream().map(extension -> Map.entry(extension, support)))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Map<String, LanguageSupport> languageToSupport(final Instance<LanguageSupport> supports) {
        return supports.stream()
            .collect(Collectors.toMap(LanguageSupport::name, Function.identity()));
    }
}
