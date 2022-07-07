package com.norcane.zen.template;


import com.norcane.zen.resource.Resource;

public interface TemplateFactory {

    String templateType();

    Template compile(Resource resource);
}
