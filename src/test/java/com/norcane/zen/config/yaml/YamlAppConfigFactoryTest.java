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

    @Test
    public void testGetMinCompatibleVersion() {
        final String yamlValidVersion =
            """
                version: 0.1.0
                foo: bar
                """;
        final String yamlMissingVersion =
            """
                foo: bar
                """;

        assertEquals(SemVer.from("0.1.0"), testFactory(yamlValidVersion).getMinCompatibleVersion());
        assertThrows(MissingConfigVersionException.class, () -> testFactory(yamlMissingVersion).getMinCompatibleVersion());

    }

    @Test
    public void testParse() {
        final String yaml =
            """
                version: 0.1.0
                foo: "bar"
                """;

        final YamlAppConfigFactory factory = testFactory(yaml);
        final AppConfig appConfig = factory.parse();

        assertEquals(SemVer.from("0.1.0"), appConfig.version());

    }

    private static YamlAppConfigFactory testFactory(String yaml) {
        return new YamlAppConfigFactory() {

            @Override
            protected Reader configFileReader() {
                return new StringReader(yaml);
            }
        };
    }
}
