package frc.robot.utils;

public class GeneralUtils {
    public static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }
}
