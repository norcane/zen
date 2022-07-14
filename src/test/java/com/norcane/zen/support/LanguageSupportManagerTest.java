package com.norcane.zen.support;

import com.norcane.zen.resource.Resource;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
class LanguageSupportManagerTest {

    @Inject
    LanguageSupportManager manager;

    @Inject
    Instance<LanguageSupport> languageSupports;

    @Test
    void analyze_unsupportedType() {
        final Resource resource = Resource.inline("test", "foo", "some content");
        assertThrows(IllegalArgumentException.class, () -> manager.analyze(resource));
    }

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