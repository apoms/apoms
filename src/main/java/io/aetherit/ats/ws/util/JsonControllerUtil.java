package io.aetherit.ats.ws.util;

import java.util.Collection;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class JsonControllerUtil {
    private JsonUtil jsonUtil;

    public JsonControllerUtil(JsonUtil jsonUtil) {
        this.jsonUtil = jsonUtil;
    }

    public ResponseEntity<String> getResponseEntity() {
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    public ResponseEntity<String> getResponseEntity(HttpStatus status) {
        return new ResponseEntity<String>(status);
    }

    public ResponseEntity<String> getJsonResponseEntity(Object obj) {
        return new ResponseEntity<String>(getJsonString(obj), getHttpHeaders("application/json"), HttpStatus.OK);
    }

    public <T extends Resource> ResponseEntity<T> getResourceResponseEntity(T resource, String contentType) {
        return new ResponseEntity<T>(resource, getHttpHeaders(contentType), HttpStatus.OK);
    }

    public String getJsonString(Object obj) {
        String jsonStr = null;

        try {
            jsonStr = jsonUtil.getJsonString(obj);
        } catch(Exception e) {
            throw new RuntimeException("Can't convert " + (obj != null ? obj.getClass().getSimpleName() : "null") + " to json string.", e);
        }

        return jsonStr;
    }

    public <T> T getObjectFromJson(String json, Class<T> type) {
        T obj = null;

        try {
            obj = jsonUtil.getObjectFromJson(json, type);
        } catch(Exception e) {
            throw new RuntimeException("Can't convert json string to " + (type != null ? type.getSimpleName() : "null"), e);
        }

        return obj;
    }

    public <E, T extends Collection<E>> T getCollectionFromJson(String json, Class<T> collectionType, Class<E> elementType) {
        T collection = null;

        try {
            collection = jsonUtil.getCollectionFromJson(json, collectionType, elementType);
        } catch(Exception e) {
            throw new RuntimeException("Can't convert json string to " + (collectionType != null ? collectionType.getSimpleName() : "null") + " of " + (elementType != null ? elementType.getSimpleName() : "null"));
        }

        return collection;
    }

    private HttpHeaders getHttpHeaders(String contentType) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", contentType);

        return headers;
    }
}
