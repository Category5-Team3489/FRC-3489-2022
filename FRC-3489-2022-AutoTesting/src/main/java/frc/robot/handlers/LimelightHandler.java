package frc.robot.handlers;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.framework.RobotHandler;
import frc.robot.types.LimelightMode;

public class LimelightHandler extends RobotHandler {

    private NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");
    private NetworkTableEntry pipeline = limelight.getEntry("pipeline");
    private NetworkTableEntry targetX = limelight.getEntry("tx");
    private NetworkTableEntry targetY = limelight.getEntry("ty");
    private NetworkTableEntry targetV = limelight.getEntry("tv");

    private Timer targetVisibleTimer = new Timer();

    public double x = 100;
    public double y = 100;

    @Override
    public void robotInit() {
        targetVisibleTimer.reset();
        targetVisibleTimer.start();
    }

    @Override
    public void robotPeriodic() {
        // Update visibility history
        boolean targetVisible = targetV.getDouble(0) == 0 ? false : true; 

        if (targetVisible) {
            targetVisibleTimer.reset();
            double rawX = targetX.getDouble(100);
            double rawY = targetY.getDouble(100);
            if (rawX != 100)
                x = rawX;
            if (rawY != 100)
                y = rawY;
        }
    }
    
    public boolean isTargetVisible() {
        return targetVisibleTimer.get() < 0.5;
    }

    public void setLimelightMode(LimelightMode mode) {
        switch (mode) {
            case Off:
                pipeline.setNumber(1);
                break;
            case AutoAim:
                pipeline.setNumber(0);
                break;
            case Driver:
                pipeline.setNumber(0);
                break;
        }
    }
}
