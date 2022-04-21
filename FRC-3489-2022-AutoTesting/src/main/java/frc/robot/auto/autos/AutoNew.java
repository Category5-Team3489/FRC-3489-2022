package frc.robot.auto.autos;

import java.util.Map;

import frc.robot.Constants;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;
import frc.robot.types.DriveState;

public class AutoNew extends AutoBuilder {

    @Override
    public Map.Entry<AutoInstruction, AutoInstruction> build() {
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
        .withTimeout(7);

        return first;
    }
    
}
