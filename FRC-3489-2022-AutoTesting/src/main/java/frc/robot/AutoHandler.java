package frc.robot;

public class AutoHandler {

    private AutoRunner autoRunner;

    public AutoHandler(AutoRunner autoRunner) {
        this.autoRunner = autoRunner;
    }

    public void init() {
        testAuto();
    }
    
    public void periodic() {
        autoRunner.periodic();
    }

    private void testAuto() {
        drive(1000).onFinished(() -> {
            drive(2000);
            // figure out events between instructions
        });
    }

    private DriveInstruction drive(double clicks) {
        DriveInstruction instruction = new DriveInstruction();
        autoRunner.beginExecution(instruction);
        return instruction;
    }
}
