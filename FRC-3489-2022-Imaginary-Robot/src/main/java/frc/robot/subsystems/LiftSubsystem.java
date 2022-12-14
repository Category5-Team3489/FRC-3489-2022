// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LiftSubsystem extends SubsystemBase {
  
  //Motor
  private TalonFX liftMotor = new TalonFX(Constants.liftMotorCanID);
  
  //Pneumatics
  private Solenoid liftBrakeSolenoid = new Solenoid(PneumaticsModuleType.REVPH,Constants.liftBrakSolenoidId);

  public LiftSubsystem() {
  }

  public void RaiseLift() {
    setBrake(false);
    
  }

  public void LowerLift() {

  }

  public void setBrake(boolean engaged){
     liftBrakeSolenoid.set(engaged);
  }

  public boolean getBrakeStatus(){
    return liftBrakeSolenoid.get();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
