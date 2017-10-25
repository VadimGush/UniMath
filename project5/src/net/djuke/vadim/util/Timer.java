package net.djuke.vadim.util;

public class Timer {

    private long start;
    private long time;

    public void start() {
        start = System.currentTimeMillis();
    }

    public void end() {
        time = System.currentTimeMillis() - start;
    }

    public long getTime() {
        return time;
    }
}
