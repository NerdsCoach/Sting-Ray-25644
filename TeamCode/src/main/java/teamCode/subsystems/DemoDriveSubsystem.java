package teamCode.subsystems;
//change 3 spots
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.function.DoubleSupplier;

import teamCode.GoBildaPinpointDriver;
import teamCode.Pose2DUnNormalized;

public class DemoDriveSubsystem extends SubsystemBase
{
    public MecanumDrive m_drive;
    private DcMotor m_fLMotor;
    private DcMotor m_fRMotor;
    private DcMotor m_bLMotor;
    private DcMotor m_bRMotor;


    private double m_lastRecordedAngle;
    private double m_currentAngle;
    private double error;

    private GoBildaPinpointDriver m_odo;


    public DemoDriveSubsystem(MecanumDrive drive, teamCode.GoBildaPinpointDriver odo)
    {
        this.m_drive = drive;
        this.m_currentAngle = 0.0;
        this.m_odo = odo;
    }


    public void headingDrive(double leftX, double leftY, double rightX, double rightY)
    {
        m_drive.driveFieldCentric
                (
                        leftX * leftX * leftX * -0.7,//-1 //-0.7 for slow
                        leftY * leftY * leftY * -1.0,//-1
                        getJoystickAngle(rightX, rightY),
                        Math.toDegrees(m_odo.getHeading())
                );
    }

    public void autoHeadingDrive (DoubleSupplier targetX, DoubleSupplier targetY, DoubleSupplier targetAngle)
    {
        m_drive.driveFieldCentric
                (
                        this.getDeltaPosition(targetX.getAsDouble())[0],
                        this.getDeltaPosition(targetY.getAsDouble())[1],
                        getTurnPower(true,0),
                        Math.toDegrees(m_odo.getHeading())
                );
    }

    public double getJoystickAngle (double rightX, double rightY)
    {
        return getTurnPower(rightX > 0.5 || rightX < -0.5 || rightY > 0.5 || rightY < -0.5, Math.atan2(rightX, rightY * -1) * -1 * (180 / Math.PI));
    }


    public double getTurnPower(boolean deadband, double angle)
    {
        turnTo(deadband, angle);
//        System.out.println("Running!");

        if (Math.abs(error) > 6)
        {
            double motorPower = 0.4;//.5 for normal//0.4 is best for slow
            error = error - getAngle();
            return motorPower * error / 100 + (0.1 * (error / Math.abs(error)));
        }
        else
        {
            return 0.0;
        }
    }

    public void turnTo(boolean deadband, double angle)
    {
        double orientation = Math.toDegrees(m_odo.getHeading());
        m_odo.update();
        double desiredAngle;
        if (deadband)
        {
            desiredAngle = angle;
        }
        else
        {
            desiredAngle = orientation;
        }

        error = desiredAngle - orientation;

        if(error > 180)
        {
            error -= 360;
        }
        else if (error < -180)
        {
            error += 360;
        }

        turn(error, desiredAngle);
    }

    public void turn(double degrees, double desiredAngle)
    {
        resetAngle();

        error = degrees;
    }
    public void resetAngle()
    {
        m_odo.update();
        m_lastRecordedAngle = Math.toDegrees(m_odo.getHeading());
        m_currentAngle = 0;
    }

    public double getAngle()
    {
        double orientation = Math.toDegrees(m_odo.getHeading());
        double deltaAngle = orientation - m_lastRecordedAngle;

        if (deltaAngle > 180)
        {
            deltaAngle -= 360;
        }
        else if (deltaAngle <= -180)
        {
            deltaAngle += 360;
        }

        m_currentAngle += deltaAngle;
        m_lastRecordedAngle = orientation;
        return m_currentAngle;
    }
    public void resetOdo()
    {
        this.m_odo.resetPosAndIMU();
    }

    public Pose2DUnNormalized getPosition()
    {
        return this.m_odo.getPosition();
    }

    public void updateOdo()
    {
        this.m_odo.update();
    }

    public double[] getDeltaPosition(double target)
    {
        return new double[]
                {
                        (target - m_odo.getPosX()) / target,
                        (target - m_odo.getPosY()) / target
                };
    }
}