package frc.robot.handlers;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.Constants;
import frc.robot.framework.RobotHandler;
import frc.robot.framework.utils.GeneralUtils;

public class ShooterHandler extends RobotHandler {
    public enum State {
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

        public boolean sameMode(Setting o) {
            return isPercent == o.isPercent;
        }
        public boolean sameBottom(Setting o) {
            return bottom == o.bottom;
        }
        public boolean sameTop(Setting o) {
            return top == o.top;
        }
    }

    // State
    private Setting currentSetting = new Setting(true, 0, 0);

    @Override
    public void robotInit() {
        setStopped();
    }

    public boolean canShoot() {
        if (currentSetting.isPercent) {
            return currentSetting.bottom != 0 && currentSetting.top != 0;
        }
        else {
            double bottomAbsVelocity = Math.abs(components.bottomShooterMotor.getSelectedSensorVelocity());
            double topAbsVelocity = Math.abs(components.topShooterMotor.getSelectedSensorVelocity());

            boolean fastEnoughBottom = bottomAbsVelocity >= (1 - Constants.Shooter.ReadyToShootThreshold) * currentSetting.bottom;
            boolean fastEnoughTop = topAbsVelocity >= (1 - Constants.Shooter.ReadyToShootThreshold) * currentSetting.top;
            boolean slowEnoughBottom = bottomAbsVelocity <= (1 + Constants.Shooter.ReadyToShootThreshold) * currentSetting.bottom;
            boolean slowEnoughTop = topAbsVelocity <= (1 + Constants.Shooter.ReadyToShootThreshold) * currentSetting.top;
            return fastEnoughBottom && fastEnoughTop && slowEnoughBottom && slowEnoughTop;
        }
    }

    public void set(Setting setting) {
        if (currentSetting.sameMode(setting)) {
            if (!currentSetting.sameBottom(setting)) {
                setBottom(setting);
            }
            if (!currentSetting.sameTop(setting)) {
                setTop(setting);
            }
        }
        else {
            setBottom(setting);
            setTop(setting);
        }

        currentSetting = setting;
    }

    private void setBottom(Setting setting) {
        if (setting.bottom == 0) {
            components.bottomShooterMotor.stopMotor();
        }
        else if (setting.isPercent) {
            components.bottomShooterMotor.set(ControlMode.PercentOutput, setting.bottom);
        }
        else {
            components.bottomShooterMotor.set(ControlMode.Velocity, setting.bottom);
        }
    }
    private void setTop(Setting setting) {
        if (setting.top == 0) {
            components.topShooterMotor.stopMotor();
        }
        else if (setting.isPercent) {
            components.topShooterMotor.set(ControlMode.PercentOutput, setting.top);
        }
        else {
            components.topShooterMotor.set(ControlMode.Velocity, setting.top);
        }
    }

    public void setLowHub() {
        set(Constants.Shooter.LowHubSetting);
    }
    public void setHighHub() {
        set(Constants.Shooter.HighHubSetting);
    }
    public void setEjectCargo() {
        set(Constants.Shooter.EjectCargoSetting);
    }
    public void setStopped() {
        set(Setting.Percent(0, 0));
    }

    public void setShooterAtDistance(double distance) {
        set(getSettingAtDistance(distance));
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
