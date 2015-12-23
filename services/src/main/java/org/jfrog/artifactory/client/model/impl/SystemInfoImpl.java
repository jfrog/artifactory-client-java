package org.jfrog.artifactory.client.model.impl;

import org.jfrog.artifactory.client.model.SystemInfo;

/**
 * @author Ivan Verhun (ivanver@jfrog.com)
 */
public class SystemInfoImpl implements SystemInfo {
    private long committedVirtualMemorySize;
    private long totalSwapSpaceSize;
    private long freeSwapSpaceSize;
    private long processCpuTime;
    private long totalPhysicalMemorySize;
    private long openFileDescriptorCount;
    private long maxFileDescriptorCount;
    private double processCpuLoad;
    private double systemCpuLoad;
    private long freePhysicalMemorySize;
    private long numberOfCores;
    private long heapMemoryUsage;
    private long noneHeapMemoryUsage;
    private long threadCount;
    private long noneHeapMemoryMax;
    private long heapMemoryMax;

    @Override
    public long getCommittedVirtualMemorySize() {
        return committedVirtualMemorySize;
    }

    public void setCommittedVirtualMemorySize(long committedVirtualMemorySize) {
        this.committedVirtualMemorySize = committedVirtualMemorySize;
    }

    @Override
    public long getTotalSwapSpaceSize() {
        return totalSwapSpaceSize;
    }

    public void setTotalSwapSpaceSize(long totalSwapSpaceSize) {
        this.totalSwapSpaceSize = totalSwapSpaceSize;
    }

    @Override
    public long getFreeSwapSpaceSize() {
        return freeSwapSpaceSize;
    }

    public void setFreeSwapSpaceSize(long freeSwapSpaceSize) {
        this.freeSwapSpaceSize = freeSwapSpaceSize;
    }

    @Override
    public long getProcessCpuTime() {
        return processCpuTime;
    }

    public void setProcessCpuTime(long processCpuTime) {
        this.processCpuTime = processCpuTime;
    }

    @Override
    public long getTotalPhysicalMemorySize() {
        return totalPhysicalMemorySize;
    }

    public void setTotalPhysicalMemorySize(long totalPhysicalMemorySize) {
        this.totalPhysicalMemorySize = totalPhysicalMemorySize;
    }

    @Override
    public long getOpenFileDescriptorCount() {
        return openFileDescriptorCount;
    }

    public void setOpenFileDescriptorCount(long openFileDescriptorCount) {
        this.openFileDescriptorCount = openFileDescriptorCount;
    }

    @Override
    public long getMaxFileDescriptorCount() {
        return maxFileDescriptorCount;
    }

    public void setMaxFileDescriptorCount(long maxFileDescriptorCount) {
        this.maxFileDescriptorCount = maxFileDescriptorCount;
    }

    @Override
    public double getProcessCpuLoad() {
        return processCpuLoad;
    }

    public void setProcessCpuLoad(double processCpuLoad) {
        this.processCpuLoad = processCpuLoad;
    }

    @Override
    public double getSystemCpuLoad() {
        return systemCpuLoad;
    }

    public void setSystemCpuLoad(double systemCpuLoad) {
        this.systemCpuLoad = systemCpuLoad;
    }

    @Override
    public long getFreePhysicalMemorySize() {
        return freePhysicalMemorySize;
    }

    public void setFreePhysicalMemorySize(long freePhysicalMemorySize) {
        this.freePhysicalMemorySize = freePhysicalMemorySize;
    }

    @Override
    public long getNumberOfCores() {
        return numberOfCores;
    }

    public void setNumberOfCores(long numberOfCores) {
        this.numberOfCores = numberOfCores;
    }

    @Override
    public long getHeapMemoryUsage() {
        return heapMemoryUsage;
    }

    public void setHeapMemoryUsage(long heapMemoryUsage) {
        this.heapMemoryUsage = heapMemoryUsage;
    }

    @Override
    public long getNoneHeapMemoryUsage() {
        return noneHeapMemoryUsage;
    }

    public void setNoneHeapMemoryUsage(long noneHeapMemoryUsage) {
        this.noneHeapMemoryUsage = noneHeapMemoryUsage;
    }

    @Override
    public long getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(long threadCount) {
        this.threadCount = threadCount;
    }

    @Override
    public long getNoneHeapMemoryMax() {
        return noneHeapMemoryMax;
    }

    public void setNoneHeapMemoryMax(long noneHeapMemoryMax) {
        this.noneHeapMemoryMax = noneHeapMemoryMax;
    }

    @Override
    public long getHeapMemoryMax() {
        return heapMemoryMax;
    }

    public void setHeapMemoryMax(long heapMemoryMax) {
        this.heapMemoryMax = heapMemoryMax;
    }
}
