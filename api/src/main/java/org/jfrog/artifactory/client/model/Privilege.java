package org.jfrog.artifactory.client.model;

/**
 * @author jbaruch
 * @since 22/11/12
 */
public enum Privilege {
    ADMIN('m'), DELETE('d'), DEPLOY('w'), ANNOTATE('n'), READ('r');

    private Privilege(char abbreviation) {
        this.abbreviation = abbreviation;
    }

    private char abbreviation;

    public char getAbbreviation() {
        return abbreviation;
    }

    public static Privilege fromAbbreviation(char abbreviation){
        for (Privilege privilege : values()) {
            if(privilege.abbreviation == abbreviation){
                return privilege;
            }
        }
        throw new IllegalArgumentException("No Privilege for "+abbreviation+" found.");
    }

}
