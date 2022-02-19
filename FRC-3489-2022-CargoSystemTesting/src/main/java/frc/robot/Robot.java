// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Robot extends TimedRobot {

  private Joystick leftDrive = new Joystick(0);
  private Joystick rightDrive = new Joystick(1);
  private Joystick manipulator = new Joystick(2);

  private WPI_TalonSRX leftFrontDriveMotor = new WPI_TalonSRX(1);
  private WPI_TalonSRX rightFrontDriveMotor = new WPI_TalonSRX(2);
  private WPI_TalonSRX leftFollowerDriveMotor = new WPI_TalonSRX(3);
  private WPI_TalonSRX rightFollowerDriveMotor = new WPI_TalonSRX(4);
  private DifferentialDrive drive;

  private WPI_TalonFX shooterBottom = new WPI_TalonFX(5);
  private WPI_TalonFX shooterTop = new WPI_TalonFX(6);
  private WPI_TalonSRX cargoTransfer = new WPI_TalonSRX(7);
  private WPI_TalonSRX intake = new WPI_TalonSRX(8);

  private static final int Shooter = 7;
  private static final int Cargo = 9;
  private static final int Intake = 11;
  private static final int ShooterKill = 8;
  private static final int CargoKill = 10;
  private static final int IntakeKill = 12;
  private static final int EStop = 2;

  private static final int SwitchFront = 13;

  private Subsystem activeSubsystem = Subsystem.Intake;

  private boolean isFrontSwitched = false;

  private double shooterSpeed = 0;
  private double cargoSpeed = 0;
  private double intakeSpeed = 0;

  enum Subsystem
  {
    Shooter,
    Cargo,
    Intake
  }

  @Override
  public void robotInit() {
    rightFrontDriveMotor.setInverted(true);
    rightFollowerDriveMotor.setInverted(true);
    leftFollowerDriveMotor.follow(leftFrontDriveMotor);
    rightFollowerDriveMotor.follow(rightFrontDriveMotor);

    drive = new DifferentialDrive(leftFrontDriveMotor, rightFrontDriveMotor);
  }

  @Override
  public void teleopInit() {

  }

  private boolean shouldSwitchFront() {
    return leftDrive.getRawButtonPressed(SwitchFront) || rightDrive.getRawButtonPressed(SwitchFront);
  }

  @Override
  public void teleopPeriodic() {
    if (shouldSwitchFront())
      isFrontSwitched = !isFrontSwitched;
    double leftY = leftDrive.getY();
    double rightY = rightDrive.getY();
    double leftSpeed = 0;
    double rightSpeed = 0;
    if (Math.abs(leftY) >= 0.1) leftSpeed = leftY;
    if (Math.abs(rightY) >= 0.1) rightSpeed = rightY;
    if (isFrontSwitched)
      drive.tankDrive(rightSpeed, leftSpeed);
    else
      drive.tankDrive(-leftSpeed, -rightSpeed);

    if (manipulator.getRawButton(Shooter))
      activeSubsystem = Subsystem.Shooter;
    if (manipulator.getRawButton(Cargo))
      activeSubsystem = Subsystem.Cargo;
    if (manipulator.getRawButton(Intake))
      activeSubsystem = Subsystem.Intake;

    if (manipulator.getRawButton(ShooterKill))
      shooterSpeed = 0;
    if (manipulator.getRawButton(CargoKill))
      cargoSpeed = 0;
    if (manipulator.getRawButton(IntakeKill))
      intakeSpeed = 0;

    if (manipulator.getRawButton(EStop)) {
      shooterSpeed = 0;
      cargoSpeed = 0;
      intakeSpeed = 0;
    }

    controlActiveSubsystem();

    switch (activeSubsystem) {
      case Shooter:
        shooterBottom.set(-shooterSpeed);
        shooterTop.set(shooterSpeed);
        break;
      case Cargo:
        cargoTransfer.set(cargoSpeed);
        break;
      case Intake:
        intake.set(intakeSpeed);
        break;
    }
  }

  private void controlActiveSubsystem() {

    if (Math.abs(manipulator.getY()) < 0.1) return;

    switch (activeSubsystem) {
      case Shooter:
        shooterSpeed += getAdjust();
        break;
      case Cargo:
        cargoSpeed += getAdjust();
        break;
      case Intake:
        intakeSpeed += getAdjust();
        break;
    }

    shooterSpeed = clamp(shooterSpeed, -1, 1);
    cargoSpeed = clamp(cargoSpeed, -1, 1);
    intakeSpeed = clamp(intakeSpeed, -1, 1);
  }

  private double getAdjust() {
    return manipulator.getY() * 0.01;
  }

  private static double clamp(double val, double min, double max) {
    return Math.max(min, Math.min(max, val));
  }

  @Override
  public void disabledInit() {

  }

  @Override
  public void disabledPeriodic() {

  }
}
