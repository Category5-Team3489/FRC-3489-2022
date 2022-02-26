// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
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

  private WPI_TalonFX shooterBottom = new WPI_TalonFX(7); // 7
  private WPI_TalonFX shooterTop = new WPI_TalonFX(8); // 8
  private WPI_TalonSRX cargoTransfer = new WPI_TalonSRX(5); // 5
  private WPI_TalonSRX intake = new WPI_TalonSRX(6); // 6
  // 9

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

  private SlewRateLimiter leftLimiter = new SlewRateLimiter(10);
  private SlewRateLimiter rightLimiter = new SlewRateLimiter(10);

  private Solenoid solenoid = new Solenoid(36, PneumaticsModuleType.REVPH, 0);

  private long loop = 0;

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
    shooterSpeed = 0;
    cargoSpeed = 0;
    intakeSpeed = 0;
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
    double leftSpeed = leftLimiter.calculate(leftY);
    double rightSpeed = rightLimiter.calculate(rightY);
    if (Math.abs(leftY) < 0.1) leftSpeed = 0;
    if (Math.abs(rightY) < 0.1) rightSpeed = 0;
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

    if (loop % 50 == 0) {
      System.out.println("Shooter: " + ((int)(shooterSpeed * 100)));
      System.out.println("Cargo: " + ((int)(cargoSpeed * 100)));
      System.out.println("Intake: " + ((int)(intakeSpeed * 100)));
    }
    loop++;
  }

  private void controlActiveSubsystem() {

    if (Math.abs(manipulator.getY()) < 0.2) return;

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
    return manipulator.getY() * 0.005;
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
