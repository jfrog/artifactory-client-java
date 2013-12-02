/* Copyright 2013 Yahoo! Inc. Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or 
 * agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and 
 * limitations under the License. See accompanying LICENSE file.  */

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
     * @param boundRequestBuilder
     *            is a builder that has been created already to allow the implmentation class to build custom requests.
     * @return the {@link BoundRequestBuilder} to be added to the Http Client's request.
     */
    public BoundRequestBuilder getBoundRequestBuilder(BoundRequestBuilder boundRequestBuilder);

}
