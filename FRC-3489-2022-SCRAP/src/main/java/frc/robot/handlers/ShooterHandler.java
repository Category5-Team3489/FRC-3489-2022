package frc.robot.handlers;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.Constants;
import frc.robot.framework.RobotHandler;
import frc.robot.framework.utils.GeneralUtils;

public class ShooterHandler extends RobotHandler {
    public enum State {
        Disabled,
        Percent,
        PID
    }

    public static class DistanceSetting {
        public final double distance;
        public final Setting setting;

        private DistanceSetting(double distance, Setting setting) {
            this.distance = distance;
            this.setting = setting;
        }

        public static DistanceSetting S(double distance, double bottom, double top) {
            return new DistanceSetting(distance, Setting.PID(bottom, top));
        }
    }

    public static class Setting {
        public final boolean isPercent;
        public final double bottom;
        public final double top;

        private Setting(boolean isPercent, double bottom, double top) {
            this.isPercent = isPercent;
            this.bottom = bottom;
            this.top = top;
        }

        public static Setting Percent(double bottom, double top) {
            return new Setting(true, bottom, top);
        }

        public static Setting PID(double bottom, double top) {
            return new Setting(false, bottom, top);
        }

        public static Setting lerp(Setting a, Setting b, double t) {
            return new Setting(a.isPercent || b.isPercent, GeneralUtils.lerp(a.bottom, b.bottom, t), GeneralUtils.lerp(a.top, b.top, t));
        }
    }

    // State
    private State currentState = State.Disabled;
    private double percentSpeedBottom = 0;
    private double percentSpeedTop = 0;

    @Override
    public void robotInit() {

    }

    public void setShooter(Setting setting) {
        if (setting.isPercent) {
            if (shouldUpdate(State.Percent) || shouldUpdatePercent(setting.bottom, setting.top)) {
                components.bottomShooterMotor.set(ControlMode.PercentOutput, setting.bottom);
                components.topShooterMotor.set(ControlMode.PercentOutput, setting.top);
            }
        }
        else {
            if (shouldUpdate(State.PID)) {
                components.bottomShooterMotor.set(ControlMode.Velocity, setting.bottom);
                components.topShooterMotor.set(ControlMode.Velocity, setting.top);
            }
        }
    }

    public void setLowHub() {
        setShooter(Constants.Shooter.LowHubSetting);
    }
    public void setHighHub() {
        setShooter(Constants.Shooter.HighHubSetting);
    }
    public void setEjectCargo() {
        setShooter(Constants.Shooter.EjectCargoSetting);
    }
    public void setStopped() {
        setShooter(Setting.Percent(0, 0));
    }

    private boolean shouldUpdate(State state) {
        if (currentState != state) {
            currentState = state;
            return true;
        }
        return false;
    }
    private boolean shouldUpdatePercent(double bottom, double top) {
        if (percentSpeedBottom != bottom || percentSpeedTop != top) {
            percentSpeedBottom = bottom;
            percentSpeedTop = top;
            return true;
        }
        return false;
    }

    private Setting getSettingAtDistance(double distance) {
        DistanceSetting previous = getPreviousDistanceSetting(distance);
        DistanceSetting next = getNextDistanceSetting(distance);
        double distanceLinearStep = GeneralUtils.inverseLerp(previous.distance, next.distance, distance);
        return Setting.lerp(previous.setting, next.setting, distanceLinearStep);
    }

    private DistanceSetting getPreviousDistanceSetting(double distance) {
        for (int i = Constants.Shooter.DistanceSettingTable.length - 1; i >= 0; i--) {
            DistanceSetting distanceSetting = Constants.Shooter.DistanceSettingTable[i];
            // skip distance settings greater than or equal to the estimated distance
            if (distanceSetting.distance >= distance) {
                continue;
            }
            return distanceSetting;
        }
        // when there is no previous distance, return the first
        return Constants.Shooter.DistanceSettingTable[0];
    }

    private DistanceSetting getNextDistanceSetting(double distance) {
        for (int i = 0; i < Constants.Shooter.DistanceSettingTable.length; i++) {
            DistanceSetting distanceSetting = Constants.Shooter.DistanceSettingTable[i];
            // skip distance settings less than the estimated distance
            if (distanceSetting.distance < distance) {
                continue;
            }
            return distanceSetting;
        }
        // when there is no next distance, return the last
        return Constants.Shooter.DistanceSettingTable[Constants.Shooter.DistanceSettingTable.length - 1];
    }
}
