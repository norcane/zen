package com.norcane.zen.source;

import com.google.common.base.MoreObjects;

import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.exception.CannotReadResourceException;
import com.norcane.zen.source.syntax.CommentSyntax;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class AbstractSourceCode implements SourceCode {

    private final CommentSyntax commentSyntax;
    private final Resource resource;

    protected AbstractSourceCode(final CommentSyntax commentSyntax,
                                 final Resource resource) {

        this.commentSyntax = Objects.requireNonNull(commentSyntax);
        this.resource = Objects.requireNonNull(resource);
    }

    @Override
    public Optional<Header> header() {
        try (final BufferedReader reader = new BufferedReader(resource.reader())) {
            final List<String> lines = new ArrayList<>();
            int lineNumber = 0;
            int headerStart = -1;
            int headerEnd = -1;

            while (reader.ready()) {
                final String line = reader.readLine();
                lineNumber++;

                if (line == null) {
                    break;
                }

                if (headerStart < 0 && commentSyntax.isStart(line)) {
                    headerStart = lineNumber;
                    lines.add(line);     // FIXME proper line handling

                    if (commentSyntax.isEnd(line)) {
                        headerEnd = lineNumber;
                        break;
                    }
                } else if (headerStart > 0) {
                    lines.add(line);     // FIXME proper line handling

                    if (commentSyntax.isEnd(line)) {
                        headerEnd = lineNumber;
                        break;
                    }
                }
            }

            return headerStart > 0 && headerEnd > 0
                   ? Optional.of(new Header(headerStart, headerEnd, Collections.unmodifiableList(lines)))
                   : Optional.empty();
        } catch (IOException e) {
            throw new CannotReadResourceException(resource, e);
        }
    }

    @Override
    public Resource resource() {
        return resource;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("resource", this.resource)
            .toString();
    }
}
