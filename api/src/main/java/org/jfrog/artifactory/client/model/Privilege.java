package org.jfrog.artifactory.client.model;

/**
 * @author jbaruch
 * @since 22/11/12
 */
public enum Privilege {
    ADMIN("m"), DELETE("d"), DEPLOY("w"), ANNOTATE("n"), READ("r");

    Privilege(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    private String abbreviation;

    public String getAbbreviation() {
        return abbreviation;
    }

    public static Privilege fromAbbreviation(String abbreviation){
        for (Privilege privilege : values()) {
            if (privilege.abbreviation.equals(abbreviation)) {
                return privilege;
            }
        }
        throw new IllegalArgumentException("No Privilege for "+abbreviation+" found.");
    }
}
