package frc.robot.auto.autos;

import frc.robot.Constants;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;
import frc.robot.types.DriveState;

public class AutoNew extends AutoBuilder {

    @Override
    public AutoInstruction build() {
        AutoInstruction first = first();

        first.onInitialized(() -> {
            driveHandler.toggleToAim();
        })
        .blank(false)
        .periodically(() -> {
            return driveHandler.getDriveState() == DriveState.Driving;
        })
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
            return driveHandler.getDriveState() == DriveState.Driving;
        })
        .onCompleted(() -> {
            driveHandler.toggleToDrive();
        });

        return first;
    }
    
}
