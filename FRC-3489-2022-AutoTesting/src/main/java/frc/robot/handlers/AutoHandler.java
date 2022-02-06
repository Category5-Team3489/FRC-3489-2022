package frc.robot.handlers;

import java.util.HashMap;
import java.util.Map;

import frc.robot.auto.autos.Auto32;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoRunner;
import frc.robot.framework.RobotHandler;

public class AutoHandler extends RobotHandler {

    private AutoRunner autoRunner;

    private Map<Integer, AutoBuilder> autos = new HashMap<Integer, AutoBuilder>();

    public AutoHandler() {
        autos.put(32, new Auto32());
    }

    @Override
    public void autonomousInit() {
        autoRunner = new AutoRunner(robotManager);
        AutoBuilder auto = autos.get(getSelectedAuto());
        robotManager.copyReferences(auto);
        auto.setRunner(autoRunner);
        auto.build();
    }
    
    @Override
    public void autonomousPeriodic() {
        autoRunner.periodic();
    }

    private int getSelectedAuto() {
        return 32;
    }
}
