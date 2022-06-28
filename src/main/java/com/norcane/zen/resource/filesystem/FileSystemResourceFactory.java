package com.norcane.zen.resource.filesystem;

import com.norcane.zen.resource.DefaultResourceFactory;
import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.ResourceFactory;
import com.norcane.zen.resource.exception.ResourceNotFoundException;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@DefaultResourceFactory
public class FileSystemResourceFactory implements ResourceFactory {

    private static final List<String> PREFIXES = List.of("file");
    private static final String PATH_MATCHER_SYNTAX = "glob";

    @Override
    public List<String> getPrefixes() {
        return PREFIXES;
    }

    @Override
    public Resource getResource(final String location) {
        final Path path = Paths.get(location);
        if (!Files.isReadable(path)) {
            throw new ResourceNotFoundException(location);
        }

        return FileSystemResource.of(path);
    }

    @Override
    public List<Resource> getResources(final String location) {
        // FIXME implement
        throw new UnsupportedOperationException();
    }

    protected List<Resource> findResources(final String rootDir, final String glob) {
        final String syntaxAndPattern = PATH_MATCHER_SYNTAX + ":" + glob;

        try (final Stream<Path> stream = Files.walk(Paths.get(rootDir))) {
            final PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher(syntaxAndPattern);
            return stream.filter(pathMatcher::matches).map(path -> (Resource) FileSystemResource.of(path)).toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }
}
