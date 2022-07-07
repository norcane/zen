package com.norcane.zen.resource;

import com.norcane.zen.resource.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class ResourceManager {

    private static final String PREFIX_SEPARATOR = ":";

    private final Instance<ResourceFactory> factories;
    private final ResourceFactory defaultFactory;

    @Inject
    public ResourceManager(final Instance<ResourceFactory> factories,
                           @DefaultResourceFactory final ResourceFactory defaultFactory) {

        this.factories = Objects.requireNonNull(factories);
        this.defaultFactory = Objects.requireNonNull(defaultFactory);
    }

    public Resource resource(final String location) {
        final ResourceFactory factory = findFactory(location);
        return factory
            .resource(removePrefixes(location, factory.prefixes()))
            .orElseThrow(() -> new ResourceNotFoundException(location));
    }

    public List<Resource> resources(final String locationPattern) {
        final ResourceFactory factory = findFactory(locationPattern);
        return factory.resources(removePrefixes(locationPattern, factory.prefixes()), resource -> true);
    }

    protected String removePrefixes(final String location, final List<String> possiblePrefixes) {
        return possiblePrefixes
            .stream()
            .map(prefix -> prefix + PREFIX_SEPARATOR)
            .filter(location::startsWith)
            .findAny()
            .map(s -> location.substring(s.length()))
            .orElse(location);
    }

    private ResourceFactory findFactory(final String location) {
        return factories
            .stream()
            .filter(factory -> factory.prefixes().stream().anyMatch(location::startsWith))
            .findAny().orElse(defaultFactory);
    }
}
