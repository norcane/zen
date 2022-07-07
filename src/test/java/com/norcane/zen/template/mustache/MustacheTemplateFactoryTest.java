package com.norcane.zen.template.mustache;

import com.norcane.zen.resource.inline.InlineResource;
import com.norcane.zen.template.Template;

import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class MustacheTemplateFactoryTest {

    @Inject
    MustacheTemplateFactory factory;

    @Test
    void templateType() {
        assertEquals("mustache", factory.templateType());
    }

    @Test
    void compile() {
        final Template template = factory.compile(InlineResource.of("test", "mustache", "Hello, {{name}}!"));
        assertNotNull(template);
    }
}