package com.norcane.zen.io;

import com.norcane.zen.io.exception.ResourceIOException;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ResourceLoader {

    public static final String CLASSPATH_PREFIX = "classpath:";
    public static final String HTTP_PREFIX = "http:";
    public static final String HTTPS_PREFIX = "https:";
    public static final String INLINE_PREFIX = "inline:";

    public Resource getResource(final String path) {
        Objects.requireNonNull(path, "path must not be null");

        if (path.startsWith(CLASSPATH_PREFIX)) {
            // handle as classpath resource
            return new ClassPathResource(path.substring(CLASSPATH_PREFIX.length()));
        } else if (path.startsWith(HTTP_PREFIX) || path.startsWith(HTTPS_PREFIX)) {
            // handle as remote URL resource
            try {
                return new UrlResource(new URL(path));
            } catch (MalformedURLException e) {
                throw new ResourceIOException(path, e);
            }
        } else if (path.startsWith(INLINE_PREFIX)) {
            // handle as inlined string-based resource
            return new StringResource(path.substring(INLINE_PREFIX.length()));
        } else {
            // by default, consider path as file system resource
            return new FileSystemResource(Paths.get(path));
        }
    }
}
