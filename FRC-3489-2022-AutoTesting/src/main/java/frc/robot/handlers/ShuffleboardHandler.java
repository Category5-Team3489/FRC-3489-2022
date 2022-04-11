package frc.robot.handlers;

import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.cscore.VideoSource;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.Constants;
import frc.robot.framework.RobotHandler;
import frc.robot.interfaces.IShuffleboardState;
import frc.robot.utils.ShuffleboardUtils;

//https://docs.wpilib.org/en/stable/docs/software/dashboards/shuffleboard/getting-started/index.html

public class ShuffleboardHandler extends RobotHandler {

    private SendableChooser<Integer> autoChooser = new SendableChooser<Integer>();

    private SendableChooser<Integer> shooterSpinupChooser = new SendableChooser<Integer>();

    private long loop = 0;

    private boolean shouldShowUpdate() {
        return loop % Constants.ShuffleboardShowUpdatePeriod == 0;
    }

    @Override
    public void robotInit() {
        robotManager.forEachHandler((handler) -> {
            if (handler instanceof IShuffleboardState)
                ((IShuffleboardState)handler).setShuffleboardState();
        });
        createShooterSpinupChooserWidget();
    }

    @Override
    public void robotPeriodic() {
        loop++;
    }

    public void setBoolean(boolean isMainTab, String name, boolean value) {
        ShuffleboardUtils.setBoolean(getTab(isMainTab), name, value);
    }
    public void setDouble(boolean isMainTab, String name, double value) {
        ShuffleboardUtils.setDouble(getTab(isMainTab), name, value);
    }
    public void setString(boolean isMainTab, String name, String value) {
        ShuffleboardUtils.setString(getTab(isMainTab), name, value);
    }
    public void setNumber(boolean isMainTab, String name, Number value) {
        ShuffleboardUtils.setNumber(getTab(isMainTab), name, value);
    }

    public void showBoolean(boolean isMainTab, String name, Boolean value) {
        if (shouldShowUpdate())
            setBoolean(isMainTab, name, value);
    }
    public void showDouble(boolean isMainTab, String name, Double value) {
        if (shouldShowUpdate())
            setDouble(isMainTab, name, value);
    }
    public void showString(boolean isMainTab, String name, String value) {
        if (shouldShowUpdate())
            setString(isMainTab, name, value);
    }
    public void showNumber(boolean isMainTab, String name, Number value) {
        if (shouldShowUpdate())
            setNumber(isMainTab, name, value);
    }

    public ShuffleboardTab getTab(boolean isMainTab) {
        return isMainTab ? ShuffleboardUtils.mainTab : ShuffleboardUtils.autoTab;
    }

    public void createCameraWidget(VideoSource source) {
        ShuffleboardUtils.mainTab
            .add(source)
            .withWidget(BuiltInWidgets.kCameraStream)
            .withSize(4, 4)
            .withPosition(0, 0);
    }

    public void createLimelightCameraWidget(HttpCamera source) {
        ShuffleboardUtils.mainTab
            .add(source)
            .withWidget(BuiltInWidgets.kCameraStream)
            .withSize(2, 2);
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
        autoChooser.addOption("16: Auto Simple", 16);
        autoChooser.addOption("17: Auto Simple Pickup", 17);
        autoChooser.addOption("18: Auto Auto Aim???", 18);
        autoChooser.addOption("32: Test", 32);
        ShuffleboardUtils.autoTab
            .add(autoChooser)
            .withSize(3, 3);
    }

    public void createShooterSpinupChooserWidget() {
        SendableRegistry.setName(shooterSpinupChooser, "Shooter Spin Up");
        shooterSpinupChooser.setDefaultOption("0: Yes", 0);
        shooterSpinupChooser.addOption("1: No", 1);
        ShuffleboardUtils.autoTab
            .add(shooterSpinupChooser)
            .withSize(2, 2);
    }

    public int getSelectedAuto() {
        return autoChooser.getSelected();
    }

    public boolean isShooterSpinupEnabled() {
        return shooterSpinupChooser.getSelected() == 0;
    }
    
}
