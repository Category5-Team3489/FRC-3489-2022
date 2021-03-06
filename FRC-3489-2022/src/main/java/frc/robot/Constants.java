// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    // Drive motor CAN bus ids  
    public static final int FrontLeftDriveId = 1;
    public static final int FrontRightDriveId = 2;
    public static final int BackLeftDriveId = 3;
    public static final int BackRightDriveId = 4;

    //intake motor id
    public static final int IntakeMotorId = 5;

    //intake motor speed
    public static final double intakeSpeed = 0.8;

    //shooter motor id
    public static final int ShooterTopId = 6;
    public static final int ShooterBottomId = 7;

    //cargo mover id
    public static final int CargoMoverId = 8;

    //Climber id
    public static final int ClimberId = 9;

    //drive speed
    public static final double driveBackwardSpeed = -0.65;
    public static final double driveForwardSpeed = 0.65;

    //shooter speed
    public static final double ShooterLowSpeed = 0.35;
    public static final double ShooterHighSpeed = 0.65;

    // Joystick driver station port indexes
    public static final int LeftDriveJoystick = 0;
    public static final int RightDriveJoystick = 1;
    public static final int ManipulatorJoystick = 2;

    //Auto 1 Encoder Clicks
    public static final double auto1ForwardEncoderClicks = 4000;
    public static final double auto1TurnLeft = 2000;

    //Auto 6 Encoder Clicks
    public static final double auto6ShootEncoderClicks = 4000;
    public static final double auto6IntakeStartClicks =4000;
    public static final double auto6IntakeStopClicks = 4000;
    public static final double auto6CargoTransferClicks = 4000;
    public static final double auto6TurnRightEncoderClicks = 4000;
    public static final double auto6ForwardEncoderClicks = 4000;

    //auto 2 constants
    public static final double auto2DriveForwardClicks = 4000; 
    public static final double auto2DriveBackwardClicks = 4000;
    public static final double auto2ShootHighClicks = 4000;
    public static final double auto2CargotransferClicks = 4000;

    //auto 3 constants
    public static final double Auto3TransferClicks = 4000;
    public static final double Auto3DriveForwardClicks = 4000; 
    public static final double Auto3DriveBackwardClicks = 4000;
    public static final double Auto3ShootHighClicks = 4000;
    
    //auto 5 constants
    public static final double auto5LeftMotorForTurn = 0.2;
    public static final double auto5RightMotorForTurn = 0.4;
    public static final double auto5DriveForwardClicks = 4000;
    public static final double auto5TurnLeftClicks = 4000;

    //auto 4 Constants 
    public static final double auto4ShootHighClicks = 4000;
    public static final double auto4CargoMoverClicks = 4000; 

    //auto 7 constants 
    public static final double auto7ShootHighClicks = 4000;
    public static final double auto7DriveForwardClicks = 4000;
    public static final double auto7TransferClicks = 4000; 
    public static final double auto7DriveTurnClicks = 4000;

    //teloscope motor ids
    public static final int TeloscpeMotorId = 10;
  
  

 

    
}
    
