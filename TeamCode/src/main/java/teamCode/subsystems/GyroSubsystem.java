package teamCode.subsystems;

import static teamCode.PoseStorage.odoHeading;
import static teamCode.PoseStorage.xEncoder;
import static teamCode.PoseStorage.yEncoder;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.UnnormalizedAngleUnit;

import teamCode.GoBildaPinpointDriver;
import teamCode.Pose2DUnNormalized;
import teamCode.PoseStorage;

public class GyroSubsystem extends SubsystemBase
{
    private GoBildaPinpointDriver m_odo;

    public GyroSubsystem(GoBildaPinpointDriver odo)
    {
       this.m_odo = odo;
    }

    public void resetGyro()
    {
        this.m_odo.setPosition(PoseStorage.zero);

//        this.m_odo.recalibrateIMU();
    }

    public void holdBasketPose()
    {
        this.m_odo.setPosition(new Pose2DUnNormalized(DistanceUnit.MM, 1270, -205,UnnormalizedAngleUnit.DEGREES,90.5));
    }
    public void holdChamberPose()
    {
        this.m_odo.setPosition(new Pose2DUnNormalized(DistanceUnit.MM, 73, -1087,UnnormalizedAngleUnit.DEGREES,-91.6));
    }
}
