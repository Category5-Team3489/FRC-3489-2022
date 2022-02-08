package frc.robot.auto.autos;

import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;

public class Auto1 extends AutoBuilder {

    @Override
    public AutoInstruction build() {
        AutoInstruction first = first();

        first
        .drive(0.65, 1000);
        
        return first;
    }
    
}
