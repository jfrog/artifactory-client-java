package org.jfrog.artifactory.client
/**
 * @author jbaruch
 * @since 12/08/12
 */
public class ArtifactoryClient {

   /**
    * @deprecated use {@link ArtifactoryClientBuilder}
    */
    @Deprecated
    public static Artifactory create(
            String url,
            String username = null,
            String password = null,
            Integer connectionTimeout = null,
            Integer socketTimeout = null,
            ProxyConfig proxy = null,
            userAgent = null,
            ignoreSSLIssues = false,
            String accessToken = null) {

        return ArtifactoryClientBuilder.create()
                .setUrl(url)
                .setUsername(username)
                .setPassword(password)
                .setConnectionTimeout(connectionTimeout)
                .setSocketTimeout(socketTimeout)
                .setProxy(proxy)
                .setUserAgent(userAgent)
                .setIgnoreSSLIssues(ignoreSSLIssues)
                .setAccessToken(accessToken)
                .build()

    }

    public static class ProxyConfig {
        /**
         * Host name or IP
         */
        private String host
        /**
         * Port, or -1 for the default port
         */
        private int port
        /**
         * Usually "http" or "https," or <code>null</code> for the default
         */
        private String scheme
        /**
         * Proxy user.
         */
        private String user
        /**
         * Proxy password
         */
        private String password

        ProxyConfig(String host, int port, String scheme, String user, String password) {
            this.host = host
            this.port = port
            this.scheme = scheme
            this.user = user
            this.password = password
        }

        String getHost() {
            return host
        }

        int getPort() {
            return port
        }

        String getScheme() {
            return scheme
        }

        String getUser() {
            return user
        }

        String getPassword() {
            return password
        }
    }
}
