package com.norcane.zen.resource.inline;

import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.ResourceFactory;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InlineResourceFactory implements ResourceFactory {

    private static final List<String> PREFIXES = List.of("inline");

    @Override
    public List<String> prefixes() {
        return PREFIXES;
    }

    @Override
    public Optional<Resource> resource(final String location) {
        return Optional.of(InlineResource.of(location));
    }

    @Override
    public List<Resource> resources(String locationPattern, Predicate<Resource> filter) {
        throw new UnsupportedOperationException();
    }
}
