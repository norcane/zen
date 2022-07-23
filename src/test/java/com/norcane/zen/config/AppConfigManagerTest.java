package com.norcane.zen.config;

import com.norcane.zen.config.exception.InvalidConfigurationException;
import com.norcane.zen.config.exception.NoConfigFileFoundException;
import com.norcane.zen.config.model.AppConfigRef;
import com.norcane.zen.meta.SemVer;

import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
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
    void defaultConfigRef() {
        final AppConfigRef configRef = appConfigManager.defaultConfigRef();

        assertNotNull(configRef);
        assertEquals(SemVer.from("0.1.0"), configRef.config().baseVersion());
    }

    @Test
    void userConfigRef() throws Exception {
        final String nonExistingConfigPath = "/foo/bar.yaml";
        final String validConfigPath = "/config/valid-config.yaml";

        // mocks
        when(appConfigManager.userConfigPath(any())).thenReturn(nonExistingConfigPath, absolutePath(validConfigPath));

        // test case when no configuration is present
        appConfigManager.resetMemoizedState();
        assertThrows(NoConfigFileFoundException.class, appConfigManager::userConfigRef);

        // test loading of existing configuration
        appConfigManager.resetMemoizedState();
        final AppConfigRef userConfig = appConfigManager.userConfigRef();
        assertNotNull(userConfig);
        assertEquals(SemVer.from("0.1.0"), userConfig.config().baseVersion());
    }

    @Test
    void finalConfigRef() throws Exception {
        final String validConfigPath = "/config/valid-config.yaml";
        final String invalidConfigPath = "/config/invalid-config.yaml";

        // mocks
        when(appConfigManager.userConfigPath(any())).thenReturn(
            absolutePath(validConfigPath),
            absolutePath(validConfigPath),
            absolutePath(invalidConfigPath));

        // valid configuration loaded
        appConfigManager.resetMemoizedState();
        assertNotNull(appConfigManager.finalConfigRef());

        // invalid configuration loaded
        appConfigManager.resetMemoizedState();
        assertThrows(InvalidConfigurationException.class, () -> appConfigManager.finalConfigRef());
    }

    private String absolutePath(String classPathResource) throws URISyntaxException {
        return Path.of(Objects.requireNonNull(getClass().getResource(classPathResource)).toURI()).toAbsolutePath().toString();
    }
}
