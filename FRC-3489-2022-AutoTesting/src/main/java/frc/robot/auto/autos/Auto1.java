package frc.robot.auto.autos;

import frc.robot.Constants.Auto;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;

public class Auto1 extends AutoBuilder {

    @Override
    public AutoInstruction build() {
        AutoInstruction first = first();

        first
        .drive(0.65, 8 * Auto.DriveClicksPerFoot)
        .pause(1)
        .turn(0.4, -60)
        .drive(0.65, 6 * Auto.DriveClicksPerFoot);
        
        return first;
    }
    
}
