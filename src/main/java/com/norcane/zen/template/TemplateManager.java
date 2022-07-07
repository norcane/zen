package com.norcane.zen.template;

import com.norcane.zen.config.AppConfigManager;
import com.norcane.zen.resource.ResourceManager;
import com.norcane.zen.support.LanguageSupportManager;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class TemplateManager {

    private final AppConfigManager appConfigManager;
    private final LanguageSupportManager languageSupportManager;
    private final Instance<TemplateFactory> factories;
    private final ResourceManager resourceManager;

    private Map<String, Template> templates = new HashMap<>();

    @Inject
    public TemplateManager(final AppConfigManager appConfigManager,
                           final LanguageSupportManager languageSupportManager,
                           final Instance<TemplateFactory> factories,
                           final ResourceManager resourceManager) {

        this.appConfigManager = Objects.requireNonNull(appConfigManager);
        this.languageSupportManager = Objects.requireNonNull(languageSupportManager);
        this.factories = Objects.requireNonNull(factories);
        this.resourceManager = Objects.requireNonNull(resourceManager);
    }

    public Set<String> managedTemplateExtensions() {
        return factories.stream()
            .map(TemplateFactory::fileExtensions)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
    }

    public Map<String, Template> templates() {
        if (templates.isEmpty()) {
            final List<String> locations = appConfigManager.finalConfig().templates();
            final Set<String> templateNames = languageSupportManager.managedLanguageNames();
            final Set<String> templateExtensions = managedTemplateExtensions();

            // todo implement
        }

        return Collections.unmodifiableMap(templates);
    }
}
