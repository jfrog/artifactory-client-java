package org.jfrog.artifactory.client.httpClient.http;


public class NoProxyHostsEvaluator {

    private final String noProxyHosts;

    public NoProxyHostsEvaluator(String noProxyHosts) {
        this.noProxyHosts = getCleanedNoProxyHostsList(noProxyHosts);
    }

    public String getNoProxyHosts() {
        return noProxyHosts;
    }

    public boolean shouldBypassProxy(String host) {
        String noProxyHostsList = noProxyHosts;
        if (!noProxyHostsList.isEmpty()) {
            // IPV4 and IPV6
            if (host.matches("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}(:[0-9]{1,5})??$")
                    || host.matches("^(?:[a-fA-F0-9]{1,4}:){7}[a-fA-F0-9]{1,4}(:[0-9]{1,5})??$")) {
                return match(noProxyHostsList, host);
            } else {
                return isHostAndPortMatch(noProxyHostsList, host);
            }
        }
        return false;
    }

    private boolean isHostAndPortMatch(String noProxyHostsList, String host) {
        int depth = 0;
        String originalHost = host;
        // Loop while we have a valid domain name: acme.com
        // We add a safeguard to avoid a case where the host would always be valid because the regex would
        // for example fail to remove subdomains.
        // According to Wikipedia (no RFC defines it), 128 is the max number of subdivision for a valid FQDN:
        // https://en.wikipedia.org/wiki/Subdomain#Overview
        while (host.matches("^([a-z0-9]+(-[a-z0-9]+)*\\.)+[a-z]{2,}(:[0-9]{1,5})??$")
                && depth < 128) {
            ++depth;
            if (match(noProxyHostsList, host)) {
                return true;
            } else {
                // Remove first subdomain: sub1.sub2.acme.com -> sub2.acme.com
                host = host.replaceFirst("^[a-z0-9]+(-[a-z0-9]+)*\\.", "");
            }
        }

        String[] noProxyArray = noProxyHostsList.split(",");
        return depth > 0 && suffixMatch(originalHost, noProxyArray);
    }

    private boolean match(String noProxyHostsList, String host) {
        // Check if the no_proxy list contains the host
        if (noProxyHostsList.matches(".*(^|,)\\Q" + host + "\\E($|,).*"))
            return true;

        // Check if host comes with port, but host on no_proxy list without port
        int portSplitIndex = host.indexOf(':');
        return portSplitIndex > -1 && noProxyHostsList.contains(host.substring(0, portSplitIndex))
                && !noProxyHostsList.contains(host.substring(0, portSplitIndex + 1));
    }

    private String getCleanedNoProxyHostsList(String noProxyHostsList) {
        if (noProxyHostsList == null) {
            return "";
        }
        String updatedString = noProxyHostsList.trim()
                // Remove spaces
                .replaceAll("\\s+", "")
                // Remove asterisks followed by dot
                .replaceAll("\\*\\.", "")
                // Convert .foobar.com to foobar.com after first char
                .replaceAll(",\\.", ",");
        if (updatedString.startsWith(".")) {
            // Convert .foobar.com to foobar.com in the first char
            return updatedString.substring(1);
        }
        return updatedString;
    }

    private boolean suffixMatch(String host, String[] noProxyArray) {
        for (String proxy : noProxyArray) {
            if (!proxy.contains(".")) {
                continue;
            }
            if (host.endsWith(proxy)) {
                return true;
            }
        }
        return false;
    }
}
