package io.aetherit.ats.ws.util;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

public class JsonUtil {
    private boolean indent;
    private boolean dateAsTimestamp;

    public JsonUtil() {
        this(false, false);
    }

    public JsonUtil(boolean indent, boolean dateAsTimestamp) {
        this.indent = indent;
        this.dateAsTimestamp = dateAsTimestamp;
    }

    public <T> T getObjectFromJson(String json, Class<T> type) throws IOException {
        ObjectMapper mapper = getObjectMapper();

        return mapper.readValue(json, type);
    }

    public <T> T getObjectFromFile(File file, Class<T> type) throws JsonProcessingException, IOException {
        ObjectMapper mapper = getObjectMapper();

        return mapper.readValue(file, type);
    }

    public <E, T extends Collection<E>> T getCollectionFromJson(String json, Class<T> collectionType, Class<E> elementType) throws JsonProcessingException, IOException {
        ObjectMapper mapper = getObjectMapper();
        JavaType type = mapper.getTypeFactory().constructCollectionType(collectionType, elementType);

        return mapper.readValue(json, type);
    }

    public <E, T extends Collection<E>> T getCollectionFromFile(File file, Class<T> collectionType, Class<E> elementType) throws JsonProcessingException, IOException {
        ObjectMapper mapper = getObjectMapper();
        JavaType type = mapper.getTypeFactory().constructCollectionType(collectionType, elementType);

        return mapper.readValue(file, type);
    }

    public String getJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = getObjectMapper();

        return mapper.writeValueAsString(obj);
    }

    public void writeToFile(File file, Object obj) throws JsonProcessingException, IOException {
        ObjectMapper mapper = getObjectMapper();

        mapper.writeValue(file, obj);
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());

        if(this.indent) {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
        } else {
            mapper.disable(SerializationFeature.INDENT_OUTPUT);
        }

        if(this.dateAsTimestamp) {
            mapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        } else {
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        }

        return mapper;
    }
}

