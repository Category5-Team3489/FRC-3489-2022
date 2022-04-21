package frc.robot.auto.autos;

import frc.robot.Constants;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;

public class AutoQuickPickup extends AutoBuilder {

    @Override
    public AutoInstruction build() {
        AutoInstruction first = first();

        // shoot
        // drive out of tarmac
        first
        .concurrently(
            shoot(0.5, 4),
            pause(3)
                .cargoTransfer(0.5, 5 * Constants.CargoTransfer.ClicksPerCargoLength)
        )// shoot high once
        .onCompleted(() -> {
            System.out.println("Shot at dist: " + driveHandler.getDistanceEstimate());
        })
        .concurrently(
            driveSeconds(0.65, 1.35 * 0.75), // 1.5
            intake(2)
        )//drive foward and intake cargo
        .cargoTransfer(0.5, 0.5 * Constants.CargoTransfer.ClicksPerCargoLength)
        .onCompleted(() -> {
            System.out.println("Stopped at dist: " + driveHandler.getDistanceEstimate());
        })
        .concurrently(
            driveSeconds(-0.65, 1 * 0.75)
        )// drive back to tarmac line
        .concurrently(
            shoot(0.5, 4),//shoot high once
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
        )
        .onCompleted(() -> {
            System.out.println("Stopped at dist: " + driveHandler.getDistanceEstimate());
        });

        return first;
    }
    
}
