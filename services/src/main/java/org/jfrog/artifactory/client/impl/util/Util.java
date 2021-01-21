package org.jfrog.artifactory.client.impl.util;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static com.fasterxml.jackson.databind.introspect.VisibilityChecker.Std.defaultInstance;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.jfrog.artifactory.client.ArtifactoryRequest;
import org.jfrog.artifactory.client.impl.jackson.RepositoryMixIn;
import org.jfrog.artifactory.client.impl.jackson.RepositorySettingsMixIn;
import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.Repository;
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings;

/**
 * @author Alexei Vainshtein
 */
public class Util {

    public static <T> T responseToObject(HttpResponse httpResponse, Class<? extends T> object, Class<T> interfaceClass) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        configureObjectMapper(objectMapper);

        if (interfaceClass != null) {
            SimpleModule module = new SimpleModule("CustomModel", Version.unknownVersion());
            SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver();
            resolver.addMapping(interfaceClass, object);
            module.setAbstractTypes(resolver);
            objectMapper.registerModule(module);
        }

        String content = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
        return objectMapper.readValue(content, object);
    }

    public static void configureObjectMapper(ObjectMapper objectMapper) {
        objectMapper.addMixIn(Repository.class, RepositoryMixIn.class);
        objectMapper.addMixIn(RepositorySettings.class, RepositorySettingsMixIn.class);
        objectMapper.configure(WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setVisibility(defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static String responseToString(HttpResponse httpResponse) throws IOException {
        if (httpResponse.getEntity() != null) {
            try(InputStream in = httpResponse.getEntity().getContent();) {
                return IOUtils.toString(in, "UTF-8");
            }
        }
        return null;
    }

    public static String getStringFromObject(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        configureObjectMapper(objectMapper);
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        return objectMapper.writeValueAsString(object);
    }

    public static ContentType getContentType(ArtifactoryRequest.ContentType contentType)  {
        switch(contentType){
            case JSON:
                return ContentType.APPLICATION_JSON;
            case JOSE:
                return ContentType.create("application/jose", Consts.ISO_8859_1);
            case JOSE_JSON:
                return ContentType.create("application/jose+json", Consts.UTF_8);
            case TEXT:
                return ContentType.TEXT_PLAIN;
            case URLENC:
                return ContentType.APPLICATION_FORM_URLENCODED;
            case XML:
                return ContentType.APPLICATION_XML;
            case YAML:
                return ContentType.create("application/yaml", Consts.UTF_8);
            case ANY:
                return ContentType.WILDCARD;
            default:
                throw new IllegalArgumentException("Not a valid Content Type - " + contentType);
        }
    }

    public static <T> T parseText(String text, Class<? extends T> target) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        configureObjectMapper(objectMapper);
        return objectMapper.readValue(text, target);
    }

    public static <T> T parseObjectWithTypeReference(String content, TypeReference<T> typeReference) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        configureObjectMapper(objectMapper);
        return objectMapper.readValue(content, typeReference);
    }

    public static String encodeParams(String param) throws UnsupportedEncodingException {
        return URLEncoder.encode(param, "UTF-8");
    }

    public static String getQueryPath(String startingParam, Map<String, String> paramsMap) throws UnsupportedEncodingException {
        StringBuilder queryPath = new StringBuilder(startingParam);
        Iterator<Map.Entry<String, String>> it = paramsMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pair = it.next();
            String key = pair.getKey();
            String value = pair.getValue();
            queryPath.append(encodeParams(key)).append("=").append(Util.encodeParams(value));
            if(it.hasNext()){
                queryPath.append("&");
            }
        }
        return queryPath.toString();
    }

    public static Class<? extends RepositorySettings> getRepositorySettingsClassForPackageType(PackageType packageType) {
        JsonSubTypes annotation = RepositorySettingsMixIn.class.getDeclaredAnnotation(JsonSubTypes.class);

        for (JsonSubTypes.Type type : annotation.value()) {
            if (type.name().equals(packageType.name())) {
                return (Class<? extends RepositorySettings>)type.value();
            }
        }

        return null;
    }

}
