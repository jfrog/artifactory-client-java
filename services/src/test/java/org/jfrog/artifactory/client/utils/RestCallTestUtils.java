package org.jfrog.artifactory.client.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class RestCallTestUtils {

    public Map<String, Object> createPermissionTargetBody(String permissionName) throws IOException {
        String json =
                "{" +
                        "\"name\" : \"" + permissionName + "\"," +
                        "\"includesPattern\" : \"**\"," +
                        "\"excludesPattern\" : \"\"," +
                        "\"repositories\" : [ \"ANY\" ]," +
                        "\"principals\" : {" +
                        "\"users\" : {" +
                        "\"anonymous\" : [ \"r\" ]" +
                        "}," +
                        "\"groups\" : {" +
                        "\"readers\" : [ \"r\" ]" +
                        "}" +
                        "}" +
                        "}";

        return new ObjectMapper().readValue(json, Map.class);
    }

    public boolean findPermissionInList(List list, String permissionName) {
        for(Object permission : list) {
            Object name = ((Map)permission).get("name");
            if (permissionName.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public Map<String, Object> createBuildBody() throws IOException {
        String buildStarted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(System.currentTimeMillis());
        String buildInfoJson = IOUtils.toString(this.getClass().getResourceAsStream("/build.json"), "UTF-8");
        buildInfoJson = StringUtils.replace(buildInfoJson, "{build.start.time}", buildStarted);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(buildInfoJson, Map.class);
    }
}
