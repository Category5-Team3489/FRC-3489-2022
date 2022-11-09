package frc.robot;

public class PidConstants {
    private double p, i, d;

    public PidConstants(double p, double i, double d) {
        this.p = p;
        this.i = i;
        this.d = d;
    }

    public double getP() {
        return p;
    }

    public double getI() {
        return i;
    }

    public double getD() {
        return d;
    }
}
