package org.jfrog.artifactory.client.model.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.jfrog.artifactory.client.model.ContentSync;

public class ContentSyncImpl implements ContentSync {

    private boolean enabled;

    @JsonDeserialize(as = EnabledHolderImpl.class)
    private EnabledHolder statistics;

    @JsonDeserialize(as = EnabledHolderImpl.class)
    private EnabledHolder properties;

    @JsonDeserialize(as = OriginAbsenceDetectionHolderImpl.class)
    private OriginAbsenceDetectionHolder source;

    public ContentSyncImpl() {
        statistics = new EnabledHolderImpl();
        properties = new EnabledHolderImpl();
        source = new OriginAbsenceDetectionHolderImpl();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public EnabledHolder getStatistics() {
        return statistics;
    }

    @Override
    public void setStatistics(EnabledHolder statistics) {
        this.statistics = statistics;
    }

    @Override
    public EnabledHolder getProperties() {
        return properties;
    }

    @Override
    public void setProperties(EnabledHolder properties) {
        this.properties = properties;
    }

    @Override
    public OriginAbsenceDetectionHolder getSource() {
        return source;
    }

    @Override
    public void setSource(OriginAbsenceDetectionHolder source) {
        this.source = source;
    }

    public class EnabledHolderImpl implements EnabledHolder {

        private boolean enabled;

        @Override
        public boolean isEnabled() {
            return enabled;
        }

        @Override
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    public class OriginAbsenceDetectionHolderImpl implements OriginAbsenceDetectionHolder {

        private boolean originAbsenceDetection;

        @Override
        public boolean isOriginAbsenceDetection() {
            return originAbsenceDetection;
        }

        @Override
        public void setOriginAbsenceDetection(boolean originAbsenceDetection) {
            this.originAbsenceDetection = originAbsenceDetection;
        }
    }
}
