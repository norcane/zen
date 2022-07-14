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
        final String[] chunks = location.split(":", 3);

        if (chunks.length != 3) {
            throw new IllegalArgumentException("Illegal inline resource '%s', expected format NAME:TYPE:CONTENT".formatted(location));
        }

        return Optional.of(Resource.inline(chunks[0], chunks[1], chunks[2]));
    }

    @Override
    public List<Resource> resources(String locationPattern, Predicate<Resource> filter) {
        throw new UnsupportedOperationException();
    }
}
