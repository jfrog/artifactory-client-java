package org.artifactory.client;

import java.util.Map;
import java.util.Set;

/**
 * @author jbaruch
 * @since 12/08/12
 */
public interface ItemHandle {

    public <T> T get();

    public Map<String, ?> getProps();

    public Map<String, ?> getProps(Set props);

    boolean isFolder();

    public <T> T setProps(Map<String, ?> props);

    public <T> T setProps(Map<String, ?> props, boolean recursive);

    public Map<String, ?> deleteProps(Set props);

    public Map<String, ?> deleteProps();
}