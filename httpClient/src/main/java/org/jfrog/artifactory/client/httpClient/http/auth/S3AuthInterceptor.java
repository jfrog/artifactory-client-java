package org.jfrog.artifactory.client.httpClient.http.auth;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.protocol.HttpContext;

public class S3AuthInterceptor implements HttpRequestInterceptor {
	  @Override
	    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
	      HttpClientContext clientContext = HttpClientContext.adapt(context);
		  if(clientContext.getRedirectLocations() != null) {
			  if (clientContext.getTargetHost().getHostName().endsWith(".amazonaws.com") && request.getHeaders("Authorization") != null) {
				 request.removeHeaders("Authorization"); 
			  }
	        }
	  }
}
