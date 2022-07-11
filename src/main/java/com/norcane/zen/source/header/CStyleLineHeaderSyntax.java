package com.norcane.zen.source.header;

import java.util.regex.Pattern;

public class CStyleLineHeaderSyntax implements HeaderSyntax {

    private static final Pattern PATTERN = Pattern.compile("^//");

    @Override
    public boolean isStart(String line) {
        return PATTERN.matcher(line).find();
    }

    @Override
    public boolean isEnd(String line) {
        return PATTERN.matcher(line).find();
    }
}
