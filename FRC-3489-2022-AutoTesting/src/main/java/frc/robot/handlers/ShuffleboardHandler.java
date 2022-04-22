package frc.robot.handlers;

import java.util.Map;

import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.cscore.VideoSource;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
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
        ShuffleboardUtils.setBoolean(getTab(isMainTab), name, value, (data) -> initializeWidget(data));
    }
    public void setDouble(boolean isMainTab, String name, double value) {
        ShuffleboardUtils.setDouble(getTab(isMainTab), name, value, (data) -> initializeWidget(data));
    }
    public void setString(boolean isMainTab, String name, String value) {
        ShuffleboardUtils.setString(getTab(isMainTab), name, value, (data) -> initializeWidget(data));
    }
    public void setNumber(boolean isMainTab, String name, Number value) {
        ShuffleboardUtils.setNumber(getTab(isMainTab), name, value, (data) -> initializeWidget(data));
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
        /*
        ShuffleboardUtils.mainTab
            .add(source)
            .withWidget(BuiltInWidgets.kCameraStream)
            .withSize(4, 4)
            .withPosition(0, 0);
        */
    }

    public void createLimelightCameraWidget(HttpCamera source) {
        ShuffleboardUtils.mainTab
            .add(source)
            .withWidget(BuiltInWidgets.kCameraStream)
            .withSize(5, 4)
            .withPosition(0, 0);
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
        autoChooser.addOption("42: ???", 42);
        autoChooser.addOption("42 extra", 44);
        //autoChooser.addOption("42 extra: Red", 44);
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
    
    private void initializeWidget(Map.Entry<String, SimpleWidget> data) {
        String name = data.getKey();
        SimpleWidget widget = data.getValue();
        switch (name) {
            case "Auto Aiming":
                widget.withPosition(8, 1)
                .withSize(2, 1);
                break;
            case "Intake State":
                widget.withPosition(8, 2);
                break;
            case "Shooter State":
                widget.withPosition(9, 0);
                break;
            case "Distance Estimate":
                widget.withPosition(7, 1);
                break;
            case "Drive State":
                widget.withPosition(8, 1);
                break;
            case "Intake Running":
                widget.withPosition(5, 0)
                .withSize(2, 2);
                break;
            case "Selected Auto":
                widget.withPosition(7, 2);
                break;
            case "Climber Step":
                widget.withPosition(7, 0)
                .withSize(2, 1);
                break;
            case "Cargo Count":
                widget.withPosition(9, 2);
                break;
        }
    }
}
