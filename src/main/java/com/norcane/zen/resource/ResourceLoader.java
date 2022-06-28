package com.norcane.zen.resource;

import java.util.List;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class ResourceLoader {

    private static final String PREFIX_SEPARATOR = ":";

    private final Instance<ResourceFactory> factories;
    private final ResourceFactory defaultFactory;

    @Inject
    public ResourceLoader(final Instance<ResourceFactory> factories,
                          @DefaultResourceFactory final ResourceFactory defaultFactory) {

        this.factories = Objects.requireNonNull(factories);
        this.defaultFactory = Objects.requireNonNull(defaultFactory);
    }

    public Resource getResource(final String location) {
        final ResourceFactory selectedFactory = factories
            .stream()
            .filter(factory -> factory.getPrefixes().stream().anyMatch(location::startsWith))
            .findAny().orElse(defaultFactory);

        return selectedFactory.getResource(removePrefixes(location, selectedFactory.getPrefixes()));
    }

    public List<Resource> getResources(final String location) {
        // FIXME implement
        throw new UnsupportedOperationException("not implemented yet");
    }

    protected String removePrefixes(String location, List<String> possiblePrefixes) {
        return possiblePrefixes
            .stream()
            .map(prefix -> prefix + PREFIX_SEPARATOR)
            .filter(location::startsWith)
            .findAny()
            .map(s -> location.substring(s.length()))
            .orElse(location);
    }
}
