package com.norcane.zen.meta;

/**
 * General information about the application. Where possible, such information is extracted from <i>Maven</i> project files.
 */
public class ProductInfo {

    public static final String NAME = BuildInfo.NAME;
    public static final String DESCRIPTION = BuildInfo.DESCRIPTION;
    public static final String VERSION = BuildInfo.VERSION;
    public static final String WEBSITE = "https://github.com/norcane/zen";
    private static final String URL_REPORT_BUG = "https://github.com/norcane/zen/issues/new";

    public static String productHeader() {
        return String.format("Welcome to @|bold,magenta %s %s|@ :: @|underline %s|@", NAME, VERSION, WEBSITE);
    }

    public static SemVer productVersion() {
        return SemVer.from(VERSION);
    }

    public static String linkReportBug() {
        return URL_REPORT_BUG;
    }
}
