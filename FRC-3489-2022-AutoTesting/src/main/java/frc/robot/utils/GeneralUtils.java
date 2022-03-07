package frc.robot.utils;

public class GeneralUtils {

    public static double lerp(double a, double b, double t)
    {
        return a + t * (b - a);
    }

}
