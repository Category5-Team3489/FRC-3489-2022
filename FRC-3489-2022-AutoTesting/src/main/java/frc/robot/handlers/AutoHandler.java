package frc.robot.handlers;

import java.util.HashMap;
import java.util.Map;

import frc.robot.auto.autos.*;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;
import frc.robot.auto.framework.AutoRunner;
import frc.robot.framework.RobotHandler;

public class AutoHandler extends RobotHandler {

    public AutoRunner runner;

    private Map<Integer, AutoBuilder> autos = new HashMap<Integer, AutoBuilder>();

    public AutoHandler() {
        autos.put(1, new Auto1());
        autos.put(2, new Auto2());
        autos.put(3, new Auto3());
        autos.put(4, new Auto4());
        autos.put(5, new Auto5());
        autos.put(6, new Auto6());
        autos.put(7, new Auto7());
        autos.put(32, new Auto32());
    }

    @Override
    public void autonomousInit() {
        runner = new AutoRunner(robotManager);
        AutoBuilder auto = autos.get(getSelectedAuto());
        robotManager.copyReferences(auto);
        auto.setRunner(runner);
        AutoInstruction first = auto.build();
        auto.begin(first);
    }
    
    @Override
    public void autonomousPeriodic() {
        runner.periodic();
    }

    private int getSelectedAuto() {
        return 32;
    }
}
