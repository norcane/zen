package com.norcane.zen.source;

import com.norcane.zen.config.AppConfigManager;
import com.norcane.zen.config.model.AppConfig;
import com.norcane.zen.config.model.AppConfigBuilder;
import com.norcane.zen.config.model.AppConfigRef;
import com.norcane.zen.config.model.AppConfigRefBuilder;
import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.ResourceManager;
import com.norcane.zen.support.java.JavaSourceCode;

import org.junit.jupiter.api.Test;

import java.util.List;

import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@QuarkusTest
class SourceCodeManagerTest {

    @InjectMock
    AppConfigManager appConfigManager;

    @InjectMock
    ResourceManager resourceManager;

    @Inject
    SourceCodeManager sourceCodeManager;

    @Test
    void sourceCodePaths() {
        final String sourcePath = "source-path";
        final AppConfig appConfig = AppConfigBuilder.builder().sources(List.of(sourcePath)).build();
        final AppConfigRef appConfigRef = AppConfigRefBuilder.builder().config(appConfig).build();

        // -- mocks
        when(appConfigManager.finalConfigRef()).thenReturn(appConfigRef);

        assertEquals(List.of(sourcePath), sourceCodeManager.sourceCodePaths());
        assertThrows(UnsupportedOperationException.class, () -> sourceCodeManager.sourceCodePaths().add(null));

        // -- verify
        verify(appConfigManager, times(2)).finalConfigRef();
    }

    @Test
    void sourceCodes() {
        final String sourcePath = "source-path";
        final AppConfig appConfig = AppConfigBuilder.builder().sources(List.of(sourcePath)).build();
        final AppConfigRef appConfigRef = AppConfigRefBuilder.builder().config(appConfig).build();
        final Resource resouce = Resource.inline("test", "java", "imagine some java code here");

        // -- mocks
        when(appConfigManager.finalConfigRef()).thenReturn(appConfigRef);
        when(resourceManager.resources(eq(sourcePath), any())).thenReturn(List.of(resouce));

        final List<SourceCode> sourceCodes = sourceCodeManager.sourceCodes();
        assertEquals(1, sourceCodes.size());
        assertTrue(sourceCodes.get(0) instanceof JavaSourceCode);

        // -- verify
        verify(appConfigManager).finalConfigRef();
        verify(resourceManager).resources(eq(sourcePath), any());
    }

    @Test
    void resetMemoizedState() {
        sourceCodeManager.resetMemoizedState();
    }
}