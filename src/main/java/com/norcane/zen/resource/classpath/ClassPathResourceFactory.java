package com.norcane.zen.resource.classpath;

import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.ResourceFactory;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClassPathResourceFactory implements ResourceFactory {

    private static final List<String> PREFIXES = List.of("classpath");

    @Override
    public List<String> prefixes() {
        return PREFIXES;
    }

    @Override
    public Optional<Resource> resource(final String location) {
        return Optional
            .ofNullable(getClass().getResource(location))
            .map(url -> Resource.classPath(location));
    }

    @Override
    public List<Resource> resources(final String locationPattern, final Predicate<Resource> filter) {
        throw new UnsupportedOperationException();
    }
}
