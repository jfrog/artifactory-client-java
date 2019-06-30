package org.jfrog.artifactory.client.httpClient.http;

import org.apache.commons.lang.StringUtils;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultRoutePlanner;
import org.apache.http.impl.conn.DefaultSchemePortResolver;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.jfrog.artifactory.client.httpClient.http.auth.ProxyPreemptiveAuthInterceptor;

import javax.net.ssl.SSLContext;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Base builder for HTTP client.
 *
 * @author Yossi Shaul
 */
public abstract class HttpBuilderBase<T extends HttpBuilderBase> {

    protected HttpClientBuilder builder = HttpClients.custom();
    private RequestConfig.Builder config = RequestConfig.custom();
    private HttpHost defaultHost;
    private BasicCredentialsProvider credsProvider;
    private JFrogAuthScheme chosenAuthScheme = JFrogAuthScheme.BASIC; //Signifies what auth scheme will be used by the client
    private boolean cookieSupportEnabled = false;
    private boolean trustSelfSignCert = false;
    private SSLContextBuilder sslContextBuilder;
    private int maxConnectionsTotal = DEFAULT_MAX_CONNECTIONS;
    private int maxConnectionsPerRoute = DEFAULT_MAX_CONNECTIONS;
    private int connectionPoolTimeToLive = CONNECTION_POOL_TIME_TO_LIVE;

    HttpBuilderBase() {
        credsProvider = new BasicCredentialsProvider();
        config.setMaxRedirects(20);
    }

    public CloseableHttpClient build() {
        if (defaultHost != null) {
            builder.setRoutePlanner(new DefaultHostRoutePlanner(defaultHost));
        }
        PoolingHttpClientConnectionManager connectionMgr = configConnectionManager();
        CloseableHttpClientDecorator client = new CloseableHttpClientDecorator(builder.build(), connectionMgr,
                chosenAuthScheme == JFrogAuthScheme.SPNEGO);
        return client;
    }

    private T self() {
        return (T) this;
    }

    /**
     * Sets the User-Agent value
     */
    public T userAgent(String userAgent) {
        builder.setUserAgent(userAgent);
        return self();
    }

    /**
     * Sets the host the client works with by default. This method accepts any valid {@link URL} formatted string.
     * This will extract the schema, host and port to use by default.
     *
     * @throws IllegalArgumentException if the given URL is invalid
     */
    public T hostFromUrl(String urlStr) {
        if (StringUtils.isNotBlank(urlStr)) {
            try {
                URL url = new URL(urlStr);
                defaultHost = new HttpHost(url.getHost(), url.getPort(), url.getProtocol());
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException("Cannot parse the url " + urlStr, e);
            }
        } else {
            defaultHost = null;
        }
        return self();
    }

    public T maxConnectionsPerRoute(int maxConnectionsPerHost) {
        this.maxConnectionsPerRoute = maxConnectionsPerHost;
        return self();
    }

    public T maxTotalConnections(int maxTotalConnections) {
        this.maxConnectionsTotal = maxTotalConnections;
        return self();
    }

    public T connectionTimeout(int connectionTimeout) {
        config.setConnectTimeout(connectionTimeout);
        return self();
    }

    public T socketTimeout(int soTimeout) {
        config.setSocketTimeout(soTimeout);
        return self();
    }

    public T addInterceptorLast(HttpRequestInterceptor httpRequestInterceptor) {
        builder.addInterceptorLast(httpRequestInterceptor);
        return self();
    }

    /**
     * How long to keep connections alive for reuse purposes before ditching them
     *
     * @param seconds Time to live in seconds
     */
    public T connectionPoolTTL(int seconds) {
        this.connectionPoolTimeToLive = seconds;
        return self();
    }

    /**
     * @param trustSelfSignCert Trust self signed certificates on SSL handshake
     * @return {@link T}
     */
    public T trustSelfSignCert(boolean trustSelfSignCert) {
        this.trustSelfSignCert = trustSelfSignCert;
        return self();
    }

    /**
     * @param sslContextBuilder SSLContext builder
     * @return {@link T}
     */
    public T sslContextBuilder(SSLContextBuilder sslContextBuilder) {
        this.sslContextBuilder = sslContextBuilder;
        return self();
    }

    /**
     * Configures preemptive authentication on this client. Ignores blank username input.
     */
    public T authentication(String username, String password) {
        return authentication(username, password, false);
    }

    /**
     * Configures preemptive authentication on this client. Ignores blank username input.
     */
    public T authentication(String username, String password, boolean allowAnyHost) {
        if (StringUtils.isNotBlank(username)) {
            if (defaultHost == null || StringUtils.isBlank(defaultHost.getHostName())) {
                throw new IllegalStateException("Cannot configure authentication when host is not set.");
            }
            AuthScope authscope = allowAnyHost ?
                    new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM) :
                    new AuthScope(defaultHost.getHostName(), AuthScope.ANY_PORT, AuthScope.ANY_REALM);
            credsProvider.setCredentials(authscope, new UsernamePasswordCredentials(username, password));
        }
        return self();
    }

    public HttpBuilderBase.ProxyConfigBuilder proxy(String host, int port) {
        return new HttpBuilderBase.ProxyConfigBuilder(host, port);
    }

    public class ProxyConfigBuilder {
        private final String proxyHost;
        private final int proxyPort;
        Credentials creds;

        public ProxyConfigBuilder(String host, int port) {
            this.proxyHost = host;
            this.proxyPort = port;
            config.setProxy(new HttpHost(host, port));
        }

        public HttpBuilderBase.ProxyConfigBuilder authentication(String username, String password) {
            creds = new UsernamePasswordCredentials(username, password);
            //This will demote the NTLM authentication scheme so that the proxy won't barf
            //when we try to give it traditional credentials. If the proxy doesn't do NTLM
            //then this won't hurt it (jcej at tragus dot org)
            List<String> authPrefs = Arrays.asList(AuthSchemes.DIGEST, AuthSchemes.BASIC, AuthSchemes.NTLM);
            config.setProxyPreferredAuthSchemes(authPrefs);

            // preemptive proxy authentication
            builder.addInterceptorFirst(new ProxyPreemptiveAuthInterceptor());
            setProxyCreds(proxyHost, proxyPort);
            return this;
        }

        private void setProxyCreds(String host, int port) {
            if (StringUtils.isBlank(proxyHost) || port == 0) {
                throw new IllegalStateException("Proxy host and port must be set before creating authentication");
            }
            credsProvider.setCredentials(new AuthScope(host, port, AuthScope.ANY_REALM), creds);
        }
    }

    public boolean isCookieSupportEnabled() {
        return cookieSupportEnabled;
    }

    /**
     * Produces a {@link ConnectionKeepAliveStrategy}
     *
     * @return keep-alive strategy to be used for connection pool
     */
    public static ConnectionKeepAliveStrategy createConnectionKeepAliveStrategy() {
        return new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                // Honor 'keep-alive' header
                HeaderElementIterator it = new BasicHeaderElementIterator(
                        response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        try {
                            return Long.parseLong(value) * 1000;
                        } catch (NumberFormatException ignore) {
                        }
                    }
                }
                return 30 * 1000;
            }
        };
    }

    protected PoolingHttpClientConnectionManager configConnectionManager() {
        if (!isCookieSupportEnabled()) {
            builder.disableCookieManagement();
        }
        if (hasCredentials()) {
            builder.setDefaultCredentialsProvider(credsProvider);
        }
        RequestConfig defaultRequestConfig = config.build();
        builder.setDefaultRequestConfig(defaultRequestConfig);

        /**
         * Connection management
         */
        builder.setKeepAliveStrategy(createConnectionKeepAliveStrategy());

        builder.setMaxConnTotal(maxConnectionsTotal);
        builder.setMaxConnPerRoute(maxConnectionsPerRoute);

        PoolingHttpClientConnectionManager connectionMgr = createConnectionMgr();
        builder.setConnectionManager(connectionMgr);
        return connectionMgr;
    }

    /**
     * Creates custom Http Client connection pool to be used by Http Client
     *
     * @return {@link PoolingHttpClientConnectionManager}
     */
    private PoolingHttpClientConnectionManager createConnectionMgr() {
        PoolingHttpClientConnectionManager connectionMgr;

        // prepare SSLContext
        SSLContext sslContext = buildSslContext();
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        // we allow to disable host name verification against CA certificate,
        // notice: in general this is insecure and should be avoided in production,
        // (this type of configuration is useful for development purposes)
        boolean noHostVerification = false;
        LayeredConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslContext,
                noHostVerification ? NoopHostnameVerifier.INSTANCE : new DefaultHostnameVerifier()
        );
        Registry<ConnectionSocketFactory> r = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", plainsf)
                .register("https", sslsf)
                .build();
        connectionMgr = new PoolingHttpClientConnectionManager(r, null, null,
                null, connectionPoolTimeToLive, TimeUnit.SECONDS);

        connectionMgr.setMaxTotal(maxConnectionsTotal);
        connectionMgr.setDefaultMaxPerRoute(maxConnectionsPerRoute);
        HttpHost localhost = new HttpHost("localhost", 80);
        connectionMgr.setMaxPerRoute(new HttpRoute(localhost), maxConnectionsPerRoute);
        return connectionMgr;
    }

    private SSLContext buildSslContext() {
        SSLContext sslContext = null;
        try {
            SSLContextBuilder sslBuilder = sslContextBuilder;
            if (trustSelfSignCert) {
                if (sslBuilder == null) {
                    sslBuilder = SSLContexts.custom();
                }
                // trust any self signed certificate
                sslBuilder.loadTrustMaterial(TrustSelfSignedMultiChainStrategy.INSTANCE);
            }
            if (sslBuilder != null) {
                sslContext = sslBuilder.build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslContext != null ? sslContext : SSLContexts.createDefault();
    }

    private boolean hasCredentials() {
        return credsProvider.getCredentials(AuthScope.ANY) != null;
    }

    protected enum JFrogAuthScheme {
        BASIC, SPNEGO
    }

    /**
     * Implementation that uses the (optional) default configured {@link HttpHost} if the request doesn't explicitly
     * specify the host as part of the request query.
     */
    public static class DefaultHostRoutePlanner extends DefaultRoutePlanner {

        private final HttpHost defaultHost;

        DefaultHostRoutePlanner(HttpHost defaultHost) {
            super(DefaultSchemePortResolver.INSTANCE);
            this.defaultHost = defaultHost;
        }

        @Override
        public HttpRoute determineRoute(HttpHost host, HttpRequest request, HttpContext context) throws HttpException {
            if (host == null) {
                host = defaultHost;
            }
            return super.determineRoute(host, request, context);
        }
    }

    /**
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * WARNING CONFUSING HTTPCLIENT DOC                                                     *
     * we tested this and the *longer* the timeout is the better it will reuse connections  *
     * and not the opposite as we would expect from reading their convoluted doc            *
     * see RTFACT-13074                                                                     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     **/
    public static final int CONNECTION_POOL_TIME_TO_LIVE = 30;
    private static final int DEFAULT_MAX_CONNECTIONS = 50;

}
