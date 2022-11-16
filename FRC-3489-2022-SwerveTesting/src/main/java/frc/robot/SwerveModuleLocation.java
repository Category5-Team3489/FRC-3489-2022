package frc.robot;

public enum SwerveModuleLocation {
    FrontLeft(0), // drive: 1, steer: 2, encoder: 12
    FrontRight(1), // drive: 3, steer: 4, encoder: 34
    BackRight(2), // drive: 5, steer: 6, encoder: 56
    BackLeft(3); // drive 7, steer: 8, encoder: 18

    private final int moduleIndex;

    SwerveModuleLocation(final int moduleIndex) {
        this.moduleIndex = moduleIndex;
    }

    public int getModuleIndex() {
        return moduleIndex;
    }

    public int getDriveCanId() {
        return moduleIndex * 2 + 1;
    }

    public int getSteeringCanId() {
        return (moduleIndex + 1) * 2;
    }

    public int getSteeringEncoderCanId() {
        switch (moduleIndex) {
            case 0:
                return 12;
            case 1:
                return 34;
            case 2:
                return 56;
            case 3:
                return 18;
            default:
                return -1;
        }
    }

    public double getDriveNegation() {
        switch (moduleIndex) {
            case 0:
                return -1;
            case 1:
                return 1;
            case 2:
                return 1;
            case 3:
                return -1;
            default:
                return 0;
        }
    }
}
