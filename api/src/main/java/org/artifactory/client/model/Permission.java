package org.artifactory.client.model;

/**
 * @author jbaruch
 * @since 22/11/12
 */
public enum Permission {
    ADMIN('m'), DELETE('d'), DEPLOY('w'), ANNOTATE('n'), READ('r');

    private Permission(char abbreviation) {
        this.abbreviation = abbreviation;
    }

    private char abbreviation;

    public char getAbbreviation() {
        return abbreviation;
    }

    public static Permission fromAbbreviation(char abbreviation){
        for (Permission permission : values()) {
            if(permission.abbreviation == abbreviation){
                return permission;
            }
        }
        throw new IllegalArgumentException("No Permission for "+abbreviation+" found.");
    }

}
