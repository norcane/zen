package com.norcane.zen.config.model;

import com.norcane.zen.meta.SemVer;

import org.junit.jupiter.api.Test;

import java.util.List;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class AppConfigTest {

    @Test
    void merge() {
        final AppConfig appConfig1 = AppConfigBuilder.builder()
            .baseVersion(SemVer.from("0.1.0"))
            .templates(List.of())
            .sources(List.of())
            .build();
        final AppConfig appConfig2 = AppConfigBuilder.builder()
            .baseVersion(SemVer.from("0.2.0"))
            .templates(List.of())
            .sources(List.of())
            .build();

        final AppConfig merged = appConfig1.merge(appConfig2);

        assertEquals(appConfig2.baseVersion(), merged.baseVersion());

    }
}