package com.norcane.zen.resource.inline;

import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.ResourceFactory;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InlineResourceFactory implements ResourceFactory {

    private static final List<String> PREFIXES = List.of("inline");

    @Override
    public List<String> getPrefixes() {
        return PREFIXES;
    }

    @Override
    public Resource getResource(final String location) {
        return InlineResource.of(location);
    }

    @Override
    public List<Resource> getResources(String location) {
        throw new UnsupportedOperationException();
    }
}
