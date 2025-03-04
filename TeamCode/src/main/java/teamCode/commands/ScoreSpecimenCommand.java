package teamCode.commands;

import static teamCode.Constants.LiftArmConstants.kLiftArmCloseSample;
import static teamCode.Constants.LiftArmConstants.kLiftArmHighChamber;
import static teamCode.Constants.LiftArmConstants.kLiftArmIntakeReset;
import static teamCode.Constants.PivotIntakeConstants.kIntakePivotPickUp;
import static teamCode.Constants.PivotIntakeConstants.kIntakePivotScore;
import static teamCode.Constants.PivotIntakeConstants.kIntakePivotSpecimen;
import static teamCode.Constants.SlideArmConstants.kSlideArmCloseSample;
import static teamCode.Constants.SlideArmConstants.kSlideArmHighChamber;
import static teamCode.Constants.SlideArmConstants.kSlideAutoScore;
import static teamCode.commands.ScoreSpecimenCommand.StateMachine.DRIVE_TO_SUBMERSIBLE;
import static teamCode.commands.ScoreSpecimenCommand.StateMachine.OBSERVATION_ZONE;
import static teamCode.commands.ScoreSpecimenCommand.StateMachine.PICK_UP_SPECIMEN;
import static teamCode.commands.ScoreSpecimenCommand.StateMachine.PRE_PICK_UP_SPECIMEN;
import static teamCode.commands.ScoreSpecimenCommand.StateMachine.PRE_SUBMERSIBLE;
import static teamCode.commands.ScoreSpecimenCommand.StateMachine.SCORE_SPECIMEN;
import static teamCode.commands.ScoreSpecimenCommand.StateMachine.PRESCORE_SPECIMEN;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.UnnormalizedAngleUnit;
import org.firstinspires.ftc.teamcode.Libs.GoBilda.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.Libs.GoBilda.TeleOpDriveToPoint;

import teamCode.Auto.Pose2DUnNormalized;

import teamCode.subsystems.DriveSubsystem;
import teamCode.subsystems.IntakePivotSubsystem;
import teamCode.subsystems.IntakeWheelSubsystem;
import teamCode.subsystems.LiftArmSubsystem;
import teamCode.subsystems.PinPointOdometrySubsystem;
import teamCode.subsystems.SlideArmSubsystem;
import teamCode.subsystems.StingRayArmSubsystem;

//@Disabled
public class ScoreSpecimenCommand extends CommandBase
{
    //Initializing Motors & Servos
    private DcMotor m_leftFront;
    private DcMotor m_rightFront;
    private DcMotor m_leftBack;
    private DcMotor m_rightBack;
    private DcMotor m_liftArmMotor;
    private DcMotor m_slideArmMotor;
    private CRServo m_intakeWheelServo;
    private TouchSensor m_touch;
    private IntakePivotSubsystem m_intakePivotSubsystem;
    private StingRayArmSubsystem m_ascentArmSubsystem;
    private LiftArmSubsystem m_liftArmSubsystem;
    private SlideArmSubsystem m_slideArmSubsystem;
    private IntakeWheelSubsystem m_intakeWheelSubsystem;
    private DriveSubsystem m_driveSubsystem;
    private PinPointOdometrySubsystem m_pinPointOdometrySubsystem;


    private ArmPositionTravelCommand m_armPositionHomeCommand;
    private ArmPositionCloseSampleCommand m_armPositionCloseSampleCommand;
    private ArmPositionHighChamberCommand m_armPositionHighChamberCommand;
    private IntakePivotCommand m_intakePivotCommand;


    public int ySpecScore = 20;
    public int specimen = 0;

    public GoBildaPinpointDriver m_odo; // Declare OpMode member for the Odometry Computer
    public TeleOpDriveToPoint nav = new TeleOpDriveToPoint(); //OpMode member for the point-to-point navigation class

    // Positions and Measurements
    public Pose2DUnNormalized Submersible = new Pose2DUnNormalized(DistanceUnit.MM, 630, ySpecScore, UnnormalizedAngleUnit.DEGREES,  -180);
    public Pose2DUnNormalized ScoreSpecimen = new Pose2DUnNormalized(DistanceUnit.MM, 450, ySpecScore, UnnormalizedAngleUnit.DEGREES, -180);
    public Pose2DUnNormalized ObservationZone = new Pose2DUnNormalized(DistanceUnit.MM, 70, -900, UnnormalizedAngleUnit.DEGREES, -90);
    public Pose2DUnNormalized PreSubmersible = new Pose2DUnNormalized(DistanceUnit.MM, 300, 0, UnnormalizedAngleUnit.DEGREES, -180);
    public Pose2DUnNormalized CollectSpecimen = new Pose2DUnNormalized(DistanceUnit.MM, 70, -1220, UnnormalizedAngleUnit.DEGREES, -90);

    public StateMachine stateMachine;

    enum StateMachine
    {
        OBSERVATION_ZONE,
        PRE_PICK_UP_SPECIMEN,
        PICK_UP_SPECIMEN,
        PRE_SUBMERSIBLE,
        DRIVE_TO_SUBMERSIBLE,
        PRESCORE_SPECIMEN,
        SCORE_SPECIMEN,
    }
    public ScoreSpecimenCommand(LiftArmSubsystem liftArmSubsystem, SlideArmSubsystem slideArmSubsystem, IntakePivotSubsystem intakePivotSubsystem, IntakeWheelSubsystem intakeWheelSubsystem, DriveSubsystem driveSubsystem, PinPointOdometrySubsystem pinPointOdometrySubsystem, DcMotor lF, DcMotor rF, DcMotor lB, DcMotor rB)
    {

        this.m_driveSubsystem = driveSubsystem;
        this.m_liftArmSubsystem = liftArmSubsystem;
        this.m_slideArmSubsystem = slideArmSubsystem;
        this.m_intakePivotSubsystem = intakePivotSubsystem;
        this.m_intakeWheelSubsystem = intakeWheelSubsystem;
        this.m_pinPointOdometrySubsystem = pinPointOdometrySubsystem;

        this.m_leftFront = lF;
        this.m_rightFront = rF;
        this.m_leftBack = lB;
        this.m_rightBack = rB;
        addRequirements(m_liftArmSubsystem, m_slideArmSubsystem, m_intakePivotSubsystem, m_intakeWheelSubsystem, m_driveSubsystem, m_pinPointOdometrySubsystem);


    }

    @Override
    public void initialize()
    {

    }

    @Override
    public void execute()
    {
        this.m_pinPointOdometrySubsystem.updateOdo();
        stateMachine = OBSERVATION_ZONE;
        System.out.println("1");
        switch (stateMachine)
        {
            case OBSERVATION_ZONE:
                System.out.println("2");
                this.m_intakeWheelSubsystem.spinIntake(-0.5);//Start intake
                this.m_liftArmSubsystem.liftArm(kLiftArmCloseSample);
                this.m_slideArmSubsystem.slideArm(kSlideArmCloseSample);
                System.out.println("3");

            if (nav.driveTo(m_pinPointOdometrySubsystem.getPosition(),ObservationZone, 0.4, 0))
                {
                    System.out.println("4");
                    this.m_intakePivotSubsystem.pivotIntake(kIntakePivotPickUp);
                    stateMachine = PRE_PICK_UP_SPECIMEN;
                }
            System.out.println("5");
            break;

            case PRE_PICK_UP_SPECIMEN:
                System.out.println("6");
                if (nav.driveTo(m_pinPointOdometrySubsystem.getPosition(), CollectSpecimen, 0.3, 0.5))
                {
                    System.out.println("7");
                    stateMachine = PICK_UP_SPECIMEN;
                }
                break;

            case PICK_UP_SPECIMEN:
//                    this.m_slideArmSubsystem.slideArm(75);
                System.out.println("8");
                if (nav.driveTo(m_pinPointOdometrySubsystem.getPosition(), m_pinPointOdometrySubsystem.getPosition(), 0.5, 0.5))
                {
                    this.m_intakeWheelSubsystem.spinIntake(0.0);//Stop intake
                    this.m_intakePivotSubsystem.pivotIntake(kIntakePivotScore);
                    this.m_liftArmSubsystem.liftArm(kLiftArmIntakeReset);
                    stateMachine = PRE_SUBMERSIBLE;
                }
                break;

            case PRE_SUBMERSIBLE:
                System.out.println("9");
                if (nav.driveTo(m_pinPointOdometrySubsystem.getPosition(), PreSubmersible, 0.5, 0))
                {
                    this.m_intakePivotSubsystem.pivotIntake(kIntakePivotPickUp);
                    this.m_liftArmSubsystem.liftArm(kLiftArmHighChamber);
                    stateMachine = DRIVE_TO_SUBMERSIBLE;
                }
                break;

            case DRIVE_TO_SUBMERSIBLE: /*Drive to Submersible, moving arm, slide, pivot intake, turn on intake*/
                System.out.println("10");
                nav.driveTo(m_pinPointOdometrySubsystem.getPosition(),
                        new Pose2DUnNormalized(DistanceUnit.MM, 630, ySpecScore, UnnormalizedAngleUnit.DEGREES, -180),
                        0.6, .1);
                if (this.m_liftArmSubsystem.atTarget(kLiftArmHighChamber))
                {
                    System.out.println("11");
                    this.m_slideArmSubsystem.slideArm(kSlideArmHighChamber);
                    this.m_intakePivotSubsystem.pivotIntake(kIntakePivotSpecimen);
                }
                if (this.m_slideArmSubsystem.atTarget(kSlideArmHighChamber))
                {
                    this.m_intakeWheelSubsystem.spinIntake(-0.75);//Score on High Chamber #1
                    stateMachine = PRESCORE_SPECIMEN;
                }
                break;

            case PRESCORE_SPECIMEN: /*Slide Arm Drops*/
                this.m_slideArmSubsystem.slideArm(kSlideAutoScore);
                if (nav.driveTo(m_pinPointOdometrySubsystem.getPosition(), m_pinPointOdometrySubsystem.getPosition(), 0.6, .75))
                {
                    stateMachine = SCORE_SPECIMEN;
                }
                break;

            case SCORE_SPECIMEN: /*Drive Away*/
                if (nav.driveTo(m_pinPointOdometrySubsystem.getPosition(),
                        new Pose2DUnNormalized(DistanceUnit.MM, 450, ySpecScore, UnnormalizedAngleUnit.DEGREES, -180),
                        0.7, 0))
                {
                    this.m_intakePivotSubsystem.pivotIntake(kIntakePivotPickUp);
                    ySpecScore = ySpecScore + 70;
                    specimen = specimen + 1;
                    stateMachine = OBSERVATION_ZONE;
                }
                break;

        }
        m_leftFront.setPower(nav.getMotorPower(TeleOpDriveToPoint.DriveMotor.LEFT_FRONT));
        m_rightFront.setPower(nav.getMotorPower(TeleOpDriveToPoint.DriveMotor.RIGHT_FRONT));
        m_leftBack.setPower(nav.getMotorPower(TeleOpDriveToPoint.DriveMotor.LEFT_BACK));
        m_rightBack.setPower(nav.getMotorPower(TeleOpDriveToPoint.DriveMotor.RIGHT_BACK));
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

