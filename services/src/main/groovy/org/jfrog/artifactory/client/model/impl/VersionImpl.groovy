package org.jfrog.artifactory.client.model.impl

import groovy.transform.Canonical
import org.apache.commons.lang.StringUtils
import org.jfrog.artifactory.client.model.Version
/**
 * @author jryan
 * @revised_by yahavi
 */
@Canonical
public class VersionImpl implements Version {

    String version
    String revision
    List<String> addons
    String license

    VersionImpl() {
        addons = []
    }

    public VersionImpl(String version, String revision, String license, List<String> addons) {
        this.version = version
        this.revision = revision
        this.license = license
        this.addons = addons
    }

    public boolean isAtLeast(String atLeast) {
        if (StringUtils.isBlank(atLeast)) {
            return true
        }

        String[] versionTokens = StringUtils.split(version, ".")
        String[] otherVersionTokens = StringUtils.split(atLeast, ".")
        for (int tokenIndex = 0; tokenIndex < otherVersionTokens.length; tokenIndex++) {
            String atLeastToken = otherVersionTokens[tokenIndex].trim()
            String versionToken = versionTokens.length < (tokenIndex + 1) ? "0" : versionTokens[tokenIndex].trim()
            int comparison = compareTokens(versionToken, atLeastToken)
            if (comparison != 0) {
                return comparison > 0
            }
        }
        true
    }

    /**
     * @return less than 0 if toCheck is less than atLeast, 0 if they are equal or greater than 0 if toCheck is greater
     *         than atLeast
     */
    private static int compareTokens(String toCheckToken, String atLeastToken) {
        int toCheckFirstNumerals = Integer.parseInt(getTokenFirstNumerals(toCheckToken))
        int atLeastFirstNumerals = Integer.parseInt(getTokenFirstNumerals(atLeastToken))

        int compareNumerals = toCheckFirstNumerals <=> atLeastFirstNumerals
        compareNumerals != 0 ? compareNumerals : toCheckToken <=> atLeastToken
    }

    private static String getTokenFirstNumerals(String token) {
        StringBuilder numerals = new StringBuilder()
        for (char c : token.toCharArray()) {
            if (!Character.isDigit(c)) {
                break
            }
            numerals.append(c)
        }
        numerals.size() > 0 ? numerals.toString() : "0"
    }
}
