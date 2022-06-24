package com.norcane.zen.config.yaml;

import com.norcane.zen.config.data.AppConfig;
import com.norcane.zen.config.exception.MissingConfigVersionException;
import com.norcane.zen.meta.SemVer;

import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;

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
        final Reader yamlValidVersion = new StringReader(
            """
                min-compatible-version: 0.1.0
                foo: bar
                """);
        final Reader yamlMissingVersion = new StringReader(
            """
                foo: bar
                """);

        assertEquals(SemVer.from("0.1.0"), factory.minCompatibleVersion(yamlValidVersion, sourceName));
        assertThrows(MissingConfigVersionException.class, () -> factory.minCompatibleVersion(yamlMissingVersion, sourceName));

    }

    @Test
    public void testParse() {
        final Reader yaml = new StringReader(
            """
                min-compatible-version: 0.1.0
                foo: "bar"
                """);

        final AppConfig appConfig = factory.parse(yaml, null);

        assertEquals(SemVer.from("0.1.0"), appConfig.minCompatibleVersion());

    }
}
