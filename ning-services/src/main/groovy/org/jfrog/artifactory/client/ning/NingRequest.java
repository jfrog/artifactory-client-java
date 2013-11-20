/* Copyright (c) 2013, Yahoo! Inc.  All rights reserved. */

package org.jfrog.artifactory.client.ning;

import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;

/**
 * Implementation class can implement this interface to add custom request properties to Http Client's request.
 * 
 * @author charlesk
 * 
 */
public interface NingRequest {

    /**
     * 
     * @param BoundRequestBuilder
     *            is a builder that has been created already to allow the implmentation class to build custom requests.
     * @return the {@link BoundRequestBuilder} to be added to the Http Client's request.
     */
    public BoundRequestBuilder getBoundRequestBuilder(BoundRequestBuilder boundRequestBuilder);

}
