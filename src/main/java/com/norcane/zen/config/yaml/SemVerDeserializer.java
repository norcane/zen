package com.norcane.zen.config.yaml;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import com.norcane.zen.meta.SemVer;

import java.io.IOException;

public class SemVerDeserializer extends StdDeserializer<SemVer> {

    public SemVerDeserializer() {
        this(null);
    }

    public SemVerDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public SemVer deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        final TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);
        final String version = ((TextNode) treeNode).asText();

        return SemVer.from(version);
    }
}
