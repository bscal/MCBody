package me.bscal.mcbody.utils;

public class MathUtils {

    private MathUtils() {
    }

    public static double ClampD(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

}
