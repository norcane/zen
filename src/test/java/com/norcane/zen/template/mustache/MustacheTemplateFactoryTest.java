package com.norcane.zen.template.mustache;

import com.norcane.zen.resource.inline.InlineResource;
import com.norcane.zen.template.Template;

import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class MustacheTemplateFactoryTest {

    @Inject
    MustacheTemplateFactory factory;

    @Test
    void fileExtensions() {
        assertFalse(factory.fileExtensions().isEmpty());
    }

    @Test
    void compile() {
        final Template template = factory.compile(InlineResource.of("Hello, {{name}}!"));
        assertNotNull(template);
    }
}