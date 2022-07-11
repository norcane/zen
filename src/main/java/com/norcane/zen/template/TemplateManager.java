package com.norcane.zen.template;

import com.norcane.zen.base.Memoizable;
import com.norcane.zen.config.AppConfigManager;
import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.ResourceManager;
import com.norcane.zen.support.LanguageSupportManager;
import com.norcane.zen.template.exception.DuplicateTemplatesFoundException;
import com.norcane.zen.ui.Console;

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
    private final Console console;
    private final LanguageSupportManager languageSupportManager;
    private final Map<String, TemplateFactory> extensionToFactory;
    private final ResourceManager resourceManager;

    private Map<String, Template> templates = new HashMap<>();

    @Inject
    public TemplateManager(final AppConfigManager appConfigManager,
                           final Console console,
                           final LanguageSupportManager languageSupportManager,
                           final Instance<TemplateFactory> extensionToFactory,
                           final ResourceManager resourceManager) {

        this.appConfigManager = Objects.requireNonNull(appConfigManager);
        this.console = Objects.requireNonNull(console);
        this.languageSupportManager = Objects.requireNonNull(languageSupportManager);
        this.extensionToFactory = extensionToFactory(Objects.requireNonNull(extensionToFactory));
        this.resourceManager = Objects.requireNonNull(resourceManager);
    }

    public Set<String> managedTemplateExtensions() {
        return extensionToFactory.keySet();
    }

    public Map<String, Template> templates() {
        if (templates.isEmpty()) {
            final List<String> locations = appConfigManager.finalConfig().templates();
            final Set<String> templateNames = languageSupportManager.managedLanguageNames();
            final Set<String> templateExtensions = managedTemplateExtensions();

            console.print("Loading templates from: @|bold %s |@".formatted(locations));

            final Predicate<Resource> filter = resource ->
                templateNames.contains(resource.name()) && templateExtensions.contains(resource.type());

            final Map<String, List<Resource>> foundTemplates = locations.stream()
                .map(location -> resourceManager.resources(location, filter))
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(Resource::name));

            this.templates = foundTemplates.entrySet().stream()
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    entry -> {
                        if (entry.getValue().size() > 1) {
                            final List<String> paths = entry.getValue().stream().map(Resource::location).toList();
                            throw new DuplicateTemplatesFoundException(entry.getKey(), paths);
                        }

                        final Resource template = entry.getValue().get(0);
                        return extensionToFactory.get(template.type()).compile(template);
                    }
                ));

            console.print("Registered @|bold %d|@ template(s)".formatted(templates.size()));
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
