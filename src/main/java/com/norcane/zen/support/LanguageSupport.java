package com.norcane.zen.support;

import com.norcane.zen.resource.Resource;
import com.norcane.zen.source.SourceCode;

import java.util.Set;

public interface LanguageSupport {

    String name();

    Set<String> resourceTypes();

    SourceCode analyze(Resource resource);
}
