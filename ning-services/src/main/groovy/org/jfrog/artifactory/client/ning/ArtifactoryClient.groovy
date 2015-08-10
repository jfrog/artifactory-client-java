/* Copyright 2013 Yahoo! Inc. Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or 
 * agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and 
 * limitations under the License. See accompanying LICENSE file.  */
package org.jfrog.artifactory.client.ning

import java.util.concurrent.ExecutorService
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

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

    public static Artifactory create(final String url, final String username, final char[] password, final NingRequest ningRequest) {
        Realm realm=null;
        if (username!=null && !"".equals(username)){
            realm = new Realm.RealmBuilder()
                    .setPrincipal(username)
                    .setPassword(new String(password))
                    .setUsePreemptiveAuth(true)
                    .setScheme(AuthScheme.BASIC)
                    .build();
        }
        //Use bounded
        ExecutorService executorService =  new ThreadPoolExecutor(20, 100, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),
            new ThreadFactory() {
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r,
                            "AsyncHttpClient-Callback");
                    t.setDaemon(true);
                    return t;
                }
            });
        AsyncHttpClient ningHttpClient = new AsyncHttpClient(new AsyncHttpClientConfig.Builder()
            .setRealm(realm)
            .setExecutorService(executorService)
            .build());
        return create(url, ningHttpClient, ningRequest);
    }
    
    public static Artifactory create(final String url, final AsyncHttpClient ningHttpClient, final NingRequest ningRequest) {
        def matcher = url =~ /(https?:\/\/[^\/]+)\/+([^\/]*).*/
        if (!matcher) {
            matcher = url =~ /(https?:\/\/[^\/]+)\/*()/
            if (!matcher) {
                throw new IllegalArgumentException("Invalid Artifactory URL: ${url}.")
            }
        }
        log.debug("Url: {}, Context: {}, Host: {}", url, matcher[0][2], matcher[0][1]);
        return new ArtifactoryNingClientImpl(ningHttpClient, matcher[0][2], matcher[0][1], ningRequest);
    }
}
