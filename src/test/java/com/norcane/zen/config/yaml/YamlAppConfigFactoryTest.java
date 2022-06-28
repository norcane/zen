package com.norcane.zen.config.yaml;

import com.norcane.zen.config.exception.MissingConfigVersionException;
import com.norcane.zen.config.model.AppConfig;
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
            """
                base-version: 0.1.0
                foo: bar
                """);
        final Resource yamlMissingVersion = InlineResource.of(
            """
                foo: bar
                """);

        assertEquals(SemVer.from("0.1.0"), factory.baseVersion(yamlValidVersion));
        assertThrows(MissingConfigVersionException.class, () -> factory.baseVersion(yamlMissingVersion));

    }

    @Test
    public void testParse() {
        final Resource yaml = InlineResource.of(
            """
                base-version: 0.1.0
                foo: "bar"
                """);

        final AppConfig appConfig = factory.parse(yaml);

        assertEquals(SemVer.from("0.1.0"), appConfig.baseVersion());

    }
}
