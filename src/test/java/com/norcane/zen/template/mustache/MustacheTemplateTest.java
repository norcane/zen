package com.norcane.zen.template.mustache;

import com.norcane.zen.resource.Resource;
import com.norcane.zen.template.Template;
import com.norcane.zen.template.TemplateFactory;

import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.util.Map;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class MustacheTemplateTest {

    private final TemplateFactory templateFactory = new MustacheTemplateFactory();

    @Test
    public void testRender() {
        final String expected = "Hello John, 42 years old";
        final Resource resource = Resource.inline("test", "mustache", "Hello {{name}}, {{info.age}} years old");
        final Template template = templateFactory.compile(resource);

        final Map<String, Object> variables = Map.of(
            "name", "John",
            "info", new UserInfo(42)
        );

        assertEquals(resource.location(), template.getName());
        assertEquals(expected, template.render(new StringWriter(), variables).toString());
    }

    @Test
    void testToString() {
        final Resource resource = Resource.inline("test", "mustache", "Hello {{name}}, {{info.age}} years old");
        final Template template = templateFactory.compile(resource);

        assertNotNull(template.toString());
    }

    private record UserInfo(int age) {
    }
}
