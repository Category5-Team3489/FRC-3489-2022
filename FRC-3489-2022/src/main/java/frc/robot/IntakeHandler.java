package frc.robot;

public class IntakeHandler {
    private ComponentsContainer components;
    
    public IntakeHandler(ComponentsContainer components){

    }
    public void intake(){
        components.intakeMotor.set(Constants.intakeSpeed);
    }
    public void output(){
        components.intakeMotor.set(-Constants.intakeSpeed);
    }
    public void stop(){
        components.intakeMotor.stopMotor();
    }
}
