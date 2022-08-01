package com.norcane.zen.source.syntax;

import java.util.regex.Pattern;

public class CStyleBlockCommentSyntax implements CommentSyntax {

    private static final CStyleBlockCommentSyntax INSTANCE = new CStyleBlockCommentSyntax();

    private CStyleBlockCommentSyntax() {
        // package-private constructor
    }

    public static CStyleBlockCommentSyntax instance() {
        return INSTANCE;
    }

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
