package frc.robot.auto.autos;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;

public class Diagnostic extends AutoBuilder {

    @Override
    public AutoInstruction build() {
        // TODO Auto-generated method stub
        return null;
    }
    
    /*
    @Override
    public AutoInstruction build() {
        AutoInstruction first = first();

        first
        .periodically(() -> {

        });

        first
        .concurrently(diagnostic.intakeError())
        .concurrently(diagnostic.cargoTransferError())
        .concurrently(diagnostic.topShooterError())
        .concurrently(diagnostic.bottomShooterError())
        .concurrently(diagnostic.leftDriveForwardError())
        .concurrently(diagnostic.leftDriveReverseError())
        .concurrently(diagnostic.rightDriveForwardError())
        .concurrently(diagnostic.rightDriveReverse())
        .concurrently(diagnostic.telescopeError())
        .concurrently(diagnostic.navxError())
        .concurrently(diagnostic.intakeLaserError());

        AutoInstruction testIntake = testMotor(first,
        () -> { // start motor
            components.intakeMotor.set(Constants.Speeds.ForwardIntakeMotorSpeed);
        },
        () -> { // stop motor
            components.intakeMotor.stopMotor();
        },
        4, // test time
        () -> { // get motor speed
            return components.intakeMotor.getSelectedSensorVelocity();
        },
        () -> { // get motor current
            return components.intakeMotor.getStatorCurrent();
        });

        // print out temperatures for motors

        // make sure it isnt triggered instantly
        // for laser sensor test, print out to trigger sensor, then wait for 10 sec, if they dont trigger it print test failed

        // for navx test just print out all of the values for it at an instant

        // print out time for climber telescope moving up and down, and compare to baseline

        return first;
    }

    double avgSpeed = 0.0;
    double lastSpeed = 0.0;
    double maxSpeed = 0.0;

    double avgCurrent = 0.0;
    double lastCurrent = 0.0;
    double maxCurrent = 0.0;

    double firstTemp = 0.0;
    double lastTemp = 0.0;

    private AutoInstruction testMotor(AutoInstruction first, Runnable startMotor, Runnable stopMotor, double time, Supplier<Double> getSpeed, Supplier<Double> getCurrent, Supplier<Double> getTemp) {
        
        Timer timer = new Timer();

        return first
        .onInitialized(() -> {
            timer.start();
            firstTemp = getTemp.get();
        })
        .periodically(() -> {
            if (timer.hasElapsed(time)) {
                lastSpeed = getSpeed.get();
                lastCurrent = getCurrent.get();
                lastTemp = getTemp.get();
                stopMotor.run();
                return true;
            }
            else {
                startMotor.run();
            }
            return false;
        });
    }
    */
}
