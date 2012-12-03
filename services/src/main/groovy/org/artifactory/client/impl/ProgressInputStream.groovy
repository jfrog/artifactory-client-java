package org.artifactory.client.impl

import org.artifactory.client.UploadListener

/**
 * @author jbaruch
 * @since 21/11/12
 */
class ProgressInputStream extends FilterInputStream {
    private final UploadListener listener
    private final long totalBytes
    private volatile long totalBytesRead

    public ProgressInputStream(InputStream inputStream, long totalBytes, UploadListener listener) {
        super(inputStream)
        this.totalBytes = totalBytes
        this.listener = listener
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

    private long updateProgress(long bytesRead) {
        if (bytesRead > 0) {
            this.totalBytesRead += bytesRead;
            listener.uploadProgress(totalBytesRead, totalBytes)
        }
        bytesRead;
    }
}