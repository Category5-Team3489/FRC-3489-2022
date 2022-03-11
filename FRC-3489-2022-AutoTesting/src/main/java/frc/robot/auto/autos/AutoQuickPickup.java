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
            pause(2)
                .cargoTransfer(0.5, 5 * Constants.ClicksPerCargoLength)
        )
        .concurrently(
            driveSeconds(0.65, 1.5),
            intake(2)
        )
        .onCompleted(() -> {
            cargoTransferHandler.set(-0.5);
        })
        .pause(0.5)
        .onCompleted(() -> {
            cargoTransferHandler.set(0);
        })
        .concurrently(
            driveSeconds(-0.65, 1.5)
        )
        .concurrently(
            shoot(0.5, 4),
            pause(0.5)
            .cargoTransfer(0.5, 5 * Constants.ClicksPerCargoLength)
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
        .driveSeconds(0.65, 1.5);

        return first;
    }
    
}
