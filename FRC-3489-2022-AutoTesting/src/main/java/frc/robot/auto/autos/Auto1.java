package frc.robot.auto.autos;

import frc.robot.Constants;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;

public class Auto1 extends AutoBuilder {

    @Override
    public AutoInstruction build() {
        AutoInstruction first = first();

        first
        .drive(0.65, 12 * Constants.DriveClicksPerFoot) //12 ft at 0.65% 
        .pause(1)
        .turn(0.4, 30)
        .drive(0.65, 8.4375 * Constants.DriveClicksPerFoot) // 8.4375 ft at 0.65% 
        .turn(0.4, 120)
        .drive(0.65, 3 * Constants.DriveClicksPerFoot); // 3 ft for 0.65% 

        return first;
    }
    
}