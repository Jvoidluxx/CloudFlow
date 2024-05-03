package cloud.main.utils;

public class TimerSkid
{
    private long prevMS;

    public TimerSkid() {
        this.prevMS = 0L;
    }

    public boolean delay(final float milliSec) {
        return this.getTime() - this.prevMS >= milliSec;
    }

    public void reset() {
        this.prevMS = this.getTime();
    }

    public long getTime() {
        return System.nanoTime() / 1000000L;
    }

    public long getDifference() {
        return this.getTime() - this.prevMS;
    }
}