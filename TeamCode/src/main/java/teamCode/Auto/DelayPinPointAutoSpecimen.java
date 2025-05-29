package teamCode.Auto;

import static teamCode.Constants.LiftArmConstants.kLiftArmCloseSample;
import static teamCode.Constants.LiftArmConstants.kLiftArmHighChamber;
import static teamCode.Constants.LiftArmConstants.kLiftArmIntakeReset;
import static teamCode.Constants.PivotIntakeConstants.kIntakePivotPickUp;
import static teamCode.Constants.PivotIntakeConstants.kIntakePivotScore;
import static teamCode.Constants.PivotIntakeConstants.kIntakePivotSpecimen;
import static teamCode.Constants.SlideArmConstants.kSlideArmCloseSample;
import static teamCode.Constants.SlideArmConstants.kSlideArmHighChamber;
import static teamCode.Constants.SlideArmConstants.kSlideArmHome;
import static teamCode.Constants.SlideArmConstants.kSlideAutoScore;

import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.UnnormalizedAngleUnit;
import org.firstinspires.ftc.teamcode.Libs.GoBilda.DriveToPoint;
import org.firstinspires.ftc.teamcode.Libs.GoBilda.GoBildaPinpointDriver;

import java.util.Locale;

import teamCode.commands.ArmIntakeResetCommand;
import teamCode.commands.ArmPositionCloseSampleCommand;
import teamCode.commands.ArmPositionHighBasketCommand;
import teamCode.commands.ArmPositionHighChamberCommand;
import teamCode.commands.ArmPositionTravelCommand;
import teamCode.commands.IntakePivotCommand;
import teamCode.commands.SlideFudgeInCommand;
import teamCode.commands.StingrayAscent1ArmCommand;
import teamCode.subsystems.IntakePivotSubsystem;
import teamCode.subsystems.IntakeWheelSubsystem;
import teamCode.subsystems.LiftArmSubsystem;
import teamCode.subsystems.SlideArmSubsystem;
import teamCode.subsystems.StingRayArmSubsystem;

@Autonomous(name="DelayPinPointAutoSpecimen", group="Pinpoint")
//@Disabled

public class DelayPinPointAutoSpecimen extends LinearOpMode
{
    //Initializing Motors & Servos
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;
    private DcMotor m_liftArmMotor;
    private DcMotor m_slideArmMotor;
    private CRServo m_intakeWheelServo;
    private IntakePivotSubsystem m_intakePivotSubsystem;
    private StingRayArmSubsystem m_ascentArmSubsystem;
    private LiftArmSubsystem m_liftArmSubsystem;
    private SlideArmSubsystem m_slideArmSubsystem;
    private IntakeWheelSubsystem m_intakeWheelSubsystem;
    private ArmPositionHighBasketCommand m_armPositionHighBasketCommand;
    private ArmPositionHighChamberCommand m_armPositionHighChamberCommand;
    private ArmPositionTravelCommand m_armPositionHomeCommand;
    private ArmPositionCloseSampleCommand m_armPositionCloseSampleCommand;
    private ArmIntakeResetCommand m_armIntakeResetCommand;
    private IntakePivotCommand m_intakePivotCommand;
    private StingrayAscent1ArmCommand m_ascentArmCommand;
    private SlideFudgeInCommand m_slideFudgeInCommand;
    private TouchSensor m_touch;
    private final ElapsedTime holdTimer = new ElapsedTime();

    public int ySpecScore = 20;
    public int yDriveToSample = -700;
    public int ySampleCollect = -900;

    public int samples = 1;
    public int specimen = 0;

    GoBildaPinpointDriver odo; // Declare OpMode member for the Odometry Computer
//    DriveToPoint nav = new DriveToPoint(this); //OpMode member for the point-to-point navigation class
    DriveToPoint nav = new DriveToPoint(); //OpMode member for the point-to-point navigation class

    // Positions and Measurements
    public Pose2DUnNormalized Submersible = new Pose2DUnNormalized(DistanceUnit.MM, 630, ySpecScore, UnnormalizedAngleUnit.DEGREES,  -180);
    public Pose2DUnNormalized ScoreSpecimen = new Pose2DUnNormalized(DistanceUnit.MM, 450, ySpecScore, UnnormalizedAngleUnit.DEGREES, -180);
    public Pose2DUnNormalized PreSampleDrive = new Pose2DUnNormalized(DistanceUnit.MM, 450, -700, UnnormalizedAngleUnit.DEGREES, -90);
    public Pose2DUnNormalized StrafeToSample = new Pose2DUnNormalized(DistanceUnit.MM, 1200, -700, UnnormalizedAngleUnit.DEGREES, -90);
    public Pose2DUnNormalized ForwardToSample = new Pose2DUnNormalized(DistanceUnit.MM, 1200, -900, UnnormalizedAngleUnit.DEGREES, -90);
    public Pose2DUnNormalized ObservationZone = new Pose2DUnNormalized(DistanceUnit.MM, 75, -900, UnnormalizedAngleUnit.DEGREES, -90);
    final Pose2DUnNormalized PickUpSpecimen = new Pose2DUnNormalized(DistanceUnit.MM, 70, -1150, UnnormalizedAngleUnit.DEGREES, -90);
    final Pose2DUnNormalized PreSubmersible = new Pose2DUnNormalized(DistanceUnit.MM, 300, 0, UnnormalizedAngleUnit.DEGREES, -180);
    final Pose2DUnNormalized CollectSpecimen = new Pose2DUnNormalized(DistanceUnit.MM, 70, -1220, UnnormalizedAngleUnit.DEGREES, -90);


    public enum StateMachine
    {
        WAITING_FOR_START,
        SCORE_SPECIMEN,
        DRIVE_TO_SAMPLE,
        PRESCORE_SPECIMEN,
        STRAFE_TO_SAMPLE,
        OBSERVATION_ZONE,
        PICK_UP_SPECIMEN,
        FORWARD_TO_SAMPLE,
        COUNTER_SCORE,
        DRIVE_TO_SUBMERSIBLE, PRE_SUBMERSIBLE, PARK_OBSERVATION_ZONE, PARKED, PRE_PICK_UP_SPECIMEN,
    }

    @Override
    public void runOpMode()
    {
        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.

        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

        odo = hardwareMap.get(GoBildaPinpointDriver.class, "odo");
        odo.setOffsets(68, -178);//these are tuned for Sting-Ray 3110-0002-0001 Product Insight #1
        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED, GoBildaPinpointDriver.EncoderDirection.FORWARD);

        odo.resetPosAndIMU();

        //nav.setXYCoefficients(0.02,0.002,0.0,DistanceUnit.MM,12);
        //nav.setYawCoefficients(1,0,0.0, AngleUnit.DEGREES,2);
        nav.setDriveType(DriveToPoint.DriveType.MECANUM);

        StateMachine stateMachine;
        stateMachine = StateMachine.WAITING_FOR_START;


        telemetry.addData("Status", "Initialized");
        telemetry.addData("X offset", odo.getXOffset());
        telemetry.addData("Y offset", odo.getYOffset());
        telemetry.addData("Device Version Number:", odo.getDeviceVersion());
        telemetry.addData("Device Scalar", odo.getYawScalar());
        telemetry.addData("y1 = ", (yDriveToSample));
        telemetry.addData("y2 = ", (ySampleCollect));
        telemetry.update();

        this.m_liftArmMotor = hardwareMap.get(DcMotor.class, "liftArmMotor");
        this.m_slideArmMotor = hardwareMap.get(DcMotor.class, "slideArmMotor");

        this.m_intakeWheelServo = new CRServo(hardwareMap, "intakeWheelServo");
        this.m_intakePivotSubsystem = new IntakePivotSubsystem(hardwareMap, "intakePivotServo");
        this.m_ascentArmSubsystem = new StingRayArmSubsystem(hardwareMap, "ascentArmServo");

        this.m_liftArmSubsystem = new LiftArmSubsystem(this.m_liftArmMotor);
        this.m_slideArmSubsystem = new SlideArmSubsystem(this.m_slideArmMotor);
        this.m_intakeWheelSubsystem = new IntakeWheelSubsystem(this.m_intakeWheelServo, this.m_touch);

        this.m_armPositionHomeCommand = new ArmPositionTravelCommand(this.m_liftArmSubsystem, this.m_slideArmSubsystem, this.m_intakePivotSubsystem);
        this.m_armPositionCloseSampleCommand = new ArmPositionCloseSampleCommand(this.m_liftArmSubsystem, this.m_slideArmSubsystem, this.m_intakePivotSubsystem);
        this.m_armPositionHighBasketCommand = new ArmPositionHighBasketCommand(this.m_liftArmSubsystem, this.m_slideArmSubsystem, this.m_intakePivotSubsystem);
        this.m_armPositionHighChamberCommand = new ArmPositionHighChamberCommand(this.m_liftArmSubsystem, this.m_slideArmSubsystem, this.m_intakePivotSubsystem);
        this.m_intakePivotCommand = new IntakePivotCommand(this.m_intakePivotSubsystem);
        this.m_ascentArmCommand = new StingrayAscent1ArmCommand(this.m_ascentArmSubsystem);
        //this.m_armFudgeFactorUpCommand = new ArmFudgeFactorUpCommand(this.m_liftArmSubsystem);

        this.m_slideArmSubsystem.slideArm(kSlideArmHome);
        waitForStart();
        resetRuntime();

        while (opModeIsActive())
        {
            odo.update();


            switch (stateMachine)
            {
                case WAITING_FOR_START:
                    this.m_liftArmSubsystem.liftArm(kLiftArmIntakeReset);
                    //the first step in the autonomous
                    stateMachine = StateMachine.DRIVE_TO_SUBMERSIBLE;
                    break;

                case DRIVE_TO_SUBMERSIBLE: /*Drive to Submersible, moving arm, slide, pivot intake, turn on intake*/
                    /*
                    drive the robot to the first target, the nav.driveTo function will return true once
                    the robot has reached the target, and has been there for (holdTime) seconds.
                    Once driveTo returns true, it prints a telemetry line and moves the state machine forward.
                     */
                    nav.driveTo(odo.getPosition(),
                            new Pose2DUnNormalized(DistanceUnit.MM, 630, ySpecScore, UnnormalizedAngleUnit.DEGREES, -180),
                            0.6, .1);

                    this.m_liftArmSubsystem.liftArm(kLiftArmHighChamber);
                    if (this.m_liftArmSubsystem.atTarget(kLiftArmHighChamber))
                    {
                        this.m_slideArmSubsystem.slideArm(kSlideArmHighChamber);
                        this.m_intakePivotSubsystem.pivotIntake(kIntakePivotSpecimen);
                    }
                    if (this.m_slideArmSubsystem.atTarget(kSlideArmHighChamber))
                    {
                        this.m_intakeWheelSubsystem.spinIntake(-0.75);//Score on High Chamber #1
                        telemetry.addLine("Ready to Score!");
                        stateMachine = StateMachine.PRESCORE_SPECIMEN;
                    }
                    break;

                case PRESCORE_SPECIMEN: /*Slide Arm Drops*/
                    this.m_slideArmSubsystem.slideArm(kSlideAutoScore);
                    if (nav.driveTo(odo.getPosition(), odo.getPosition(), 0.6, .75))
                    {
                        stateMachine = StateMachine.SCORE_SPECIMEN;
                    }
                    break;

                case SCORE_SPECIMEN: /*Drive Away*/
                    resetRuntime();
                    if (nav.driveTo(odo.getPosition(),
                            new Pose2DUnNormalized(DistanceUnit.MM, 450, ySpecScore, UnnormalizedAngleUnit.DEGREES, -180),
                            0.7, 0) || getRuntime() > 3)
                    {
                        this.m_intakePivotSubsystem.pivotIntake(kIntakePivotPickUp);
                        telemetry.addLine("SCORE!!!");
                        ySpecScore = ySpecScore + 70;
                        specimen = specimen + 1;
                        stateMachine = StateMachine.COUNTER_SCORE;
                    }
                    break;

                case COUNTER_SCORE:
                    if (specimen == 1)
                    {
                        stateMachine = StateMachine.DRIVE_TO_SAMPLE;
                    }
                    else if (specimen ==2 )
                    {
                            stateMachine = StateMachine.OBSERVATION_ZONE;
                    }
                    else if (specimen ==3)
                    {
                        this.m_intakePivotSubsystem.pivotIntake(kIntakePivotScore);
                        this.m_liftArmSubsystem.liftArm(kLiftArmCloseSample);
                        stateMachine = StateMachine.PARK_OBSERVATION_ZONE;
                    }
                    break;

                case DRIVE_TO_SAMPLE:  //drive to the Sample
                    this.m_slideArmSubsystem.slideArm(kSlideArmCloseSample);
                    if (this.m_slideArmSubsystem.atTarget(kSlideArmCloseSample)) ;
                    {
                        this.m_liftArmSubsystem.liftSlow(kLiftArmIntakeReset);
                        this.m_intakePivotSubsystem.pivotIntake(kIntakePivotScore);
                        this.m_intakeWheelSubsystem.spinIntake(1.0);//Score on High Chamber #1

                    }
                    if (nav.driveTo(odo.getPosition(), PreSampleDrive,0.7, 0))
                    {
                        this.m_intakeWheelSubsystem.spinIntake(0.0);//Turn off intake
                        stateMachine = StateMachine.STRAFE_TO_SAMPLE;
                    }
                    break;

                case STRAFE_TO_SAMPLE:
                    if (nav.driveTo(odo.getPosition(),StrafeToSample,0.7, 0))
                    {
                        stateMachine = StateMachine.FORWARD_TO_SAMPLE;
                    }
                    break;

                case FORWARD_TO_SAMPLE:
                    if (nav.driveTo(odo.getPosition(), ForwardToSample,0.7, 0))
                    {
                        stateMachine = StateMachine.OBSERVATION_ZONE;
                    }
                    break;

                case OBSERVATION_ZONE:
                    this.m_intakeWheelSubsystem.spinIntake(-0.5);//Start intake
                    this.m_liftArmSubsystem.liftArm(kLiftArmCloseSample);
                    this.m_slideArmSubsystem.slideArm(kSlideArmCloseSample);
                    if (nav.driveTo(odo.getPosition(),ObservationZone, 0.4, 0))
                    {
                        this.m_intakePivotSubsystem.pivotIntake(kIntakePivotPickUp);
                        samples = samples + 1;
//                        ySampleCollect = ySampleCollect - 275;
//                        yDriveToSample = yDriveToSample - 254;
                        stateMachine = StateMachine.PRE_PICK_UP_SPECIMEN;
                    }
                    break;

//                case COUNTER:
//                    if (samples < 2)
//                    {
//                        stateMachine = StateMachine.DRIVE_TO_SAMPLE;
//                    }
//                    else
//                    {
//                        stateMachine = StateMachine.COLLECT_SPECIMEN;
//                    }
//                    break;

                case PRE_PICK_UP_SPECIMEN:
                    this.m_intakePivotSubsystem.pivotIntake(kIntakePivotPickUp);
                    if (nav.driveTo(odo.getPosition(), CollectSpecimen, 0.3, 0.5))
                    {
                        stateMachine = StateMachine.PICK_UP_SPECIMEN;
                    }
                    break;

                case PICK_UP_SPECIMEN:
//                    this.m_slideArmSubsystem.slideArm(75);
                    if (nav.driveTo(odo.getPosition(), odo.getPosition(), 0.5, 0.5))
                    {
                        this.m_intakeWheelSubsystem.spinIntake(0.0);//Stop intake
                        this.m_intakePivotSubsystem.pivotIntake(kIntakePivotScore);
                        this.m_liftArmSubsystem.liftArm(kLiftArmIntakeReset);
                        stateMachine = StateMachine.PRE_SUBMERSIBLE;
                    }
                    break;

                case PRE_SUBMERSIBLE:
                    if (nav.driveTo(odo.getPosition(), PreSubmersible, 0.5, 0))
                    {
                        this.m_intakePivotSubsystem.pivotIntake(kIntakePivotPickUp);
                        this.m_liftArmSubsystem.liftArm(kLiftArmHighChamber);
                        stateMachine = StateMachine.DRIVE_TO_SUBMERSIBLE;
                    }
                    break;

                case PARK_OBSERVATION_ZONE:
                    if (nav.driveTo(odo.getPosition(),ObservationZone, 0.4, 0))
                    {
                        this.m_intakeWheelSubsystem.spinIntake(0.0);//Turn off intake
                        stateMachine = StateMachine.PARKED;
                    }
                    break;
            }
        //nav calculates the power to set to each motor in a mecanum or tank drive. Use nav.getMotorPower to find that value.
        leftFront.setPower(nav.getMotorPower(DriveToPoint.DriveMotor.LEFT_FRONT));
        rightFront.setPower(nav.getMotorPower(DriveToPoint.DriveMotor.RIGHT_FRONT));
        leftBack.setPower(nav.getMotorPower(DriveToPoint.DriveMotor.LEFT_BACK));
        rightBack.setPower(nav.getMotorPower(DriveToPoint.DriveMotor.RIGHT_BACK));

        telemetry.addData("current state:", stateMachine);
        telemetry.addData("Spec Scored = ", (specimen));
//        telemetry.addData("y2 = ", (ySampleCollect));
        Pose2DUnNormalized pos = odo.getPosition();

        String data = String.format(Locale.US, "{X: %.3f, Y: %.3f, H: %.3f}", pos.getX(DistanceUnit.MM), pos.getY(DistanceUnit.MM), pos.getHeading(UnnormalizedAngleUnit.DEGREES));

            telemetry.addData("Position", data);

        telemetry.update();
        }
    }
}   // end class

