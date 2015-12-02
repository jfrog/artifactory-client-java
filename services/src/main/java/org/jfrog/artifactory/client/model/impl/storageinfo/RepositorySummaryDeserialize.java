package org.jfrog.artifactory.client.model.impl.storageinfo;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jfrog.artifactory.client.model.RepositorySummary;

import java.io.IOException;
import java.util.List;

/**
 * @author Aviad Shikloshi
 */
public class RepositorySummaryDeserialize extends JsonDeserializer<List<RepositorySummary>> {

    @Override
    public List<RepositorySummary> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        List<RepositorySummary> repositorySummary = mapper.readValue(jp, new TypeReference<List<RepositorySummaryImpl>>() {});

        return repositorySummary;

    }
}
