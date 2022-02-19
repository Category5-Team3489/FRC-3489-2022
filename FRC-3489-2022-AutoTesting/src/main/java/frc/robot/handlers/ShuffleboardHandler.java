package frc.robot.handlers;

import edu.wpi.first.cscore.VideoSource;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.framework.RobotHandler;
import frc.robot.utils.ShuffleboardUtils;

//https://docs.wpilib.org/en/stable/docs/software/dashboards/shuffleboard/getting-started/index.html

public class ShuffleboardHandler extends RobotHandler {

    private SendableChooser<Integer> autoChooser = new SendableChooser<Integer>();

    public void setBoolean(boolean isMainTab, String name, boolean value) {
        ShuffleboardUtils.setBoolean(getTab(isMainTab), name, value);
    }
    public void setDouble(boolean isMainTab, String name, double value) {
        ShuffleboardUtils.setDouble(getTab(isMainTab), name, value);
    }
    public void setString(boolean isMainTab, String name, String value) {
        ShuffleboardUtils.setString(getTab(isMainTab), name, value);
    }

    public ShuffleboardTab getTab(boolean isMainTab) {
        return isMainTab ? ShuffleboardUtils.mainTab : ShuffleboardUtils.autoTab;
    }

    public void createCameraWidget(VideoSource source) {
        ShuffleboardUtils.mainTab
            .add(source)
            .withWidget(BuiltInWidgets.kCameraStream)
            .withSize(4, 4)
            .withPosition(2, 0);
    }

    public void createAutoChooserWidget() {
        SendableRegistry.setName(autoChooser, "Auto Chooser");
        autoChooser.setDefaultOption("0: None", 0);
        autoChooser.addOption("1: Drive Human", 1);
        autoChooser.addOption("2: Shoot Intake Shoot", 2);
        autoChooser.addOption("3: Intake Shoot", 3);
        autoChooser.addOption("4: Shoot Drive", 4);
        autoChooser.addOption("5: Shoot Intake Drive", 5);
        autoChooser.addOption("6: Shoot Intake Human", 6);
        autoChooser.addOption("7: Shoot Intake", 7);
        autoChooser.addOption("32: Test", 32);
        ShuffleboardUtils.autoTab
            .add(autoChooser)
            .withSize(3, 3);
    }

    public int getSelectedAuto() {
        return autoChooser.getSelected();
    }
    
}
