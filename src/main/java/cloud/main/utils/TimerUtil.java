package cloud.main.utils;

public class TimerUtil {
    private static long lastTime = System.currentTimeMillis();

    private static void reset() {
        lastTime = System.currentTimeMillis();
    }
    public static boolean HTE(long time, boolean reset) {
        if (System.currentTimeMillis() - lastTime >= time) {
            if (reset)
                reset();
            return true;
        }
        return false;
    }
}
