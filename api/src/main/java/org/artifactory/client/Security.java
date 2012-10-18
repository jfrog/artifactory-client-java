package org.artifactory.client;

import org.artifactory.client.model.User;
import org.artifactory.client.model.builder.SecurityBuilders;

import java.util.Collection;

/**
 * Date: 10/18/12
 * Time: 9:25 AM
 *
 * @author freds
 */
public interface Security {

    final static String SECURITY_API = "/api/security/";

    SecurityBuilders builders();

    Collection<String> userNames();

    User user(String name);

    String createOrUpdate(User user);

    String deleteUser(String name);
}
