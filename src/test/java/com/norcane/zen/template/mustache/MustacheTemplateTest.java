package com.norcane.zen.template.mustache;

import com.norcane.zen.io.Resource;
import com.norcane.zen.io.StringResource;
import com.norcane.zen.template.Template;
import com.norcane.zen.template.TemplateFactory;

import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MustacheTemplateTest {

    private final TemplateFactory templateFactory = new MustacheTemplateFactory();

    @Test
    public void testRender() {
        final String expected = "Hello John, 42 years old";
        final Resource resource = StringResource.of("Hello {{name}}, {{info.age}} years old");
        final Template template = templateFactory.compile(resource);

        final Map<String, Object> variables = Map.of(
            "name", "John",
            "info", new UserInfo(42)
        );

        assertEquals(resource.getLocation(), template.getName());
        assertEquals(expected, template.render(new StringWriter(), variables).toString());
    }

    private record UserInfo(int age) {
    }
}
