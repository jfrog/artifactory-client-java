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
import org.jfrog.artifactory.client.model.AllBuilds;
import org.jfrog.artifactory.client.model.BuildPromotionResponse;
import org.jfrog.artifactory.client.model.BuildRuns;
import org.jfrog.artifactory.client.model.PackageType;
import org.jfrog.artifactory.client.model.Repository;
import org.jfrog.artifactory.client.model.impl.AllBuildsImpl;
import org.jfrog.artifactory.client.model.impl.BuildPromotionResponseImpl;
import org.jfrog.artifactory.client.model.impl.BuildRunsImpl;
import org.jfrog.artifactory.client.model.repository.settings.RepositorySettings;

/**
 * @author Alexei Vainshtein
 */
public class Util {

    /**
     * Shared, fully-configured, thread-safe {@link ObjectMapper}.
     *
     * <p>Jackson's ObjectMapper is heavyweight at construction time (module scanning,
     * type-factory setup, annotation-introspector initialisation) and thread-safe after
     * configuration. All production code in this library must use this instance rather than
     * creating a new one per call.
     *
     * <p>Configuration applied once at class-load time:
     * <ul>
     *   <li>Mix-ins for {@code Repository} and {@code RepositorySettings} (required for
     *       correct polymorphic serialisation of repository types)</li>
     *   <li>Abstract-type mappings for all known API interface→impl pairs (replaces the
     *       per-call {@link SimpleModule} that was previously registered inside
     *       {@link #responseToObject})</li>
     *   <li>Standard feature flags ({@code FAIL_ON_UNKNOWN_PROPERTIES=false}, etc.)</li>
     *   <li>{@code INDENT_OUTPUT=true} (matches previous per-call behaviour of
     *       {@link #getStringFromObject})</li>
     * </ul>
     */
    public static final ObjectMapper CONFIGURED_MAPPER = createConfiguredMapper();

    private static ObjectMapper createConfiguredMapper() {
        ObjectMapper om = new ObjectMapper();

        // Mix-ins
        om.addMixIn(Repository.class, RepositoryMixIn.class);
        om.addMixIn(RepositorySettings.class, RepositorySettingsMixIn.class);

        // Feature flags
        om.configure(WRITE_DATES_AS_TIMESTAMPS, false);
        om.setVisibility(defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        om.configure(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS, false);
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        om.configure(SerializationFeature.INDENT_OUTPUT, true);

        // Pre-register all known interface→impl abstract-type mappings.
        // Previously a new SimpleModule was created and registered on every call to
        // responseToObject(); pre-registering them here removes that per-call overhead.
        SimpleModule module = new SimpleModule("ClientModel", Version.unknownVersion());
        SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver();
        resolver.addMapping(AllBuilds.class, AllBuildsImpl.class);
        resolver.addMapping(BuildRuns.class, BuildRunsImpl.class);
        resolver.addMapping(BuildPromotionResponse.class, BuildPromotionResponseImpl.class);
        module.setAbstractTypes(resolver);
        om.registerModule(module);

        return om;
    }

    /**
     * Deserialise an HTTP response body into an instance of {@code object}.
     *
     * <p>The {@code interfaceClass} parameter is retained for binary compatibility but is no
     * longer used — all known interface→impl mappings are pre-registered on
     * {@link #CONFIGURED_MAPPER}.
     *
     * @param httpResponse   the raw HTTP response whose entity will be read
     * @param object         the concrete class to deserialise into
     * @param interfaceClass unused; kept for binary compatibility
     */
    public static <T> T responseToObject(HttpResponse httpResponse, Class<? extends T> object,
            Class<T> interfaceClass) throws IOException {
        String content = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
        return CONFIGURED_MAPPER.readValue(content, object);
    }

    /**
     * Configure an external {@link ObjectMapper} with the same settings as
     * {@link #CONFIGURED_MAPPER}.
     *
     * @deprecated Use {@link #CONFIGURED_MAPPER} directly. This method mutates a shared mapper
     *             on every call and will be removed in a future major version.
     */
    @Deprecated
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
            try (InputStream in = httpResponse.getEntity().getContent()) {
                return IOUtils.toString(in, "UTF-8");
            }
        }
        return null;
    }

    /**
     * Serialise {@code object} to a pretty-printed JSON string.
     */
    public static String getStringFromObject(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        return CONFIGURED_MAPPER.writeValueAsString(object);
    }

    public static ContentType getContentType(ArtifactoryRequest.ContentType contentType) {
        switch (contentType) {
            case JSON:
                return ContentType.APPLICATION_JSON;
            case JOSE:
                return ContentType.create("application/jose", Consts.ISO_8859_1);
            case JOSE_JSON:
                return ContentType.create("application/jose+json", Consts.UTF_8);
            case TEXT:
                return ContentType.create("text/plain", Consts.UTF_8);
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
        return CONFIGURED_MAPPER.readValue(text, target);
    }

    public static <T> T parseObjectWithTypeReference(String content,
            TypeReference<T> typeReference) throws IOException {
        return CONFIGURED_MAPPER.readValue(content, typeReference);
    }

    public static String encodeParams(String param) throws UnsupportedEncodingException {
        return URLEncoder.encode(param, "UTF-8");
    }

    public static String getQueryPath(String startingParam,
            Map<String, String> paramsMap) throws UnsupportedEncodingException {
        StringBuilder queryPath = new StringBuilder(startingParam);
        Iterator<Map.Entry<String, String>> it = paramsMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pair = it.next();
            String key = pair.getKey();
            String value = pair.getValue();
            queryPath.append(encodeParams(key)).append("=").append(Util.encodeParams(value));
            if (it.hasNext()) {
                queryPath.append("&");
            }
        }
        return queryPath.toString();
    }

    public static Class<? extends RepositorySettings> getRepositorySettingsClassForPackageType(
            PackageType packageType) {
        JsonSubTypes annotation = RepositorySettingsMixIn.class.getDeclaredAnnotation(JsonSubTypes.class);
        for (JsonSubTypes.Type type : annotation.value()) {
            if (type.name().equals(packageType.name())) {
                return (Class<? extends RepositorySettings>) type.value();
            }
        }
        return null;
    }
}
