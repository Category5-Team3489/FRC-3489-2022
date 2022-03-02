package frc.robot.auto.instructions;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import frc.robot.Constants;
import frc.robot.auto.framework.AutoInstruction;

public class TurnInstruction extends AutoInstruction {

    private final static double kP = 0;
    private final static double kI = 0;
    private final static double kD = 0;
    private final static double kTolerance = 2;

    private PIDController controller;

    private double speed;
    private double degrees;

    public TurnInstruction(double speed, double degrees) {
        this.speed = speed;
        this.degrees = degrees;
    }

    @Override
    public void init() {
        components.navx.reset();
        controller = new PIDController(kP, kI, kD, Constants.FastPeriodicPerioid);
        controller.setSetpoint(degrees);
        controller.setTolerance(kTolerance);
    }

    @Override
    public void periodic() {

    }

    @Override
    public void fastPeriodic() {
        double currentAngle = components.navx.getAngle();
        double controllerOutput = controller.calculate(currentAngle);
        double output = MathUtil.clamp(controllerOutput, -speed, speed);
        components.drive.tankDrive(-output, output);

        // Uncomment when done tuning
        /*
        if (controller.atSetpoint())
            complete();
        */
    }

    @Override
    public void completed() {
        components.drive.stopMotor();
    }

    @Override
    public String debug() {
        return getInstructionName();
    }
    
}
