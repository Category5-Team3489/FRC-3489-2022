package frc.robot.auto.autos;

import frc.robot.Constants;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;
import frc.robot.types.LimelightMode;

public class AutoQuickPickupAim extends AutoBuilder {

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
        )
        .concurrently(
            driveSeconds(0.65, 1.35 * 0.75), // 1.5
            intake(2)
        )
        .cargoTransfer(0.5, 0.5 * Constants.CargoTransfer.ClicksPerCargoLength)
        .concurrently(
            driveSeconds(-0.65, (1 * 0.75) - 0.1)
        )
        .blank(false)
        .periodically(() -> {
            return driveHandler.autoAim();
        })
        .onCompleted(() -> {
            limelightHandler.setLimelightMode(LimelightMode.Driver);
        })
        .concurrently(
            shoot(0.5, 4),
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
