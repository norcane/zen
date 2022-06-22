package com.norcane.zen.meta;

import com.norcane.zen.ui.PrettyPrintable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Simple implementation of version using the <a href="https://semver.org">Semantic versioning</a> specification. Supports basic operations such as parsing,
 * pretty printing and comparing with other version.
 *
 * @param major major version
 * @param minor minor version
 * @param patch patch level
 * @param suffix additional suffix (such as {@code SNAPSHOT}), may be null
 */
public record SemVer(int major, int minor, int patch, String suffix) implements Comparable<SemVer>, PrettyPrintable {

    private static final Pattern pattern = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)(?:-([a-zA-Z\\d]+))?");

    /**
     * Parses semantic version out of the raw string representation.
     *
     * @param rawVersion raw string representation to parse
     * @return parsed representation
     * @throws IllegalArgumentException if input string doesn't hold valid version
     */
    public static SemVer from(String rawVersion) {
        final Matcher matcher = pattern.matcher(rawVersion);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Not a valid SemVer string: %s".formatted(rawVersion));
        }

        final int major = Integer.parseInt(matcher.group(1));
        final int minor = Integer.parseInt(matcher.group(2));
        final int patch = Integer.parseInt(matcher.group(3));
        final String suffix = matcher.group(4);

        return new SemVer(major, minor, patch, suffix);
    }

    @Override
    public int compareTo(SemVer that) {
        // compare major versions
        final int majorCompared = Integer.compare(this.major(), that.major());
        if (majorCompared != 0) {
            return majorCompared;
        }

        // compare minor versions
        final int minorCompared = Integer.compare(this.minor(), that.minor());
        if (minorCompared != 0) {
            return minorCompared;
        }

        // compare patch versions
        final int patchCompared = Integer.compare(this.patch(), that.patch());
        if (patchCompared != 0) {
            return patchCompared;
        }

        // compare suffixes
        if (this.suffix() == null && that.suffix() != null) {
            return 1;
        } else if (this.suffix() != null && that.suffix() == null) {
            return -1;
        } else if (this.suffix() != null) {
            try {
                // let's try to resolve suffixes as integers
                final int thisIntSuffix = Integer.parseInt(this.suffix());
                final int thatIntSuffix = Integer.parseInt(that.suffix());

                final int suffixesCompared = Integer.compare(thisIntSuffix, thatIntSuffix);
                if (suffixesCompared != 0) {
                    return suffixesCompared;
                }
            } catch (NumberFormatException e) {
                // otherwise, compare suffixes as strings
                return this.suffix().compareToIgnoreCase(that.suffix());
            }
        }

        return 0;
    }

    @Override
    public String toPretty() {
        return suffix != null
               ? "%d.%d.%d-%s".formatted(major, minor, patch, suffix)
               : "%d.%d.%d".formatted(major, minor, patch);
    }
}
