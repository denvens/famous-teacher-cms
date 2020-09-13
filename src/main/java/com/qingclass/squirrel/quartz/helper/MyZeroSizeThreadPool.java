package com.qingclass.squirrel.quartz.helper;

import org.quartz.simpl.ZeroSizeThreadPool;

public class MyZeroSizeThreadPool extends ZeroSizeThreadPool {

    private int threadCount;

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }
}
