package frc.robot.auto.autos;

import frc.robot.Constants;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;
import frc.robot.types.DriveState;

public class AutoNewRed extends AutoBuilder {

    @Override
    public AutoInstruction build() {
        AutoInstruction first = new AutoNew().build();

        

        return first;
    }
    
}
