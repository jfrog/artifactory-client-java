package org.jfrog.artifactory.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public interface ContentSync {

    boolean isEnabled();

    EnabledHolder getStatistics();

    EnabledHolder getProperties();

    OriginAbsenceDetectionHolder getSource();

    void setEnabled(boolean enabled);

    void setStatistics(EnabledHolder statistics);

    void setProperties(EnabledHolder properties);

    void setSource(OriginAbsenceDetectionHolder source);

    interface EnabledHolder {

        boolean isEnabled();

        void setEnabled(boolean enabled);
    }

    interface OriginAbsenceDetectionHolder {

        boolean isOriginAbsenceDetection();

        void setOriginAbsenceDetection(boolean originAbsenceDetection);
    }
}
