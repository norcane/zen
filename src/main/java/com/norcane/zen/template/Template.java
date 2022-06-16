package com.norcane.zen.template;

import java.io.Writer;
import java.util.Map;

public interface Template {

    String getName();

    Writer render(Writer writer, Map<String, Object> variables);
}
