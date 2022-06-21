package com.norcane.zen.template.mustache;

import com.norcane.zen.template.Template;
import com.norcane.zen.template.TemplateFactory;

import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MustacheTemplateTest {

    private final TemplateFactory templateFactory = new MustacheTemplateFactory();

    @Test
    public void testRender() {
        final String templateName = "test";
        final String expected = "Hello John, 42 years old";
        final Template template = templateFactory.compile(templateName, new StringReader("Hello {{name}}, {{info.age}} years old"));

        final Map<String, Object> variables = Map.of(
            "name", "John",
            "info", new UserInfo(42)
        );

        assertEquals(templateName, template.getName());
        assertEquals(expected, template.render(new StringWriter(), variables).toString());
    }

    private record UserInfo(int age) {
    }
}