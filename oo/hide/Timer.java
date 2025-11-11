package oo.hide;

public class Timer {
    private long startTime;

    public Timer() {
        startTime = System.currentTimeMillis();
    }

    public String getPassedTime() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        return elapsedTime / 1000.00 + "s";
    }
}
