package com.norcane.zen.source.syntax;

import java.util.regex.Pattern;

public class CStyleLineCommentSyntax implements CommentSyntax {

    private static final CStyleLineCommentSyntax INSTANCE = new CStyleLineCommentSyntax();


    private CStyleLineCommentSyntax() {
        // package-private constructor
    }

    public static CStyleLineCommentSyntax instance() {
        return INSTANCE;
    }

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
