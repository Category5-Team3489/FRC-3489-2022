package frc.robot.handlers;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
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

    public double x = 0;
    public double y = 0;

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
            if (rawX != Double.MAX_VALUE) x = rawX;
            if (rawY != Double.MAX_VALUE) y = rawY;
        }
    }

    public boolean isTargetVisible() {
        return targetVisibleTimer.get() < 0.5;
    }

    public boolean setState(State state) {
        if (currentState != state) {
            currentState = state;
            switch (state) {
                case Off:
                    pipeline.setNumber(1);
                    break;
                case AutoAim:
                    pipeline.setNumber(0);
                    break;
            }
            return true;
        }
        return false;
    }
}
