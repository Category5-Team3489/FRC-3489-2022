package frc.robot.handlers;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.framework.RobotHandler;
import frc.robot.types.LimelightMode;

public class LimelightHandler extends RobotHandler {

    private NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");
    private NetworkTableEntry pipeline = limelight.getEntry("pipeline");
    private NetworkTableEntry targetX = limelight.getEntry("tx");
    private NetworkTableEntry targetY = limelight.getEntry("ty");
    private NetworkTableEntry targetV = limelight.getEntry("tv");

    private ArrayList<Boolean> targetVHistory = new ArrayList<Boolean>();

    public double x = 0;
    public double y = 0;

    @Override
    public void robotPeriodic() {
        // Update visibility history
        boolean targetVisible = targetV.getDouble(0) == 0 ? false : true; 
        targetVHistory.add(0, targetVisible);
        if (targetVHistory.size() > 25) {
            targetVHistory.remove(targetVHistory.size() - 1);
        }

        if (targetVisible) {
            double rawX = targetX.getDouble(100);
            double rawY = targetY.getDouble(100);
            if (rawX != 100)
                x = rawX;
            if (rawY != 100)
                y = rawY;
        }
    }
    
    public boolean isTargetVisible() {
        for (Boolean targetHistoryEntry : targetVHistory) {
            if (targetHistoryEntry)
                return true;
        }
        return false;
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
                pipeline.setNumber(2);
                break;
        }
    }
}
