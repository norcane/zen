package com.norcane.zen.template;


import com.norcane.zen.resource.Resource;

import java.util.Set;

public interface TemplateFactory {

    Set<String> fileExtensions();

    Template compile(Resource resource);
}
