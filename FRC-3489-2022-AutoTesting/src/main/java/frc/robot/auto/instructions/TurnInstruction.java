package frc.robot.auto.instructions;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import frc.robot.Constants;
import frc.robot.auto.framework.AutoInstruction;

public class TurnInstruction extends AutoInstruction {

    private double speed;
    private double degrees;

    public TurnInstruction(double speed, double degrees) {
        this.speed = speed;
        this.degrees = degrees;
    }

    @Override
    public void init() {
        components.navx.reset();
        driveHandler.aimController.reset();
    }

    @Override
    public void periodic() {
        //System.out.println(cachedOutput);
        double offset = degrees - components.navx.getAngle();
        driveHandler.aim(true, offset);
        if (driveHandler.aimController.atSetpoint()) {
            complete();
        }
    }

    @Override
    public void fastPeriodic() {
        /*
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
