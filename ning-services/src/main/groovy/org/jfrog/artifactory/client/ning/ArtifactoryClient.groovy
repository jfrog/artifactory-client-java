/* Copyright (c) 2013, Yahoo! Inc.  All rights reserved. */
package org.jfrog.artifactory.client.ning

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory

import org.jfrog.artifactory.client.Artifactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.ning.http.client.AsyncHttpClient
import com.ning.http.client.AsyncHttpClientConfig
import com.ning.http.client.Realm
import com.ning.http.client.Realm.AuthScheme


/**
 * Entry point to the Artifactory client.
 * By default, {@link AsyncHttpClient} is used for the Rest Client.
 *
 * @author charlesk
 *
 */
public class ArtifactoryClient {

    private static final Logger log = LoggerFactory.getLogger(ArtifactoryClient.class);
    private static AsyncHttpClient ningHttpClient;
    private static final NingRequest ningRequest;

    public static Artifactory create(final String url, final String username, final char[] password, final NingRequest ningRequest) {
        def matcher = url =~ /(https?:\/\/[^\/]+)\/+([^\/]*).*/
        if (!matcher) {
            matcher = url =~ /(https?:\/\/[^\/]+)\/*()/
            if (!matcher) {
                throw new IllegalArgumentException("Invalid Artifactory URL: ${url}.")
            }
        }
        Realm realm=null;
        if (username!=null && !"".equals(username)){
            realm = new Realm.RealmBuilder()
                    .setPrincipal(username)
                    .setPassword(new String(password))
                    .setUsePreemptiveAuth(true)
                    .setScheme(AuthScheme.BASIC)
                    .build();
        }
        ExecutorService executorService =  Executors
                        .newCachedThreadPool(new ThreadFactory() {
                            public Thread newThread(Runnable r) {
                                Thread t = new Thread(r,
                                        "AsyncHttpClient-Callback");
                                t.setDaemon(true);
                                return t;
                            }
                        });
        ningHttpClient = new AsyncHttpClient(new AsyncHttpClientConfig.Builder()
            .setRealm(realm)
            .setExecutorService(executorService)
            .build());
        log.debug("Url: {}, Context: {}, Host: {}", url, matcher[0][2], matcher[0][1]);
        return new ArtifactoryNingClientImpl(ningHttpClient, matcher[0][2], matcher[0][1], ningRequest);
    }
}
