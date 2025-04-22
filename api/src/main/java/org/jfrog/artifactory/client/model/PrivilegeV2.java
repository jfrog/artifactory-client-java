package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PrivilegeV2 {
    READ("read"),WRITE("write"),ANNOTATE("annotate"),DELETE("delete"),MANAGE("manage"),MANAGED_XRAY_META("managedXrayMeta"),DISTRIBUTE("distribute");

    @JsonValue
    private String privilege;

    PrivilegeV2(String privilege) {
        this.privilege = privilege;
    }

    public String getPrivilege() {
        return privilege;
    }

    public static PrivilegeV2 fromPrivilege(String privilege){
        for (PrivilegeV2 privilegeV2 : values()) {
            if (privilegeV2.privilege.equals(privilege)) {
                return privilegeV2;
            }
        }
        throw new IllegalArgumentException("No Privilege for "+privilege+" found.");
    }
}
