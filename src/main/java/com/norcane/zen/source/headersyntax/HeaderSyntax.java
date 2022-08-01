package com.norcane.zen.source.headersyntax;

public interface HeaderSyntax {

    boolean isStart(String line);

    boolean isEnd(String line);
}
