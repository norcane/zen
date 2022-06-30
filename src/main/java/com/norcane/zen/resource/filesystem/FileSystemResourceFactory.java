package com.norcane.zen.resource.filesystem;

import com.norcane.zen.resource.DefaultResourceFactory;
import com.norcane.zen.resource.PathMatcher;
import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.ResourceFactory;
import com.norcane.zen.resource.exception.ResourceNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
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
        final Path path = Path.of(location);
        if (!Files.isReadable(path)) {
            throw new ResourceNotFoundException(location);
        }

        return FileSystemResource.of(path);
    }

    @Override
    public List<Resource> getResources(final String location) {
        if (!pathMatcher.isPattern(location)) {
            final Path path = Path.of(location);

            if (Files.isDirectory(path)) {
                // if path is directory, find all files inside the directory
                return findResources(path.resolve("*").toString());
            } else if (Files.isReadable(path)) {
                // if it's path to file, return the file itself
                return List.of(FileSystemResource.of(path));
            } else {
                // nothing found
                return Collections.emptyList();
            }
        }

        // find resources for given pattern
        return findResources(location);
    }

    protected List<Resource> findResources(String location) {
        final String rootDir = resolveRootDir(location);
        final String pattern = location.substring(rootDir.length());

        return findResources(rootDir, pattern);
    }

    protected List<Resource> findResources(final String rootDir, final String pattern) {
        final Path rootPath = Path.of(rootDir);

        try (final Stream<Path> stream = Files.walk(rootPath)) {
            return stream
                .filter(Files::isRegularFile)
                .filter(path -> pathMatcher.matches(pattern, Path.of(rootDir).relativize(path)))
                .map(FileSystemResource::of)
                .map(Resource.class::cast)
                .toList();
        } catch (IOException e) {
            throw new ResourceNotFoundException(pattern, e);
        }
    }

    protected String resolveRootDir(final String location) {
        int rootDirEnd = location.length();

        while (pathMatcher.isPattern(location.substring(0, rootDirEnd))) {
            rootDirEnd = location.lastIndexOf('/', rootDirEnd - 2) + 1;
        }

        return location.substring(0, rootDirEnd);
    }
}
