package com.norcane.zen.config.yaml;

import com.norcane.zen.config.exception.MissingConfigVersionException;
import com.norcane.zen.config.model.AppConfig;
import com.norcane.zen.io.Resource;
import com.norcane.zen.io.StringResource;
import com.norcane.zen.meta.SemVer;

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
    public void testMinCompatibleVersion() {
        final String sourceName = "<string_source>";
        final Resource yamlValidVersion = new StringResource(
            """
                min-compatible-version: 0.1.0
                foo: bar
                """);
        final Resource yamlMissingVersion = new StringResource(
            """
                foo: bar
                """);

        assertEquals(SemVer.from("0.1.0"), factory.minCompatibleVersion(yamlValidVersion));
        assertThrows(MissingConfigVersionException.class, () -> factory.minCompatibleVersion(yamlMissingVersion));

    }

    @Test
    public void testParse() {
        final Resource yaml = new StringResource(
            """
                min-compatible-version: 0.1.0
                foo: "bar"
                """);

        final AppConfig appConfig = factory.parse(yaml);

        assertEquals(SemVer.from("0.1.0"), appConfig.minCompatibleVersion());

    }
}
