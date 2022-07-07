package com.norcane.zen.config.yaml;

import com.norcane.zen.config.exception.ConfigParseException;
import com.norcane.zen.config.exception.MissingConfigVersionException;
import com.norcane.zen.meta.SemVer;
import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.inline.InlineResource;

import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
public class YamlAppConfigFactoryTest {

    @Inject
    YamlAppConfigFactory factory;

    @Test
    public void testFileExtension() {
        assertEquals("yaml", factory.fileExtension());
    }

    @Test
    public void testBaseVersion() {
        final Resource yamlValidVersion = InlineResource.of(
            "test",
            "yaml",
            """
                base-version: 0.1.0
                foo: bar
                """);
        final Resource yamlMissingVersion = InlineResource.of(
            "test",
            "yaml",
            """
                foo: bar
                """);
        final Resource yamlInvalid = InlineResource.of("test", "yaml", "foo");

        assertEquals(SemVer.from("0.1.0"), factory.baseVersion(yamlValidVersion));
        assertThrows(MissingConfigVersionException.class, () -> factory.baseVersion(yamlMissingVersion));
        assertThrows(ConfigParseException.class, () -> factory.baseVersion(yamlInvalid));
    }

    @Test
    public void testParse() {
        final Resource yaml = InlineResource.of(
            "test",
            "yaml",
            """
                base-version: 0.1.0
                foo: "bar"
                """);
        final Resource yamlInvalid = InlineResource.of("test", "yaml", "foo");

        assertEquals(SemVer.from("0.1.0"), factory.parse(yaml).baseVersion());
        assertThrows(ConfigParseException.class, () -> factory.parse(yamlInvalid));
    }
}
