package com.norcane.zen.source;

import com.norcane.zen.resource.Resource;
import com.norcane.zen.source.header.CStyleBlockHeaderSyntax;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AbstractSourceCodeTest {

    @Test
    void header_blockHeaderMultiLine() {
        final Resource resource = Resource.inline(
            "test",
            "java",
            """
                this is some code
                this is also some code // with inline comment
                this is also some code /* with block comment */
                                
                /*
                 * this is header
                 */
                 
                some code here
                 """);
        final SourceCode sourceCode = SourceCode.of(new CStyleBlockHeaderSyntax(), resource);

        final SourceCode.Header expected = new SourceCode.Header(5, 7, List.of("/*", " * this is header", " */"));
        assertEquals(Optional.of(expected), sourceCode.header());
    }

    @Test
    void header_blockHeaderOneLine() {
        final Resource resource = Resource.inline(
            "test",
            "java",
            """
                this is some code
                this is also some code // with inline comment
                this is also some code /* with block comment */
                                
                not /* a header */
                /* this is header */
                 
                some code here
                 """);
        final SourceCode sourceCode = SourceCode.of(new CStyleBlockHeaderSyntax(), resource);

        final SourceCode.Header expected = new SourceCode.Header(6, 6, List.of("/* this is header */"));
        assertEquals(Optional.of(expected), sourceCode.header());
    }


    @Test
    void resource() {
    }

    @Test
    void testToString() {
        final SourceCode sourceCode = SourceCode.of(new CStyleBlockHeaderSyntax(), Resource.inline("test", "test", "some text"));
        assertNotNull(sourceCode.toString());
    }
}