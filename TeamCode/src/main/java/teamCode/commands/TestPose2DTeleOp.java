package teamCode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.UnnormalizedAngleUnit;
import org.firstinspires.ftc.teamcode.Libs.GoBilda.DriveToPoint;
import org.firstinspires.ftc.teamcode.Libs.GoBilda.GoBildaPinpointDriver;
//import org.firstinspires.ftc.teamcode.Libs.GoBilda.TeleOpDriveToPoint;

import teamCode.Auto.Pose2DUnNormalized;
import teamCode.subsystems.DriveSubsystem;
import teamCode.subsystems.PinPointOdometrySubsystem;


//@Disabled
public class TestPose2DTeleOp extends CommandBase
{
         //Initializing Motors & Servos
        public MecanumDrive m_drive;
        private DcMotor m_leftFront;
        private DcMotor m_rightFront;
        private DcMotor m_leftBack;
        private DcMotor m_rightBack;
        private DriveSubsystem m_driveSubsystem;
        private PinPointOdometrySubsystem m_pinPointOdometrySubsystem;
                
        public int ySpecScore = 20;
        public int specimen = 0;

        public GoBildaPinpointDriver m_odo; // Declare OpMode member for the Odometry Computer
//        public TeleOpDriveToPoint nav = new TeleOpDriveToPoint(); //OpMode member for the point-to-point navigation class
        DriveToPoint nav = new DriveToPoint(); //OpMode member for the point-to-point navigation class


    public TestPose2DTeleOp( DriveSubsystem driveSubsystem, GoBildaPinpointDriver odo, PinPointOdometrySubsystem pinPointOdometrySubsystem, DcMotor lF, DcMotor rF, DcMotor lB, DcMotor rB)
        {
            this.m_driveSubsystem = driveSubsystem;
            this.m_odo = odo;
            this.m_pinPointOdometrySubsystem = pinPointOdometrySubsystem;

            this.m_leftFront = lF;
            this.m_rightFront = rF;
            this.m_leftBack = lB;
            this.m_rightBack = rB;
            addRequirements( m_driveSubsystem, m_pinPointOdometrySubsystem);
        }

        @Override
    public void initialize()
    {
//        this.m_odo = hardwareMap.get(GoBildaPinpointDriver.class,"odo");
//        this.m_odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
//        this.m_odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED, GoBildaPinpointDriver.EncoderDirection.FORWARD);
//        this.m_odo.getPosition();
//        this.m_odo = hardwareMap.get(GoBildaPinpointDriver.class, "odo");
//        this.m_odo.setOffsets(68, -178);//these are tuned for Sting-Ray 3110-0002-0001 Product Insight #1
    }

    @Override
    public void execute()
        {
            this.m_pinPointOdometrySubsystem.updateOdo();
            System.out.println(m_odo.getPosition().toString());
            this.nav.driveTo(m_odo.getPosition(),
                    new Pose2DUnNormalized(DistanceUnit.MM, 100, 100, UnnormalizedAngleUnit.DEGREES, -180),
                    0.7, 0);

            m_leftFront.setPower(nav.getMotorPower(DriveToPoint.DriveMotor.LEFT_FRONT));
            m_rightFront.setPower(nav.getMotorPower(DriveToPoint.DriveMotor.RIGHT_FRONT));
            m_leftBack.setPower(nav.getMotorPower(DriveToPoint.DriveMotor.LEFT_BACK));
            m_rightBack.setPower(nav.getMotorPower(DriveToPoint.DriveMotor.RIGHT_BACK));
        }

//nav calculates the power to set to each motor in a mecanum or tank drive. Use nav.getMotorPower to find that value.

    @Override
    public void end(boolean interrupted)
    {
    }

    @Override
    public boolean isFinished()
    {
        return true;
    }
}

