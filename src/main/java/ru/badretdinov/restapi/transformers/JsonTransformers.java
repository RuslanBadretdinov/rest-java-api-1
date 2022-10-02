package ru.badretdinov.restapi.transformers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import spark.ResponseTransformer;

public final class JsonTransformers implements ResponseTransformer {
    public static final JsonTransformers JSON = new JsonTransformers();

    @Override
    public String render(Object response) throws Exception {
        try {
            return (new ObjectMapper().writeValueAsString(response));
        } catch (JsonProcessingException exc) {
            return null;
        }
    }
}
