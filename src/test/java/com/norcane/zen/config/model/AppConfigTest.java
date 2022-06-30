package com.norcane.zen.config.model;

import com.norcane.zen.meta.SemVer;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class AppConfigTest {

    @Test
    void merge() {
        final AppConfig appConfig1 = new AppConfig(SemVer.from("0.2.0"));
        final AppConfig appConfig2 = new AppConfig(SemVer.from("0.2.0"));

        final AppConfig merged = appConfig1.merge(appConfig2);

        assertEquals(appConfig2.baseVersion(), merged.baseVersion());

    }
}