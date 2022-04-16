package frc.robot.auto.autos;

import frc.robot.Constants;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;
import frc.robot.types.LimelightMode;

public class Auto3BallAim extends AutoBuilder {

    @Override
    public AutoInstruction build() {
        AutoInstruction first = first();

        // shoot
        // drive out of tarmac
        first
        .onInitialized(() -> {
            limelightHandler.setLimelightMode(LimelightMode.AutoAim);
        })

        .concurrently(
            shoot(0.5, 4),
            pause(3)
                .cargoTransfer(0.5, 5 * Constants.CargoTransfer.ClicksPerCargoLength)
        )//shoot high once

        .concurrently(
            driveSeconds(0.65, 1.35 * 0.75), // 1.5
            intake(2)
        )//drive foward and intake

        .cargoTransfer(0.5, 0.5 * Constants.CargoTransfer.ClicksPerCargoLength)

        .turn(0.6, 100)  //turn 100 degrees  
        
        .concurrently(
            drive(0.65, 100 * Constants.Auto.ClicksPerInchDriven)  
            .intake(2)
        )//drive for 100 in while intake is running for 4 seconds

        .turn(0.6, 90) //turn twards tarmac

        .blank(false)
        .periodically(() -> {
            return driveHandler.autoAim();
        })//set mode to auto aim

        .onCompleted(() -> {
            limelightHandler.setLimelightMode(LimelightMode.Driver);
        })//set mode to driver

        .concurrently(
            shoot(0.5, 4),//shoot high twice
            pause(2)
            .cargoTransfer(0.5, 10 * Constants.CargoTransfer.ClicksPerCargoLength)
            .completeOn(getTrigger("stop")),
            pause(4)
                .onCompleted(setTrigger("stop"))
            /*
            .onCompleted(() -> {
                cargoTransferHandler.set(-0.5);
            })
            .pause(3)
            .onCompleted(() -> {
                cargoTransferHandler.set(0);
            })
            */
        );

        return first;
    }
    
}
