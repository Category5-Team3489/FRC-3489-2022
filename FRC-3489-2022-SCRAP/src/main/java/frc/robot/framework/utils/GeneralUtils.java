package frc.robot.framework.utils;

public final class GeneralUtils {
    public static double lerp(double a, double b, double t) {
        return a + t * (b - a);
    }
    public static double inverseLerp(double a, double b, double t) {
        return (b - t) / (b - a);
    }
}
