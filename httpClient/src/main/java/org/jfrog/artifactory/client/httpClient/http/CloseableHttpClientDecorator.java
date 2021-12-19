package org.jfrog.artifactory.client.httpClient.http;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides decoration capabilities for HttpClient,
 * where {@link CloseableObserver} can register to
 * onClose() event
 *
 * @author Michael Pasternak
 */
public class CloseableHttpClientDecorator extends CloseableHttpClient { //implements Configurable {

    private final CloseableHttpClient closeableHttpClient;
    private final List<CloseableObserver> closeableObservers;
    private final PoolingHttpClientConnectionManager clientConnectionManager;

    /**
     * @param closeableHttpClient     {@link CloseableHttpClient}
     * @param clientConnectionManager {@link PoolingHttpClientConnectionManager}
     */
    public CloseableHttpClientDecorator(CloseableHttpClient closeableHttpClient,
                                        PoolingHttpClientConnectionManager clientConnectionManager, boolean useKerberos) {
        assert closeableHttpClient != null : "closeableHttpClient cannot be empty";
        assert clientConnectionManager != null : "clientConnectionManager cannot be empty";
        this.clientConnectionManager = clientConnectionManager;
        this.closeableObservers = new ArrayList<>();
        this.closeableHttpClient = closeableHttpClient;
    }

    /**
     * Release resources and unregister itself from {@link CloseableObserver}
     */
    @Override
    public void close() throws IOException {
        // notify listeners
        onClose();
        // release resources
        closeableHttpClient.close();
    }

    @Override
    protected CloseableHttpResponse doExecute(HttpHost target, HttpRequest request, HttpContext context)
            throws IOException {
        return closeableHttpClient.execute(target, request, context);
    }

    @Deprecated
    @Override
    public HttpParams getParams() {
        return closeableHttpClient.getParams();
    }

    @Deprecated
    @Override
    public ClientConnectionManager getConnectionManager() {
        return closeableHttpClient.getConnectionManager();
    }

    /**
     * Fired on close() event
     */
    public void onClose() {
        for (CloseableObserver closeableObserver : closeableObservers) {
            closeableObserver.onObservedClose(this);
        }
    }
}