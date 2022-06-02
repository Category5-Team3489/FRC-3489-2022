package frc.robot.handlers;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.framework.RobotHandler;

public class LimelightHandler extends RobotHandler {
    public enum State {
        Off,
        AutoAim
    }
    
    private NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");
    private NetworkTableEntry pipeline = limelight.getEntry("pipeline");
    private NetworkTableEntry targetX = limelight.getEntry("tx");
    private NetworkTableEntry targetY = limelight.getEntry("ty");
    private NetworkTableEntry targetV = limelight.getEntry("tv");

    private Timer targetVisibleTimer = new Timer();

    private State currentState = State.Off;

    private double x = Double.MAX_VALUE;
    private double y = Double.MAX_VALUE;

    @Override
    public void robotInit() {
        targetVisibleTimer.reset();
        targetVisibleTimer.start();

        setState(currentState);
    }

    @Override
    public void robotPeriodic() {
        boolean targetVisible = targetV.getDouble(0) == 0 ? false : true; 

        if (targetVisible) {
            targetVisibleTimer.reset();
            double rawX = targetX.getDouble(Double.MAX_VALUE);
            double rawY = targetY.getDouble(Double.MAX_VALUE);
            if (rawX != Double.MAX_VALUE) {
                x = rawX;
            }
            if (rawY != Double.MAX_VALUE) {
                y = rawY;
            }
        }
    }

    public boolean isTargetVisible() {
        return targetVisibleTimer.get() < Constants.Limelight.TargetVisibleTimeout;
    }

    public boolean isXValid() {
        if (!isTargetVisible()) {
            x = Double.MAX_VALUE;
            return false;
        }
        return x != Double.MAX_VALUE;
    }
    public boolean isYValid() {
        if (!isTargetVisible()) {
            y = Double.MAX_VALUE;
            return false;
        }
        return y != Double.MAX_VALUE;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    public double getDistanceEstimate() {
        if (!isYValid()) {
            return Double.MAX_VALUE;
        }
        double targetYOffset = getY();
        double distance = Constants.Limelight.RobotTargetYDiff / Math.tan(Math.toRadians(Constants.Limelight.AngleOfElevation + targetYOffset));
        return distance;
    }

    public boolean setState(State state) {
        if (currentState != state) {
            currentState = state;
            switch (state) {
                case Off:
                    pipeline.setNumber(Constants.Limelight.PipelineOff);
                    break;
                case AutoAim:
                    pipeline.setNumber(Constants.Limelight.PipelineAutoAim);
                    break;
            }
            return true;
        }
        return false;
    }
}
