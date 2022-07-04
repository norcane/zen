package com.norcane.zen.resource;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface ResourceFactory {

    List<String> prefixes();

    Optional<Resource> resource(String location);

    List<Resource> resources(String locationPattern, Predicate<Resource> filter);
}
