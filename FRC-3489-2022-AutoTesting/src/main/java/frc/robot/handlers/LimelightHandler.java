package frc.robot.handlers;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.framework.RobotHandler;

public class LimelightHandler extends RobotHandler {

    public NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");
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

        // 
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
}
