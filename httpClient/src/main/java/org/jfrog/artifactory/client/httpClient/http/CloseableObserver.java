package org.jfrog.artifactory.client.httpClient.http;

/**
 * Provides onClose() observation capabilities
 *
 * @author Michael Pasternak
 */
public interface CloseableObserver {
    /**
     * Invoked by observed objects on close() event
     *
     * @param object the owner of observed event
     */
    public void onObservedClose(Object object);
}
