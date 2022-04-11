package frc.robot.auto.instructions;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import frc.robot.Constants;
import frc.robot.auto.framework.AutoInstruction;

public class TurnInstruction extends AutoInstruction {

    private final static double kP = -0.1;
    private final static double kI = -0.0001;//-0.001;//-0.001;//-0.05;//0.005;
    private final static double kD = -0.01;//.001;
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
        controller = new PIDController(kP, kI, kD, Constants.FastPeriodicPeriod);
        controller.setSetpoint(degrees);
        controller.setTolerance(kTolerance);
    }

    @Override
    public void periodic() {
        //System.out.println(cachedOutput);
    }

    private double cachedOutput = 0;

    @Override
    public void fastPeriodic() {
        double currentAngle = components.navx.getAngle();
        double controllerOutput = controller.calculate(currentAngle);
        double output = MathUtil.clamp(controllerOutput, -speed, speed);
        components.drive.tankDrive(-output, output);
        //controller.setIntegratorRange(minimumIntegral, maximumIntegral);
        cachedOutput = currentAngle;

        //System.out.println(output);

        // Uncomment when done tuning
        if (controller.atSetpoint())
            complete();
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
