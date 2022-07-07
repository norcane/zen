package com.norcane.zen.meta;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class ProductInfoTest {

    @Test
    void productHeader() {
        assertNotNull(ProductInfo.productHeader());
    }

    @Test
    void productVersion() {
        assertNotNull(ProductInfo.productVersion());
    }
}