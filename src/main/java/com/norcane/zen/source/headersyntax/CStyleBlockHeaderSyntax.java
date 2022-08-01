package com.norcane.zen.source.headersyntax;

import java.util.regex.Pattern;

public class CStyleBlockHeaderSyntax implements HeaderSyntax {

    private static final Pattern PATTERN_START = Pattern.compile("^/\\*");
    private static final Pattern PATTERN_END = Pattern.compile("\\*/$");

    @Override
    public boolean isStart(String line) {
        return PATTERN_START.matcher(line).find();
    }

    @Override
    public boolean isEnd(String line) {
        return PATTERN_END.matcher(line).find();
    }
}
