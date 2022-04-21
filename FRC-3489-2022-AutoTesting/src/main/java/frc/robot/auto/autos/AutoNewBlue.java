package frc.robot.auto.autos;

import frc.robot.Constants;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;
import frc.robot.types.DriveState;

public class AutoNewBlue extends AutoBuilder {

    @Override
    public AutoInstruction build() {
        AutoInstruction first = first();

        first
        .concurrently(
            shoot(0.5, 4),
            pause(3)
                .cargoTransfer(0.5, 5 * Constants.CargoTransfer.ClicksPerCargoLength)
        )// shoot high once
        .concurrently(
            driveSeconds(0.65, 1.35 * 0.75), // 1.5
            intake(2)
        )
        .concurrently(
            cargoTransfer(0.5, 0.5 * Constants.CargoTransfer.ClicksPerCargoLength),
            driveSeconds(-0.65, 1 * 0.75)
        )
        .onCompleted(() -> {
            driveHandler.toggleToAim();
        })
        .blank(false)
        .periodically(() -> {
            driveHandler.loop(true);
            return driveHandler.getDriveState() == DriveState.Driving;
        })
        .turn(0, 80)
        .concurrently(
            driveSeconds(0.65, 1), // 1.5
            intake(2)
        )
        .turn(0, -45)
        .concurrently(
            shoot(0.5, 3),
            pause(3)
                .cargoTransfer(0.5, 5 * Constants.CargoTransfer.ClicksPerCargoLength)
        );



        return first;
    }
    
}
