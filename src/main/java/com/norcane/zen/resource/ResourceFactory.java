package com.norcane.zen.resource;

import java.util.List;

public interface ResourceFactory {

    List<String> getPrefixes();

    Resource getResource(String location);

    List<Resource> getResources(String location);
}
