package org.artifactory.client;

import org.artifactory.client.model.Plugin;
import org.artifactory.client.model.PluginType;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.artifactory.client.model.PluginType.executions;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author jbaruch
 * @since 11/12/12
 */
public class PluginsTests extends ArtifactoryTestsBase {

    private static final String PLUGIN_NAME = "helloWorld";

    @Test
    public void testPluginsList() {
        Map<PluginType, List<Plugin>> plugins = artifactory.plugins().list();
        assertEquals(plugins.size(), 1);
        assertTrue(plugins.containsKey(executions));
        List<Plugin> executionPlugins = plugins.get(executions);
        verifyExecutionPlugins(executionPlugins);
    }

    private void verifyExecutionPlugins(List<Plugin> executionPlugins) {
        assertEquals(executionPlugins.size(), 1);
        Plugin helloWorldPlugin = executionPlugins.get(0);
        assertEquals("helloWorld", helloWorldPlugin.getName());
        Map<String, String> params = helloWorldPlugin.getParams();
        assertEquals(params.size(), 1);
        assertEquals("world", params.get("msg"));
    }

    @Test
    public void testPluginsListByType() {
        List<Plugin> list = artifactory.plugins().list(executions);
        verifyExecutionPlugins(list);
    }

    @Test
    public void testExecutePlugin(){
        verifyExecutionResult(artifactory.plugins().execute(PLUGIN_NAME).sync(), null);

        String variable = "world";
        verifyExecutionResult(artifactory.plugins().execute(PLUGIN_NAME).withParameter("msg", variable).sync(), "["+variable+"]");
    }

    private void verifyExecutionResult(String returnValue, String variable) {
        assertEquals(returnValue, "Hello "+variable);
    }
}
