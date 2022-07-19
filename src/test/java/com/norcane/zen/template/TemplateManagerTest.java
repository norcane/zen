package com.norcane.zen.template;

import com.norcane.zen.config.AppConfigManager;
import com.norcane.zen.config.model.AppConfig;
import com.norcane.zen.config.model.AppConfigBuilder;
import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.ResourceManager;
import com.norcane.zen.template.exception.DuplicateTemplatesFoundException;
import com.norcane.zen.template.mustache.MustacheTemplate;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@QuarkusTest
class TemplateManagerTest {

    @InjectMock
    AppConfigManager appConfigManager;

    @InjectMock
    ResourceManager resourceManager;

    @Inject
    TemplateManager manager;

    @Test
    void managedTemplateExtensions() {
        assertEquals(Set.of("mustache"), manager.managedTemplateExtensions());
    }

    @Test
    void templatePaths() {
        final String templatesPath = "templates";
        final AppConfig appConfig = AppConfigBuilder.builder().templates(List.of(templatesPath)).build();

        // -- mocks
        when(appConfigManager.finalConfig()).thenReturn(appConfig);

        assertEquals(List.of(templatesPath), manager.templatePaths());
        assertThrows(UnsupportedOperationException.class, () -> manager.templatePaths().add(null));
    }

    @Test
    void templates() {
        final String templatesPath = "templates";
        final Resource template1 = Resource.inline("test", "mustache", "Hello, {{name}}!");
        final Resource template2 = Resource.inline("test", "freemarker", "Hello, ${name}!");
        final AppConfig appConfig = AppConfigBuilder.builder().templates(List.of(templatesPath)).build();

        // -- mocks
        when(appConfigManager.finalConfig()).thenReturn(appConfig);
        when(resourceManager.resources(eq(templatesPath), any()))
            .thenReturn(List.of(template1))             // valid state - only one template type for source type
            .thenReturn(List.of(template1, template2)); // invalid state - two possible templates for one source type

        final Map<String, Template> templates = manager.templates();
        assertTrue(templates.containsKey("test"));
        assertTrue(templates.get("test") instanceof MustacheTemplate);

        assertThrows(UnsupportedOperationException.class, () -> manager.templates().put("foo", null));

        manager.resetMemoizedState();
        assertThrows(DuplicateTemplatesFoundException.class, () -> manager.templates());

        // -- verify
        verify(appConfigManager, times(2)).finalConfig();
        verify(resourceManager, times(2)).resources(eq(templatesPath), any());
    }
}