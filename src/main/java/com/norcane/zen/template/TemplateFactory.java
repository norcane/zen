package com.norcane.zen.template;

import java.io.Reader;
import java.util.List;

public interface TemplateFactory {

    List<String> fileExtensions();

    Template compile(String name, Reader reader);
}
