package frc.robot;

import frc.robot.handlers.ShooterHandler;
import frc.robot.handlers.ShooterHandler.DistanceSetting;

public final class Constants {
    public final static class Buttons {
        public final static class Drive {
            // Shared
            
            // Left

            // Right
        }

        // Manipulator
        public final static class Manipulator {
            public static final int ToggleIntake = 2;
            public static final int Shoot = 1;
            public static final int AutoShoot = 4;
    
            public static final int SetLowHub = 8;
            public static final int SetHighHub = 7;
            public static final int SetEjectCargo = 3;
            public static final int SetStopped = 6;
        }
    }

    public final static class Intake {
        public static final double ReverseMotorSpeed = -0.8;
        public static final double ForwardMotorSpeed = 1;
    }

    public final static class CargoTransfer {
        public final static double ClicksPerCargoLength = 11159 * 1.25d;
        public final static double ShootMotorSpeed = -0.6;
        public static final double ReverseIndexMotorSpeed = 0.6;
        public static final double ForwardIndexMotorSpeed = -0.6;
        public static final double ManualCargoSystemControlThreshhold = 0.75;
    }

    public final static class Drive {
        public static final double JoystickDeadzone = 0.1; // +-percent
        public static final double CancelAutoShootThreshold = 0.4; // +-percent
    }

    public final static class AutoShoot {
        // Aim
        public static final double AimP = 0.00625;
        public static final double AimI = 0;
        public static final double AimD = 0;
        public static final double AimTolerance = 1.5; // +-degrees
        public static final double AimFrictionConstant = 0.27; // percent

        // HoldAim
        public static final double HoldAimP = 0.00625;
        public static final double HoldAimI = 0;
        public static final double HoldAimD = 0;
        public static final double HoldAimTolerance = 0.5; // +-degrees
        public static final double HoldAimFrictionConstant = 0.27; // percent

        public static final double AimAgainThreshold = 4; // +-degrees;
    }

    public final static class Limelight {
        public static final Number PipelineOff = 1;
        public static final Number PipelineAutoAim = 0;
        public static final double TargetVisibleTimeout = 0.5; // seconds
        public static final double RobotTargetYDiff = 67.625; // inches
        public static final double AngleOfElevation = 46.13; // degrees
    }

    public final static class Shooter {
        public static final double ReadyToShootThreshold = 0.05; // +-percent
        
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
