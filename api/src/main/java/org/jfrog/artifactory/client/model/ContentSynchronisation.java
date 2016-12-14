package org.jfrog.artifactory.client.model;

public interface ContentSynchronisation {

    boolean getEnabled();

    EnabledHolder getStatistics();

    EnabledHolder getProperties();

    OriginAbsenceDetectionHolder getSource();

    void setEnabled(boolean enabled);

    void setStatistics(EnabledHolder statistics);

    void setProperties(EnabledHolder properties);

    void setSource(OriginAbsenceDetectionHolder source);

    interface EnabledHolder {

        boolean getEnabled();

        void setEnabled(boolean enabled);
    }

    interface OriginAbsenceDetectionHolder {

        boolean getOriginAbsenceDetection();

        void setOriginAbsenceDetection(boolean originAbsenceDetection);
    }
}
