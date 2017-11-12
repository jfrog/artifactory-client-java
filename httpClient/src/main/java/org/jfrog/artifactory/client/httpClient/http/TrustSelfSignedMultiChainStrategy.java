package org.jfrog.artifactory.client.httpClient.http;

import org.apache.http.conn.ssl.TrustStrategy;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * A trust strategy that accepts self-signed certificates with multiple chains as trusted.
 * Verification of all other certificates is done by the trust manager configured in the SSL context.
 */
public class TrustSelfSignedMultiChainStrategy implements TrustStrategy {

    public static final TrustSelfSignedMultiChainStrategy INSTANCE = new TrustSelfSignedMultiChainStrategy();

    public boolean isTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
        return chain.length >= 1;
    }
}