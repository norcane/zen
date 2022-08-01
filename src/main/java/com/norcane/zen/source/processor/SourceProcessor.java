package com.norcane.zen.source.processor;

import com.norcane.zen.source.SourceCode;
import com.norcane.zen.template.Template;

public interface SourceProcessor {

    String progressMessage(SourceCode sourceCode);

    void process(SourceCode sourceCode, Template template);

    void printSummary();

    int returnCode();
}
