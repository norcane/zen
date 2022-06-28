package com.norcane.zen.resource.classpath;

import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.ResourceFactory;
import com.norcane.zen.resource.exception.ResourceNotFoundException;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClassPathResourceFactory implements ResourceFactory {

    private static final List<String> PREFIXES = List.of("classpath");

    @Override
    public List<String> getPrefixes() {
        return PREFIXES;
    }

    @Override
    public Resource getResource(final String location) {
        if (getClass().getResource(location) == null) {
            throw new ResourceNotFoundException(location);
        }

        return ClassPathResource.of(location);
    }

    @Override
    public List<Resource> getResources(final String location) {
        throw new UnsupportedOperationException();
    }
}
