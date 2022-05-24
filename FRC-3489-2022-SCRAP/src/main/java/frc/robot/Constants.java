package frc.robot;

import frc.robot.handlers.ShooterHandler;
import frc.robot.handlers.ShooterHandler.DistanceSetting;

public final class Constants {
    public final static class Buttons {
        // Drive Shared

        // Drive Left

        // Drive Right

        // Manipulator
        public static final int ToggleIntake = 2;
        public static final int Shoot = 1;
        public static final int AutoShoot = 4;

        public static final int SetLowHub = 8;
        public static final int SetHighHub = 7;
        public static final int SetEjectCargo = 3;
        public static final int SetStopped = 6;
    }

    public final static class Drive {
        public static final double JoystickDeadzone = 0.1;
        public static final double CancelAutoShootThreshold = 0.4;
    }

    public final static class Shooter {
        public static final ShooterHandler.Setting LowHubSetting = ShooterHandler.Setting.Percent(0.3, 0.3);
        public static final ShooterHandler.Setting HighHubSetting = ShooterHandler.Setting.Percent(0.5, 0.5);
        public static final ShooterHandler.Setting EjectCargoSetting = ShooterHandler.Setting.Percent(0.2, 0.2);

        public static final DistanceSetting[] DistanceSettingTable = {
            DistanceSetting.S(50,       3200,   13400),
            DistanceSetting.S(60,       3000,   13700),
            DistanceSetting.S(70.9,     3000,   14500),
            DistanceSetting.S(77.4,     3000,   14500),
            DistanceSetting.S(84.5,     3250,   14500),
            DistanceSetting.S(92.4,     3575,   14900),
            DistanceSetting.S(101.4,    3400,   14820),
            DistanceSetting.S(108.5,    3000,   15000),
            DistanceSetting.S(115.5,    3250,   15600),
            DistanceSetting.S(123,      3000,   16750),
            DistanceSetting.S(137,      3250,   16000),
            DistanceSetting.S(154,      3500,   20000),
        };
    }
}
