package org.jfrog.artifactory.client;

/**
 * @author jbaruch
 * @since 12/08/12
 */
public class ProxyConfig {
    /**
     * Host name or IP
     */
    private String host;
    /**
     * Port, or -1 for the default port
     */
    private int port;
    /**
     * Usually "http" or "https," or <code>null</code> for the default
     */
    private String scheme;
    /**
     * Proxy user.
     */
    private String user;
    /**
     * Proxy password
     */
    private String password;

    public ProxyConfig(String host, int port, String scheme, String user, String password) {
        this.host = host;
        this.port = port;
        this.scheme = scheme;
        this.user = user;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getScheme() {
        return scheme;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
