package com.norcane.zen.resource.filesystem;

import com.norcane.zen.resource.DefaultResourceFactory;
import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.ResourceFactory;
import com.norcane.zen.resource.exception.ResourceNotFoundException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@DefaultResourceFactory
public class FileSystemResourceFactory implements ResourceFactory {

    private static final List<String> PREFIXES = List.of("file");

    @Override
    public List<String> getPrefixes() {
        return PREFIXES;
    }

    @Override
    public Resource getResource(String location) {
        final Path path = Paths.get(location);
        if (!Files.isReadable(path)) {
            throw new ResourceNotFoundException(location);
        }

        return FileSystemResource.of(path);
    }

    @Override
    public List<Resource> getResources(String location) {
        // FIXME implement
        throw new UnsupportedOperationException();
    }
}
