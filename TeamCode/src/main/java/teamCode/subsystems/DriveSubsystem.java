package teamCode.subsystems;
//change 3 spots
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.UnnormalizedAngleUnit;

import java.util.function.DoubleSupplier;

import teamCode.DriveToPoint;
import teamCode.GoBildaPinpointDriver;
import teamCode.Pose2DUnNormalized;

public class DriveSubsystem extends SubsystemBase
{
    public MecanumDrive m_drive;

    private DcMotor m_leftFront;
    private DcMotor m_rightFront;
    private DcMotor m_leftBack;
    private DcMotor m_rightBack;

    private GoBildaPinpointDriver m_odo;
    DriveToPoint nav = new DriveToPoint();
//    private int m_fLPos;
//    private int m_fRPos;
//    private int m_bLPos;
//    private int m_bRPos;

    private double m_lastRecordedAngle;
    private double m_currentAngle;
    private double error;

//    IMU m_imu;

//    private PinPointOdometrySubsystem m_pinPointOdometrySubsystem;


    public DriveSubsystem(MecanumDrive drive, teamCode.GoBildaPinpointDriver odo)
    {
        this.m_drive = drive;
//        this.m_lastRecordedAngle = new Orientation();
        this.m_currentAngle = 0.0;
        this.m_odo = odo;
        this.m_odo.setOffsets(68,-178);
        this.m_odo.setEncoderResolution(teamCode.GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        this.m_odo.setEncoderDirections(teamCode.GoBildaPinpointDriver.EncoderDirection.REVERSED, teamCode.GoBildaPinpointDriver.EncoderDirection.FORWARD);
        this.m_odo.setPosition(new Pose2DUnNormalized(DistanceUnit.MM, 0,0, UnnormalizedAngleUnit.DEGREES, 0.0));
//        this.m_leftFront = leftFront;
//        this.m_rightFront = rightFront;
//        this.m_leftBack = leftBack;
//        this.m_rightBack = rightBack;
    }

    public void headingDrive(double leftX, double leftY, double rightX, double rightY)
    {
        m_drive.driveFieldCentric
                (
                        leftX * leftX * leftX * -1.0,//-1 //-0.7 for slow
                        leftY * leftY * leftY * -1.0,//-1
                        getJoystickAngle(rightX, rightY),
                        Math.toDegrees(m_odo.getHeading())
                );
        m_odo.update();
//        System.out.println("Error: " + error);
//        getTurnPower(rightX, rightY);

//        System.out.println("Error: " + error);
//        System.out.println("Error: " + error);
//        System.out.println("Turn angle: " + Math.atan2(rightX, rightY * -1) * -1 * (180 / Math.PI));
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

    public double getJoystickAngle (double rightX, double rightY) /* angle in degrees*/
    {
        return getTurnPower(rightX > 0.5 || rightX < -0.5 || rightY > 0.5 || rightY < -0.5, Math.atan2(rightX, rightY * -1) * -1 * (180 / Math.PI));
    }


    public double getTurnPower(boolean deadband, double angle)
    {
        turnTo(deadband, angle);
//        System.out.println("Running!");

        if (Math.abs(error) > 6)
        {
            double motorPower = 0.5;//.5 for normal//0.4 is best for slow
            error = error - getAngle();
//            this.m_robot.driveWithMotorPowers(motorPower, -motorPower, motorPower, -motorPower);
//            this.m_drive.driveWithMotorPowers(motorPower, -motorPower, motorPower, -motorPower);
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

        m_odo.update();
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
    public void autoSetPower()
    {
        //nav calculates the power to set to each motor in a mecanum or tank drive. Use nav.getMotorPower to find that value.
        this.m_leftFront.setPower(nav.getMotorPower(DriveToPoint.DriveMotor.LEFT_FRONT));
        this.m_rightFront.setPower(nav.getMotorPower(DriveToPoint.DriveMotor.RIGHT_FRONT));
        this.m_leftBack.setPower(nav.getMotorPower(DriveToPoint.DriveMotor.LEFT_BACK));
        this.m_rightBack.setPower(nav.getMotorPower(DriveToPoint.DriveMotor.RIGHT_BACK));
        System.out.println("Power" + nav.getMotorPower(DriveToPoint.DriveMotor.LEFT_FRONT));
    }
}