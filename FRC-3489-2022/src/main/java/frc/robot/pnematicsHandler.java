package frc.robot;

public class pnematicsHandler {
    ComponentsContainer container;

    public void setBottomTrue(){
        container.bottomLeftSolenoid.set(true);
        container.bottomRightSolenoid.set(true);
    }
    public void setBottomFalse(){
        container.bottomRightSolenoid.set(false);
        container.bottomLeftSolenoid.set(false);
    }
    public void setTopTrue(){
        container.topLeftSolenoid.set(true);
        container.topRightSolenoid.set(true);
    }
    public void setTopFalse(){
        container.topRightSolenoid.set(false);
        container.topLeftSolenoid.set(false);
    }
    public void setBrakeTrue(){
        container.BrakeSolenoid.set(true);
    }
    public void setBrakeFalse(){
        container.BrakeSolenoid.set(false);
    }
}
