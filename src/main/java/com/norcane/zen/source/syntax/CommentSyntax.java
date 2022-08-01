package com.norcane.zen.source.syntax;

public interface CommentSyntax {

    boolean isStart(String line);

    boolean isEnd(String line);
}
