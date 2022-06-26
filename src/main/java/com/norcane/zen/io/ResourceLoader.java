package com.norcane.zen.io;

import com.norcane.zen.io.exception.ResourceIOException;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ResourceLoader {

    public Resource getResource(final String path) {
        Objects.requireNonNull(path, "path must not be null");

        if (path.startsWith(ClassPathResource.PREFIX)) {
            // handle as classpath resource
            return ClassPathResource.of(path);
        } else if (path.startsWith(UrlResource.HTTP_PREFIX) || path.startsWith(UrlResource.HTTPS_PREFIX)) {
            // handle as remote URL resource
            try {
                return UrlResource.of(new URL(path));
            } catch (MalformedURLException e) {
                throw new ResourceIOException(path, e);
            }
        } else if (path.startsWith(StringResource.PREFIX)) {
            // handle as inlined string-based resource
            return StringResource.of(path);
        } else {
            // by default, consider path as file system resource
            return new FileSystemResource(Paths.get(path));
        }
    }
}
