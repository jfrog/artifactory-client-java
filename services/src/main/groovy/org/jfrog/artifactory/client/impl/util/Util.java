package org.jfrog.artifactory.client.impl.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.jfrog.artifactory.client.ArtifactoryRequest;
import org.jfrog.artifactory.client.impl.jackson.RepositoryMixIn;
import org.jfrog.artifactory.client.impl.jackson.RepositorySettingsMixIn;
import org.jfrog.artifactory.client.model.Repository;
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static com.fasterxml.jackson.databind.introspect.VisibilityChecker.Std.defaultInstance;

/**
 * @author Alexei Vainshtein
 */
public class Util {

    public static <T> T responseToObject(HttpResponse httpResponse, Class<? extends T> object, Class<T> interfaceClass) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.addMixIn(Repository.class, RepositoryMixIn.class);
        objectMapper.addMixIn(RepositorySettings.class, RepositorySettingsMixIn.class);

        objectMapper.configure(WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setVisibilityChecker(defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        if (interfaceClass != null) {
            SimpleModule module = new SimpleModule("CustomModel", Version.unknownVersion());
            SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver();
            resolver.addMapping(interfaceClass, object);
            module.setAbstractTypes(resolver);
            objectMapper.registerModule(module);
        }

        return objectMapper.readValue(httpResponse.getEntity().getContent(), object);
    }

    public static String responseToString(HttpResponse httpResponse) throws IOException {
        if (httpResponse.getEntity() != null) {
            InputStream in = httpResponse.getEntity().getContent();
            try {
                return IOUtils.toString(in, "UTF-8");
            } finally {
                IOUtils.closeQuietly(in);
            }
        }
        return null;
    }

    public static String getStringFromObject(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.addMixIn(Repository.class, RepositoryMixIn.class);
        objectMapper.configure(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return objectMapper.writeValueAsString(object);
    }

    public static ContentType getContentType(ArtifactoryRequest.ContentType contentType) throws Exception {

        if (contentType.equals(ArtifactoryRequest.ContentType.JSON)) {
            return ContentType.APPLICATION_JSON;
        }

        if (contentType.equals(ArtifactoryRequest.ContentType.JSON)) {
            return ContentType.create("application/jose+json", Consts.ISO_8859_1);
        }

        if (contentType.equals(ArtifactoryRequest.ContentType.TEXT)) {
            return ContentType.TEXT_PLAIN;
        }

        if (contentType.equals(ArtifactoryRequest.ContentType.URLENC)) {
            return ContentType.APPLICATION_FORM_URLENCODED;
        }

        if (contentType.equals(ArtifactoryRequest.ContentType.ANY)) {
            return ContentType.WILDCARD;
        }

        throw new Exception("Non valid Content Type");

    }

    public static <T> T parseText(String text, Class<? extends T> target) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.addMixIn(Repository.class, RepositoryMixIn.class);
        objectMapper.addMixIn(RepositorySettings.class, RepositorySettingsMixIn.class);
        return objectMapper.readValue(text, target);
    }

    public static <T> T parseObjectWithTypeReference(String content, TypeReference typeReference) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(content, typeReference);
    }

    public static String encodeParams(String param) throws UnsupportedEncodingException {
        return URLEncoder.encode(param, "UTF-8");
    }

    public static String getQueryPath(String startingParam, Set<Map.Entry<String, String>> paramsMap) throws UnsupportedEncodingException {
        StringBuilder queryPath = new StringBuilder(startingParam);
        Iterator it = paramsMap.iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String key = (String) pair.getKey();
            String value = (String) pair.getValue();
            queryPath.append(encodeParams(key)).append("=").append(Util.encodeParams(value)).append("&");
        }

        return queryPath.toString();
    }
}
