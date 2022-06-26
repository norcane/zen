package com.norcane.zen.template;

import com.norcane.zen.io.Resource;

import java.util.List;

public interface TemplateFactory {

    List<String> fileExtensions();

    Template compile(Resource resource);
}
