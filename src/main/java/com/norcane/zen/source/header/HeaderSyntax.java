package com.norcane.zen.source.header;

public interface HeaderSyntax {

    boolean isStart(String line);

    boolean isEnd(String line);
}
