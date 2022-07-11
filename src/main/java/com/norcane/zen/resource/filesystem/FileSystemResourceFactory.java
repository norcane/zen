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
import java.util.Optional;
import java.util.function.Predicate;
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
    public List<String> prefixes() {
        return PREFIXES;
    }

    @Override
    public Optional<Resource> resource(final String location) {
        final Path path = Path.of(location);
        return Files.isReadable(path) ? Optional.of(FileSystemResource.of(path)) : Optional.empty();
    }

    @Override
    public List<Resource> resources(final String locationPattern, Predicate<Resource> filter) {
        if (!pathMatcher.isPattern(locationPattern)) {
            final Path path = Path.of(locationPattern);

            if (Files.isDirectory(path)) {
                // if path is directory, find all files inside the directory
                return findResources(path.resolve("**").toString(), filter);
            } else if (Files.isReadable(path)) {
                // if it's path to file, return the file itself
                return List.of(FileSystemResource.of(path));
            } else {
                // nothing found
                return Collections.emptyList();
            }
        }

        // find resources for given pattern
        return findResources(locationPattern, filter);
    }

    protected List<Resource> findResources(String location, Predicate<Resource> filter) {
        final String rootDir = resolveRootDir(location);
        final String pattern = location.substring(rootDir.length());

        return findResources(rootDir, pattern, filter);
    }

    protected List<Resource> findResources(final String rootDir, final String pattern, final Predicate<Resource> filter) {
        final Path rootPath = Path.of(rootDir);

        try (final Stream<Path> stream = Files.walk(rootPath)) {
            return stream
                .filter(Files::isRegularFile)
                .filter(path -> pathMatcher.matches(pattern, Path.of(rootDir).relativize(path)))
                .map(FileSystemResource::of)
                .map(Resource.class::cast)
                .filter(filter)
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
