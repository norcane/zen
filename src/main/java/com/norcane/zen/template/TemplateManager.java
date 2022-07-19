package com.norcane.zen.template;

import com.norcane.zen.base.Memoizable;
import com.norcane.zen.config.AppConfigManager;
import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.ResourceManager;
import com.norcane.zen.support.LanguageSupportManager;
import com.norcane.zen.template.exception.DuplicateTemplatesFoundException;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class TemplateManager implements Memoizable {

    private final AppConfigManager appConfigManager;
    private final LanguageSupportManager languageSupportManager;
    private final Map<String, TemplateFactory> extensionToFactory;
    private final ResourceManager resourceManager;

    private final Map<String, Template> templates = new HashMap<>();

    @Inject
    public TemplateManager(final AppConfigManager appConfigManager,
                           final LanguageSupportManager languageSupportManager,
                           final Instance<TemplateFactory> extensionToFactory,
                           final ResourceManager resourceManager) {

        this.appConfigManager = Objects.requireNonNull(appConfigManager);
        this.languageSupportManager = Objects.requireNonNull(languageSupportManager);
        this.extensionToFactory = extensionToFactory(Objects.requireNonNull(extensionToFactory));
        this.resourceManager = Objects.requireNonNull(resourceManager);
    }

    public Set<String> managedTemplateExtensions() {
        return extensionToFactory.keySet();
    }

    public List<String> templatePaths() {
        return Collections.unmodifiableList(appConfigManager.finalConfig().templates());
    }

    public Map<String, Template> templates() {
        if (templates.isEmpty()) {
            final List<String> locations = templatePaths();
            final Set<String> templateNames = languageSupportManager.managedLanguageNames();
            final Set<String> templateExtensions = managedTemplateExtensions();

            final Predicate<Resource> filter = resource ->
                templateNames.contains(resource.name()) && templateExtensions.contains(resource.type());

            final Map<String, List<Resource>> foundTemplates = locations.stream()
                .map(location -> resourceManager.resources(location, filter))
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(Resource::name));

            foundTemplates.forEach((key, value) -> {
                if (value.size() > 1) {
                    final List<String> paths = value.stream().map(Resource::location).toList();
                    throw new DuplicateTemplatesFoundException(key, paths);
                }

                final Resource template = value.get(0);
                templates.put(key, extensionToFactory.get(template.type()).compile(template));
            });
        }

        return Collections.unmodifiableMap(templates);
    }

    @Override
    public void resetMemoizedState() {
        this.templates.clear();
    }

    private static Map<String, TemplateFactory> extensionToFactory(final Instance<TemplateFactory> factories) {
        return factories.stream()
            .collect(Collectors.toMap(TemplateFactory::templateType, templateFactory -> templateFactory));
    }
}
