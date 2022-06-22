package com.norcane.zen.config;

import com.norcane.zen.config.exception.NoConfigFileFoundException;
import com.norcane.zen.meta.SemVer;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Objects;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@QuarkusTest
public class AppConfigManagerTest {

    @InjectSpy
    AppConfigManager appConfigManager;

    @Test
    public void testLoadDefaultConfig() {
        final AppConfig config = appConfigManager.loadDefaultConfig();

        assertNotNull(config);
        assertEquals(SemVer.from("0.1.0"), config.minCompatibleVersion());
    }

    @Test
    public void testLoadUserConfig() throws Exception {
        final String nonExistingConfigPath = "/foo/bar.yaml";
        final String defaultConfigPath = "/config/default-config.yaml";

        // mocks
        when(appConfigManager.userConfigPath(any())).thenReturn(
            nonExistingConfigPath,
            Paths.get(Objects.requireNonNull(getClass().getResource(defaultConfigPath)).toURI()).toAbsolutePath().toString());

        // test case when no configuration is present
        assertThrows(NoConfigFileFoundException.class, appConfigManager::loadUserConfig);

        // test loading of existing configuration
        final AppConfig userConfig = appConfigManager.loadUserConfig();
        assertNotNull(userConfig);
        assertEquals(SemVer.from("0.1.0"), userConfig.minCompatibleVersion());
    }
}
