package com.norcane.zen.source;

import com.norcane.zen.config.AppConfigManager;
import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.ResourceManager;
import com.norcane.zen.support.LanguageSupportManager;
import com.norcane.zen.template.TemplateManager;
import com.norcane.zen.ui.Console;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class SourceProcessor {

    private final AppConfigManager appConfigManager;
    private final Console console;
    private final LanguageSupportManager languageSupportManager;
    private final ResourceManager resourceManager;
    private final TemplateManager templateManager;

    @Inject
    public SourceProcessor(final AppConfigManager appConfigManager,
                           final Console console,
                           final LanguageSupportManager languageSupportManager,
                           final ResourceManager resourceManager,
                           final TemplateManager templateManager) {

        this.appConfigManager = Objects.requireNonNull(appConfigManager);
        this.console = Objects.requireNonNull(console);
        this.languageSupportManager = Objects.requireNonNull(languageSupportManager);
        this.resourceManager = Objects.requireNonNull(resourceManager);
        this.templateManager = Objects.requireNonNull(templateManager);
    }

    public void process() {
        // todo implement
        final List<SourceCode> sourceCodes = loadSourceCodes();
        console.print("Found @|bold %d|@ source code files to process".formatted(sourceCodes.size()));
    }

    protected List<SourceCode> loadSourceCodes() {
        final Set<String> languageNames = languageSupportManager.managedLanguageNames();
        final Predicate<Resource> filter = resource -> languageNames.contains(resource.type());

        return appConfigManager.finalConfig().sources().stream()
            .map(sourcePath -> resourceManager.resources(sourcePath, filter))
            .flatMap(Collection::stream)
            .map(languageSupportManager::analyze)
            .toList();
    }
}
