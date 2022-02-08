package frc.robot.auto.autos;

import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;

public class Auto32 extends AutoBuilder {

    @Override
    public void build() {

        AutoInstruction first = first();

        first
        .left(0.1, 3)
        .right(0.1, 3)
        .pause(2)
        .concurrently(
            left(0.1, 3),
            right(0.1, 3)
        )
        .pause(2)
        .asynchronously(
            left(0.1, 3),
            right(0.1, 3)
        )
        .print("Still going?");
        
        begin(first);
    }
    
}
