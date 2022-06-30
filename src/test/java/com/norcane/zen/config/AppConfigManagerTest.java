package com.norcane.zen.config;

import com.norcane.zen.config.exception.NoConfigFileFoundException;
import com.norcane.zen.config.model.AppConfig;
import com.norcane.zen.meta.SemVer;
import com.norcane.zen.resource.ResourceLoader;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Objects;

import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@QuarkusTest
public class AppConfigManagerTest {

    @Inject
    ResourceLoader resourceLoader;

    @InjectSpy
    AppConfigManager appConfigManager;

    @Test
    public void testDefaultConfig() {
        final AppConfig config = appConfigManager.defaultConfig();

        assertNotNull(config);
        assertEquals(SemVer.from("0.1.0"), config.baseVersion());
    }

    @Test
    public void testUserConfig() throws Exception {
        final String nonExistingConfigPath = "/foo/bar.yaml";
        final String defaultConfigPath = "/config/default-config.yaml";

        // mocks
        when(appConfigManager.userConfigPath(any())).thenReturn(
            nonExistingConfigPath,
            Path.of(Objects.requireNonNull(getClass().getResource(defaultConfigPath)).toURI()).toAbsolutePath().toString());

        // test case when no configuration is present
        assertThrows(NoConfigFileFoundException.class, appConfigManager::userConfig);

        // test loading of existing configuration
        final AppConfig userConfig = appConfigManager.userConfig();
        assertNotNull(userConfig);
        assertEquals(SemVer.from("0.1.0"), userConfig.baseVersion());
    }
}
