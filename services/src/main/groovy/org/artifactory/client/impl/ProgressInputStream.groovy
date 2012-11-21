package org.artifactory.client.impl

import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
/**
 * @author jbaruch
 * @since 21/11/12
 */
class ProgressInputStream extends FilterInputStream {
    private final PropertyChangeSupport propertyChangeSupport
    private final long maxNumBytes
    private volatile long totalNumBytesRead

    public ProgressInputStream(InputStream inputStream, long maxNumBytes, PropertyChangeListener listener) {
        super(inputStream)
        this.propertyChangeSupport = new PropertyChangeSupport(this)
        this.maxNumBytes = maxNumBytes
        this.propertyChangeSupport.addPropertyChangeListener(listener)
    }

    public long getTotalNumBytesRead() {
        return totalNumBytesRead;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }

    @Override
    public int read() throws IOException {
        int b = super.read()
        updateProgress(1)
        b
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return (int) updateProgress(super.read(b, off, len));
    }

    @Override
    public long skip(long n) throws IOException {
        return updateProgress(super.skip(n));
    }

    private long updateProgress(long numBytesRead) {
        if (numBytesRead > 0) {
            long oldTotalNumBytesRead = this.totalNumBytesRead;
            this.totalNumBytesRead += numBytesRead;
            propertyChangeSupport.firePropertyChange("totalNumBytesRead", oldTotalNumBytesRead, this.totalNumBytesRead);
        }
        numBytesRead;
    }
}