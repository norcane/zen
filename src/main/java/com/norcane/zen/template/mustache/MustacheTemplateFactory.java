package com.norcane.zen.template.mustache;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.norcane.zen.resource.Resource;
import com.norcane.zen.template.Template;
import com.norcane.zen.template.TemplateFactory;

import java.io.StringReader;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MustacheTemplateFactory implements TemplateFactory {

    private static final Set<String> FILE_EXTENSIONS = Set.of("mustache");

    private static final MustacheFactory mustacheFactory = new DefaultMustacheFactory();

    @Override
    public Set<String> fileExtensions() {
        return FILE_EXTENSIONS;
    }

    @Override
    public Template compile(Resource resource) {
        final Mustache compiled = mustacheFactory.compile(new StringReader(resource.readAsString()), resource.location());

        return new MustacheTemplate(resource.location(), compiled);
    }
}
