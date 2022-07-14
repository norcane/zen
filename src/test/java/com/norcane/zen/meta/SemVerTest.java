package com.norcane.zen.meta;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
public class SemVerTest {

    @Test
    public void testFrom() {
        assertEquals(new SemVer(0, 1, 0, null), SemVer.from("0.1.0"));
        assertEquals(new SemVer(0, 1, 0, "RC3"), SemVer.from("0.1.0-RC3"));

        assertThrows(IllegalArgumentException.class, () -> SemVer.from("foo"));
    }

    @Test
    public void testCompareTo() {
        assertEquals(0, SemVer.from("0.1.0").compareTo(SemVer.from("0.1.0")));
        assertEquals(1, SemVer.from("0.1.0").compareTo(SemVer.from("0.1.0-RC3")));
        assertEquals(-1, SemVer.from("0.1.0-RC2").compareTo(SemVer.from("0.1.0-RC3")));
        assertEquals(-1, SemVer.from("0.1.0").compareTo(SemVer.from("0.1.1")));
        assertEquals(1, SemVer.from("1.0.1").compareTo(SemVer.from("0.2.1")));
        assertEquals(1, SemVer.from("1.1.0").compareTo(SemVer.from("1.0.0")));
        assertEquals(-1, SemVer.from("1.0.0-RC1").compareTo(SemVer.from("1.0.0")));
        assertEquals(1, SemVer.from("1.0.0-2").compareTo(SemVer.from("1.0.0-1")));
    }

    @Test
    public void testToPretty() {
        assertEquals("0.1.0", SemVer.from("0.1.0").toPretty());
        assertEquals("0.1.0-RC3", SemVer.from("0.1.0-RC3").toPretty());

    }
}
