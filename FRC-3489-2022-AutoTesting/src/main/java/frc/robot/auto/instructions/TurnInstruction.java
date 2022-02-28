package frc.robot.auto.instructions;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.Constants;
import frc.robot.auto.framework.AutoInstruction;

public class TurnInstruction extends AutoInstruction {

    private final static double kP = 0;
    private final static double kI = 0;
    private final static double kD = 0;

    PIDController controller;

    public TurnInstruction(double speed, double degrees) {

    }

    @Override
    public void init() {
        components.configAutoPIDTurn();
        components.navx.reset();
        controller = new PIDController(kP, kI, kD, Constants.FastPeriodicPerioid);
    }

    @Override
    public void periodic() {

    }

    @Override
    public void fastPeriodic() {
        double currentAngle = components.navx.getAngle();

    }

    @Override
    public void completed() {
        components.configNominalDrive();
    }

    @Override
    public String debug() {
        return getInstructionName();
    }
    
}
