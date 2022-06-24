package com.norcane.zen.config;

import com.norcane.zen.meta.SemVer;

/**
 * Represents application configuration.
 */
public record AppConfig(SemVer minCompatibleVersion) {
}