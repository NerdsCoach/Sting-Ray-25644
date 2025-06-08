package teamCode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.IMU;

import teamCode.GoBildaPinpointDriver;

public class GyroSubsystem extends SubsystemBase
{
    private GoBildaPinpointDriver m_odo;

    public GyroSubsystem(GoBildaPinpointDriver odo)
    {
       this.m_odo = odo;
    }

    public void resetGyro()
    {
        this.m_odo.recalibrateIMU();
    }
}
