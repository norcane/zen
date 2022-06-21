package com.norcane.zen.config.yaml;

import com.norcane.zen.config.AppConfig;
import com.norcane.zen.config.exception.MissingConfigVersionException;
import com.norcane.zen.meta.SemVer;

import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class YamlAppConfigFactoryTest {

    private final YamlAppConfigFactory factory = new YamlAppConfigFactory();

    @Test
    public void testFileExtension() {
        assertEquals("yaml", factory.fileExtension());
    }

    @Test
    public void testMinCompatibleVersion() {
        final Reader yamlValidVersion = new StringReader(
            """
                version: 0.1.0
                foo: bar
                """);
        final Reader yamlMissingVersion = new StringReader(
            """
                foo: bar
                """);

        assertEquals(SemVer.from("0.1.0"), factory.minCompatibleVersion(yamlValidVersion, null));
        assertThrows(MissingConfigVersionException.class, () -> factory.minCompatibleVersion(yamlMissingVersion, null));

    }

    @Test
    public void testParse() {
        final Reader yaml = new StringReader(
            """
                version: 0.1.0
                foo: "bar"
                """);

        final AppConfig appConfig = factory.parse(yaml, null);

        assertEquals(SemVer.from("0.1.0"), appConfig.version());

    }
}
