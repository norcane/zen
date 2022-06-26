package com.norcane.zen.io;

import com.norcane.zen.io.exception.ResourceIOException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class UrlResource implements Resource {

    private final URL url;
    private final String path;

    public UrlResource(final URL url) {
        this.url = url;
        this.path = url.toString();
    }


    @Override
    public boolean exists() {
        try {
            final HttpURLConnection connection = (HttpURLConnection) this.url.openConnection();
            return HttpURLConnection.HTTP_OK == connection.getResponseCode();

        } catch (IOException e) {
            throw new ResourceIOException(this.path, e);
        }
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public String readAsString() {
        try (final InputStream stream = this.url.openStream()) {
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new ResourceIOException(this.path, e);
        }
    }
}
