package org.firstinspires.ftc.teamcode.Auto;

import static com.qualcomm.robotcore.util.ElapsedTime.Resolution.SECONDS;
import static teamCode.Constants.LiftArmConstants.kLiftArmCloseSample;
import static teamCode.Constants.LiftArmConstants.kLiftArmHighBasket;
import static teamCode.Constants.LiftArmConstants.kLiftArmIntakeReset;
import static teamCode.Constants.PivotIntakeConstants.kIntakePivotPickUp;
import static teamCode.Constants.PivotIntakeConstants.kIntakePivotScore;
import static teamCode.Constants.SlideArmConstants.kSlideArmCloseSample;
import static teamCode.Constants.SlideArmConstants.kSlideArmHighBasket;

import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Libs.GoBilda.DriveToPoint;
import org.firstinspires.ftc.teamcode.Libs.GoBilda.GoBildaPinpointDriver;

import java.util.Locale;

import teamCode.autoSubsystems.AutoDriveSubsystem;
import teamCode.commands.ArmIntakeResetCommand;
import teamCode.commands.ArmPositionCloseSampleCommand;
import teamCode.commands.ArmPositionHighBasketCommand;
import teamCode.commands.ArmPositionHighChamberCommand;
import teamCode.commands.ArmPositionTravelCommand;
import teamCode.commands.IntakePivotCommand;
import teamCode.commands.ScoreSpecimenCommand;
import teamCode.commands.SlideFudgeInCommand;
import teamCode.commands.StingrayArmCommand;
import teamCode.subsystems.IntakePivotSubsystem;
import teamCode.subsystems.IntakeWheelSubsystem;
import teamCode.subsystems.LiftArmSubsystem;
import teamCode.subsystems.SlideArmSubsystem;
import teamCode.subsystems.StingRayArmSubsystem;

@Autonomous(name="Pinpoint Navigation Example", group="Pinpoint")
//@Disabled

public class SensorPinpointDriveToPoint extends LinearOpMode
{

    private DcMotor leftFront;
    private DcMotor rightFront_perp;
    private DcMotor leftBack_par;
    private DcMotor rightBack;
    private DcMotor m_liftArmMotor;
    private DcMotor m_slideArmMotor;
    private CRServo m_intakeWheelServo;
    private IntakePivotSubsystem m_intakePivotSubsystem;
    private StingRayArmSubsystem m_ascentArmSubsystem;
    private AutoDriveSubsystem m_autoDriveSubsystem;
    private LiftArmSubsystem m_liftArmSubsystem;
    private SlideArmSubsystem m_slideArmSubsystem;
    private IntakeWheelSubsystem m_intakeWheelSubsystem;
    private ArmPositionHighBasketCommand m_armPositionHighBasketCommand;
    private ArmPositionHighChamberCommand m_armPositionHighChamberCommand;
    private ArmPositionTravelCommand m_armPositionHomeCommand;
    private ArmPositionCloseSampleCommand m_armPositionCloseSampleCommand;
    private ArmIntakeResetCommand m_armIntakeResetCommand;
    private IntakePivotCommand m_intakePivotCommand;
    private StingrayArmCommand m_ascentArmCommand;
    private SlideFudgeInCommand m_slideFudgeInCommand;
    private ScoreSpecimenCommand m_scoreSpecimenCommand;
    private TouchSensor m_touch;

    GoBildaPinpointDriver odo; // Declare OpMode member for the Odometry Computer
    DriveToPoint nav = new DriveToPoint(this); //OpMode member for the point-to-point navigation class

    enum StateMachine
    {
        WAITING_FOR_START,
        AT_TARGET,
        DRIVE_TO_TARGET_1,
        DRIVE_TO_TARGET_2,
        DRIVE_TO_TARGET_3,
        DRIVE_TO_TARGET_4,
        DRIVE_TO_TARGET_5
    }
/*
    static final Pose2D TARGET_1 = new Pose2D(DistanceUnit.MM,2000,20,AngleUnit.DEGREES,0);
    static final Pose2D PrePickUpSample2 = new Pose2D(DistanceUnit.MM, 2600, -20, AngleUnit.DEGREES, -90);
    static final Pose2D PickUpSample2 = new Pose2D(DistanceUnit.MM,2600,-2600, AngleUnit.DEGREES,-90);
    static final Pose2D PrePickUpSample3 = new Pose2D(DistanceUnit.MM, 100, -2600, AngleUnit.DEGREES, 90);
    static final Pose2D PickUpSample3 = new Pose2D(DistanceUnit.MM, 100, 0, AngleUnit.DEGREES, 0);
*/
    static final Pose2D TARGET_1 = new Pose2D(DistanceUnit.MM,0,0,AngleUnit.DEGREES,0);
    static final Pose2D TARGET_2 = new Pose2D(DistanceUnit.MM, 200, 530, AngleUnit.DEGREES, -45);
    static final Pose2D TARGET_3 = new Pose2D(DistanceUnit.MM,200,530, AngleUnit.DEGREES,-45);
    static final Pose2D TARGET_4 = new Pose2D(DistanceUnit.MM, 200, 530, AngleUnit.DEGREES, -45);
    static final Pose2D TARGET_5 = new Pose2D(DistanceUnit.MM, 200, 530, AngleUnit.DEGREES, -45);


    @Override
    public void runOpMode()
    {

        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.

        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront_perp = hardwareMap.get(DcMotor.class, "rightFront_perp");
        leftBack_par = hardwareMap.get(DcMotor.class, "leftBack_par");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront_perp.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack_par.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack_par.setDirection(DcMotorSimple.Direction.REVERSE);

        odo = hardwareMap.get(GoBildaPinpointDriver.class,"odo");
        odo.setOffsets(68, -178);//these are tuned for 3110-0002-0001 Product Insight #1
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
        telemetry.update();

        this.m_liftArmMotor = hardwareMap.get(DcMotor.class, "liftArmMotor");
        this.m_slideArmMotor = hardwareMap.get(DcMotor.class, "slideArmMotor");

        this.m_intakeWheelServo = new CRServo(hardwareMap, "intakeWheelServo");
        this.m_intakePivotSubsystem = new IntakePivotSubsystem(hardwareMap, "intakePivotServo");
        this.m_ascentArmSubsystem = new StingRayArmSubsystem(hardwareMap, "ascentArmServo");

        this.m_liftArmSubsystem = new LiftArmSubsystem(this.m_liftArmMotor);
        this.m_slideArmSubsystem = new SlideArmSubsystem(this.m_slideArmMotor);
        this.m_intakeWheelSubsystem = new IntakeWheelSubsystem(this.m_intakeWheelServo,this.m_touch);

        this.m_armPositionHomeCommand = new ArmPositionTravelCommand(this.m_liftArmSubsystem, this.m_slideArmSubsystem, this.m_intakePivotSubsystem);
        this.m_armPositionCloseSampleCommand = new ArmPositionCloseSampleCommand(this.m_liftArmSubsystem, this.m_slideArmSubsystem, this.m_intakePivotSubsystem);
        this.m_armPositionHighBasketCommand = new ArmPositionHighBasketCommand(this.m_liftArmSubsystem, this.m_slideArmSubsystem, this.m_intakePivotSubsystem);
        this.m_armPositionHighChamberCommand = new ArmPositionHighChamberCommand(this.m_liftArmSubsystem,this.m_slideArmSubsystem, this.m_intakePivotSubsystem);
        this.m_intakePivotCommand = new IntakePivotCommand(this.m_intakePivotSubsystem);
        this.m_ascentArmCommand = new StingrayArmCommand(this.m_ascentArmSubsystem);
        //this.m_armFudgeFactorUpCommand = new ArmFudgeFactorUpCommand(this.m_liftArmSubsystem);
        this.m_scoreSpecimenCommand = new ScoreSpecimenCommand(this.m_liftArmSubsystem);


        // Wait for the game to start (driver presses START)
        waitForStart();

//        odo.recalibrateIMU();
//        telemetry.addLine("Gyro Reset");

        resetRuntime();

        while (opModeIsActive())
        {
            odo.update();

            switch (stateMachine)
            {
                case WAITING_FOR_START:
                    this.m_liftArmSubsystem.liftArm(kLiftArmIntakeReset);
                    //the first step in the autonomous
                    stateMachine = StateMachine.DRIVE_TO_TARGET_1;
                    break;
                case DRIVE_TO_TARGET_1:
                    /*
                    drive the robot to the first target, the nav.driveTo function will return true once
                    the robot has reached the target, and has been there for (holdTime) seconds.
                    Once driveTo returns true, it prints a telemetry line and moves the state machine forward.
                     */
                    if (nav.driveTo(odo.getPosition(), TARGET_1, 0.5, 0))
                    {
                        telemetry.addLine("at position #1!");
                        stateMachine = StateMachine.DRIVE_TO_TARGET_2;
                    }
                    break;

                case DRIVE_TO_TARGET_2:
                    //drive to the second target
                    if (nav.driveTo(odo.getPosition(), TARGET_2, 0.5, 5))
                    {
                        telemetry.addLine("at position #2!");
                        stateMachine = StateMachine.DRIVE_TO_TARGET_3;
                    }
                    this.m_liftArmSubsystem.liftArm(kLiftArmHighBasket);
                    if (this.m_liftArmSubsystem.atTarget(kLiftArmHighBasket))
                    {
                        this.m_slideArmSubsystem.slideArm(kSlideArmHighBasket);
                        this.m_intakePivotSubsystem.pivotIntake(kIntakePivotScore);
                    }
                    if(this.m_slideArmSubsystem.atTarget(kSlideArmHighBasket))
                    {
                        this.m_intakeWheelSubsystem.spinIntake(0.5);//Score in High Basket #1
                    }
//                    if (this.getRuntime() > 1.0)
//                    {
//                        this.m_intakeWheelSubsystem.spinIntake(0.5);//Score in High Basket #1
//                    }
//                    else
//                    {this.m_intakeWheelSubsystem.spinIntake(0.5);//Score in High Basket #1
//                        //code to run while waiting
//                    }
                    this.m_intakePivotSubsystem.pivotIntake(kIntakePivotPickUp);
                    if (this.m_slideArmSubsystem.atTarget(kSlideArmCloseSample))
                    {
                        this.m_intakePivotSubsystem.pivotIntake(kIntakePivotScore);
                        this.m_liftArmSubsystem.liftArm(kLiftArmCloseSample);
                    }
                    this.m_intakePivotSubsystem.pivotIntake(kIntakePivotScore);
            }

                    break;
//
//                case SCORE_SAMPLE_2:
//                    if(nav.driveTo(odo.getPosition(), PickUpSample2, 0.5, 0))
//                    {
//                        telemetry.addLine("at position #3");
//                        stateMachine = StateMachine.DRIVE_TO_SAMPLE_3;
//                    }
//                    this.m_intakeWheelSubsystem.spinIntake(-0.5);//Pick Up Floor #1
//                    this.m_intakePivotSubsystem.pivotIntake(kIntakePivotPickUp);
//                    break;
//                case DRIVE_TO_SAMPLE_3:
//                    if(nav.driveTo(odo.getPosition(),PrePickUpSample3,0.2,0))
//                    {
//                        telemetry.addLine("at position #4");
//                        stateMachine = StateMachine.PICKUP_SAMPLE_3;
//                    }
//                    this.m_liftArmSubsystem.liftArm(kLiftArmIntakeReset);
//                    this.m_intakeWheelSubsystem.spinIntake(0.0);
//                    break;
//
//                case PICKUP_SAMPLE_3:
//                    if(nav.driveTo(odo.getPosition(),PickUpSample3,0.5,1))
//                    {
//                        telemetry.addLine("There!");
//                        stateMachine = StateMachine.PARKED;
//                    }
//                    break;
        }


            //nav calculates the power to set to each motor in a mecanum or tank drive. Use nav.getMotorPower to find that value.
            leftFront.setPower(nav.getMotorPower(DriveToPoint.DriveMotor.LEFT_FRONT));
            rightFront_perp.setPower(nav.getMotorPower(DriveToPoint.DriveMotor.RIGHT_FRONT));
            leftBack_par.setPower(nav.getMotorPower(DriveToPoint.DriveMotor.LEFT_BACK));
            rightBack.setPower(nav.getMotorPower(DriveToPoint.DriveMotor.RIGHT_BACK));

            telemetry.addData("current state:",stateMachine);

            Pose2D pos = odo.getPosition();
            String data = String.format(Locale.US, "{X: %.3f, Y: %.3f, H: %.3f}", pos.getX(DistanceUnit.MM), pos.getY(DistanceUnit.MM), pos.getHeading(AngleUnit.DEGREES));
            telemetry.addData("Position", data);

            telemetry.update();



    }
}   // end class

