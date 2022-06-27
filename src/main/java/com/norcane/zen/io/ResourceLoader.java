package com.norcane.zen.io;

import com.norcane.zen.io.exception.ResourceIOException;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ResourceLoader {

    public Resource getResource(final String location) {
        Objects.requireNonNull(location, "path must not be null");

        if (location.startsWith(ClassPathResource.PREFIX)) {
            // handle as classpath resource
            return ClassPathResource.of(location);
        } else if (location.startsWith(UrlResource.HTTP_PREFIX) || location.startsWith(UrlResource.HTTPS_PREFIX)) {
            // handle as remote URL resource
            try {
                return UrlResource.of(new URL(location));
            } catch (MalformedURLException e) {
                throw new ResourceIOException(location, e);
            }
        } else if (location.startsWith(StringResource.PREFIX)) {
            // handle as inlined string-based resource
            return StringResource.of(location);
        } else {
            // by default, consider location as file system resource
            return new FileSystemResource(Paths.get(location));
        }
    }
}
