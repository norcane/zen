package com.norcane.zen.source;

import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.exception.CannotReadResourceException;
import com.norcane.zen.source.header.CStyleBlockHeaderSyntax;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@QuarkusTest
class AbstractSourceCodeTest {

    @Test
    void header_exceptionThrown() {
        final Resource resource = mock(Resource.class);

        final SourceCode sourceCode = SourceCode.of(new CStyleBlockHeaderSyntax(), resource);

        // mocks
        doAnswer(invocation -> {
            throw new IOException("invalid");
        }).when(resource).reader();

        assertThrows(CannotReadResourceException.class, sourceCode::header);

        // verify
        verify(resource).reader();
    }

    @Test
    void header_noHeaderPresent() {
        final Resource resource = Resource.inline(
            "test",
            "java",
            """
                this is some code
                this is also some code // with inline comment
                this is also some code /* with block comment */
                                
                some code here
                """);
        final SourceCode sourceCode = SourceCode.of(new CStyleBlockHeaderSyntax(), resource);
        assertTrue(sourceCode.header().isEmpty());

    }

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
        final Resource resource = Resource.inline("test", "test", "some text");
        final SourceCode sourceCode = SourceCode.of(new CStyleBlockHeaderSyntax(), resource);

        assertEquals(resource, sourceCode.resource());
    }

    @Test
    void of() {
        final Resource resource = Resource.inline("test", "test", "some text");
        final SourceCode sourceCode = SourceCode.of(new CStyleBlockHeaderSyntax(), resource);

        assertEquals(resource, sourceCode.resource());
        assertTrue(sourceCode.variables().isEmpty());
    }

    @Test
    void testToString() {
        final SourceCode sourceCode = SourceCode.of(new CStyleBlockHeaderSyntax(), Resource.inline("test", "test", "some text"));
        assertNotNull(sourceCode.toString());
    }
}