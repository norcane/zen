package com.norcane.zen.resource.filesystem;

import com.norcane.zen.resource.DefaultResourceFactory;
import com.norcane.zen.resource.PathMatcher;
import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.ResourceFactory;
import com.norcane.zen.resource.exception.ResourceNotFoundException;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@DefaultResourceFactory
public class FileSystemResourceFactory implements ResourceFactory {

    private final PathMatcher pathMatcher;

    private static final List<String> PREFIXES = List.of("file");

    @Inject
    public FileSystemResourceFactory(PathMatcher pathMatcher) {
        this.pathMatcher = Objects.requireNonNull(pathMatcher);
    }

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

    protected List<Resource> findResources(final String rootDir, final String pattern) {
        try (final Stream<Path> stream = Files.walk(Paths.get(rootDir))) {
            return stream
                .filter(path -> pathMatcher.matches(pattern, path))
                .map(FileSystemResource::of)
                .map(Resource.class::cast)
                .toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
