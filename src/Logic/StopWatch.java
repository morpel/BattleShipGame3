package Logic;

import java.util.concurrent.TimeUnit;

public class StopWatch {

    private long startTime;
    private long timeElapsed;

    public StopWatch() {
        startTime = 0;
        timeElapsed = 0;
    }

    public void initWatch() {
        this.startTime = System.currentTimeMillis();
    }

    public long getElapsedTimeInMS() {
        return System.currentTimeMillis() - this.startTime;
    }

    public String getTimeElapsedAsString(final long msPassed) {
        final long hr = TimeUnit.MILLISECONDS.toHours(msPassed);
        final long min = TimeUnit.MILLISECONDS.toMinutes(msPassed - TimeUnit.HOURS.toMillis(hr));
        final long sec = TimeUnit.MILLISECONDS.toSeconds(msPassed - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min));
        final long ms = TimeUnit.MILLISECONDS.toMillis(msPassed - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min) - TimeUnit.SECONDS.toMillis(sec));
        return String.format("%02d:%02d:%02d.%03d", hr, min, sec, ms);

    }


}

