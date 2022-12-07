// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
  // Neo motors
  private final CANSparkMax frontLeftDriveMotor = new CANSparkMax(Constants.FrontLeftDriveMotorCanId, MotorType.kBrushless);
  private final CANSparkMax frontRightDriveMotor = new CANSparkMax(Constants.FrontRightDriveMotorCanId, MotorType.kBrushless);
  private final CANSparkMax backLeftDriveMotor = new CANSparkMax(Constants.BackLeftDriveMotorCanId, MotorType.kBrushless);
  private final CANSparkMax backRightDriveMotor = new CANSparkMax(Constants.BackRightDriveMotorCanId, MotorType.kBrushless);
  
  public final DifferentialDrive drive = new DifferentialDrive(frontLeftDriveMotor, frontRightDriveMotor);
  
  /** Creates a new ExampleSubsystem. */
  public Drivetrain() {
    setUpDriveMotors();
  }
  
  private void setUpDriveMotors() {
    // Restore factory defaults for motors
    frontLeftDriveMotor.restoreFactoryDefaults();
    frontRightDriveMotor.restoreFactoryDefaults();
    backLeftDriveMotor.restoreFactoryDefaults();
    backRightDriveMotor.restoreFactoryDefaults();

    // Set motors to brake mode
    frontLeftDriveMotor.setIdleMode(IdleMode.kBrake);
    frontRightDriveMotor.setIdleMode(IdleMode.kBrake);
    backLeftDriveMotor.setIdleMode(IdleMode.kBrake);
    backRightDriveMotor.setIdleMode(IdleMode.kBrake);

    // Invert right side motors
    frontLeftDriveMotor.setInverted(true);
    frontRightDriveMotor.setInverted(true);
    // Set back motors to follow front motors
    backLeftDriveMotor.follow(frontLeftDriveMotor);
    backRightDriveMotor.follow(frontRightDriveMotor);

    // Save settings, MUST BE DONE OR ELSE MOTOR CONTROLLER RESETS TO OLD SETTINGS AFTER BROWNOUT
    frontLeftDriveMotor.burnFlash();
    frontRightDriveMotor.burnFlash();
    backLeftDriveMotor.burnFlash();
    backRightDriveMotor.burnFlash();
  }

  public void drive(double leftSpeed, double rightSpeed) {
    drive.tankDrive(leftSpeed, rightSpeed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  // TODO add diagnostics

  // TODO add shuffleboard stuff
}
