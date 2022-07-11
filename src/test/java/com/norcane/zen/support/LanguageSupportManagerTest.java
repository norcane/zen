package com.norcane.zen.support;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class LanguageSupportManagerTest {

    @Inject
    LanguageSupportManager manager;

    @Inject
    Instance<LanguageSupport> languageSupports;

    @Test
    void managedResourceTypes() {
        final Set<String> allExtensions = languageSupports.stream()
            .map(LanguageSupport::resourceTypes)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());

        assertEquals(allExtensions, manager.managedResourceTypes());
    }

    @Test
    void managedLanguageNames() {
        final Set<String> allNames = languageSupports.stream()
            .map(LanguageSupport::name)
            .collect(Collectors.toSet());

        assertEquals(allNames, manager.managedLanguageNames());
    }
}