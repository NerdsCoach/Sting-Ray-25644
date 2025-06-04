package teamCode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.UnnormalizedAngleUnit;

import teamCode.commands.ArmFudgeFactorDownCommand;
import teamCode.commands.ArmFudgeFactorUpCommand;
import teamCode.commands.ArmPositionCloseSampleCommand;
import teamCode.commands.ArmPositionFarSampleCommand;
import teamCode.commands.ArmPositionHighBasketCommand;
import teamCode.commands.ArmPositionHighChamberCommand;
import teamCode.commands.ArmPositionLowBasketCommand;
import teamCode.commands.ArmPositionMidSampleCommand;
import teamCode.commands.ArmPositionScoreHighChamberCommand;
import teamCode.commands.ArmPositionTravelCommand;
import teamCode.commands.ClimbArmReleaseCommand;
import teamCode.commands.DriveFieldOrientedCommand;
import teamCode.commands.GrabBatCommand;
import teamCode.commands.IntakePivotCommand;
import teamCode.commands.IntakeWheelCommand;
import teamCode.commands.ResetGyroCommand;
import teamCode.commands.ResetHomeCommand;

import teamCode.commands.SlideFudgeInCommand;
import teamCode.commands.SlideFudgeOutCommand;
import teamCode.commands.StingrayAscent1ArmCommand;
import teamCode.commands.TestPose2DTeleOpCommand;
import teamCode.commands.TimerCommand;
import teamCode.subsystems.DriveSubsystem;
import teamCode.subsystems.GrabBatSubsystem;
import teamCode.subsystems.GyroSubsystem;
import teamCode.subsystems.IntakePivotSubsystem;
import teamCode.subsystems.IntakeWheelSubsystem;
import teamCode.subsystems.LiftArmSubsystem;
//import teamCode.subsystems.PinPointOdometrySubsystem;
import teamCode.subsystems.SlideArmSubsystem;
import teamCode.subsystems.StingRayArmSubsystem;
import teamCode.subsystems.TimerSubsystem;

@Disabled
@TeleOp(name = "Test")
public class AutomatedRobotContainer extends CommandOpMode {
    //    public boolean m_inEndgame;
//    private double m_endGameTime;
//    private double m_oneMinute;
    private ElapsedTime timer;

    /* Drivetrain */
    private MecanumDrive m_drive;


    /* IMU */
    private IMU m_imu;
    private IMU.Parameters m_imuParameters;


    /* Gamepad */
    private GamepadEx m_driver1;
    private GamepadEx m_driver2;

//    private Gamepad gamepad1;


    private Button m_leftBumper;
    private Button m_rightBumper;
    private Button m_xButton;
    private Button m_circle;
    private Button m_square;
    private Button m_triangle;
    private Button m_dpadTop;
    private Button m_dpadBottom;
    private Button m_dpadLeft;
    private Button m_dpadRight;
    private Button m_gyroResetButton;
    private Button m_resetHomeButton;
    private Button m_slideFudgeInButton;
    private Button m_slideFudgeOutButton;
    private Button m_odoResetButton;
    private Button m_liftArmClimbButton;
    private Button m_releaseClimbButton;
    private Button m_climbArmFudgeUp;
    private Button m_climbArmFudgeDown;
    private Button m_autoScoreButton;
    private Button m_grabBat;

    /* Motors */
    private DcMotor m_slideArmMotor;
    private DcMotor m_liftArmMotor;
    private CRServo m_intakeWheelServo;

    public DcMotor leftFront;
    public DcMotor rightFront;
    public DcMotor leftBack;
    public DcMotor rightBack;

    /* Sensors */
    private TouchSensor m_touch;

    /* Subsystems */
    private DriveSubsystem m_driveSubsystem;
    private SlideArmSubsystem m_slideArmSubsystem;
    private LiftArmSubsystem m_liftArmSubsystem;
    private IntakePivotSubsystem m_intakePivotSubsystem;
    private IntakeWheelSubsystem m_intakeWheelSubsystem;
    private StingRayArmSubsystem m_ascentArmSubsystem;
    private GyroSubsystem m_gyroSubsystem;
//    private PinPointOdometrySubsystem m_pinPointOdometrySubsystem;
    private TimerSubsystem m_timerSubsystem;
    private GrabBatSubsystem m_grabBatSubsystem;

    /* Commands */
    private DriveFieldOrientedCommand m_driveFieldOrientedCommand;
    private ArmFudgeFactorUpCommand m_armFudgeFactorUpCommand;
    private ArmFudgeFactorDownCommand m_armFudgeFactorDownCommand;
    private ArmPositionCloseSampleCommand m_armPositionCloseSampleCommand;
    private ArmPositionFarSampleCommand m_armPositionFarSampleCommand;
    private ArmPositionMidSampleCommand m_armPositionSubmersiblePickUpCommand;
    private ArmPositionHighBasketCommand m_armPositionHighBasketCommand;
    private ArmPositionHighChamberCommand m_armPositionHighChamberCommand;
    private ArmPositionLowBasketCommand m_armPositionLowBasketCommand;
    private ArmPositionScoreHighChamberCommand m_armPositionScoreHighChamberCommand;
    private ArmPositionTravelCommand m_armPositionTravelCommand;
    private IntakePivotCommand m_intakePivotCommand;
    private IntakeWheelCommand m_intakeWheelCommand;
    private StingrayAscent1ArmCommand m_ascentArmCommand;
    private ResetGyroCommand m_resetGyroCommand;
    private ResetHomeCommand m_resetHomeCommand;
    private SlideFudgeInCommand m_slideFudgeInCommand;
    private SlideFudgeOutCommand m_slideFudgeOutCommand;
    private ClimbArmReleaseCommand m_releaseCLimberArmCommand;
    private teamCode.GoBildaPinpointDriver m_odo;
    private TimerCommand m_timerCommand;
    private TestPose2DTeleOpCommand m_TestPose2DTeleOpCommand;
    private DriveToPoint m_DriveToPoint;
    private GrabBatCommand m_grabBatCommand;
    private DriveToPoint nav = new DriveToPoint(); //OpMode member for the point-to-point navigation class

    private TouchSensor m_touchSensor;
    private boolean touchSensorIsPressed = false;

    double Runtime;

    /* PID */
    private PIDController m_pIDController;

    @Override
    public void initialize()
    {
        this.m_odo = hardwareMap.get(teamCode.GoBildaPinpointDriver.class, "odo");
        this.m_odo.resetPosAndIMU();
        this.m_odo.setOffsets(68, -178);//these are tuned for Sting-Ray 3110-0002-0001 Product Insight #1
        this.m_odo.setEncoderResolution(teamCode.GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        this.m_odo.setEncoderDirections(teamCode.GoBildaPinpointDriver.EncoderDirection.REVERSED, teamCode.GoBildaPinpointDriver.EncoderDirection.FORWARD);




        /* Drivetrain */
        this.leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        this.rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        this.leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        this.rightBack = hardwareMap.get(DcMotor.class, "rightBack");

        this.m_drive = new MecanumDrive
                (
                        new Motor(hardwareMap, "leftFront", Motor.GoBILDA.RPM_312),
                        new Motor(hardwareMap, "rightFront", Motor.GoBILDA.RPM_312),
                        new Motor(hardwareMap, "leftBack", Motor.GoBILDA.RPM_312),
                        new Motor(hardwareMap, "rightBack", Motor.GoBILDA.RPM_312)
                );



        /* IMU */
        this.m_imu = hardwareMap.get(IMU.class, "m_imu");
        this.m_imuParameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.RIGHT
        ));


        this.m_imu.initialize(this.m_imuParameters);


        /* Gamepad */

        this.m_driver1 = new GamepadEx(gamepad1);
        this.m_driver2 = new GamepadEx(gamepad2);


        /* Motors */

        this.m_slideArmMotor = hardwareMap.get(DcMotor.class, "slideArmMotor");
        this.m_liftArmMotor = hardwareMap.get(DcMotor.class, "liftArmMotor");
        this.m_intakeWheelServo = new CRServo(hardwareMap, "intakeWheelServo");

        /* Sensors */
        this.m_touchSensor = hardwareMap.get(TouchSensor.class, "intakeTouchSensor");

        this.m_pIDController = new PIDController(0, 0, 0);
        this.m_pIDController.setPID(0.0, 0.0, 0.0);



        /* Subsystems */

        this.m_driveSubsystem = new DriveSubsystem(this.m_drive, this.m_odo/*, this.m_goBilda*/);
        this.m_slideArmSubsystem = new SlideArmSubsystem(this.m_slideArmMotor);
        this.m_liftArmSubsystem = new LiftArmSubsystem(this.m_liftArmMotor)/*() -> this.m_pIDController.calculate(this.m_liftArmMotor.getCurrentPosition()))*/;
        this.m_intakePivotSubsystem = new IntakePivotSubsystem(hardwareMap, "intakePivotServo");
        this.m_intakeWheelSubsystem = new IntakeWheelSubsystem(this.m_intakeWheelServo, this.m_touchSensor);
        this.m_ascentArmSubsystem = new StingRayArmSubsystem(hardwareMap, "ascentArmServo");
        this.m_gyroSubsystem = new GyroSubsystem(this.m_odo);
//        this.m_pinPointOdometrySubsystem = new PinPointOdometrySubsystem(this.m_odo,this.leftFront, this.rightFront, this.leftBack,this.rightBack);
        this.m_timerSubsystem = new TimerSubsystem(this.m_driver1, this.m_driver2);
        this.m_grabBatSubsystem = new GrabBatSubsystem(hardwareMap, "grabBatServo");
        Pose2DUnNormalized NET_ZONE = new Pose2DUnNormalized(DistanceUnit.MM, 240, 480, UnnormalizedAngleUnit.DEGREES, -45);


        register(this.m_driveSubsystem);
        register(this.m_intakeWheelSubsystem);
        register(this.m_timerSubsystem);

        /* Default Commands */

        this.m_driveFieldOrientedCommand = new DriveFieldOrientedCommand(this.m_driveSubsystem, () -> this.m_driver1.getLeftX(),
                () -> this.m_driver1.getLeftY(), () -> this.m_driver1.getRightX(), () -> this.m_driver1.getRightY());
        this.m_driveSubsystem.setDefaultCommand(this.m_driveFieldOrientedCommand);

        this.m_timerCommand = new TimerCommand (this.m_timerSubsystem, () -> getRuntime());
        this.m_timerSubsystem.setDefaultCommand(this.m_timerCommand);


        schedule();

        this.m_intakeWheelCommand = new IntakeWheelCommand(this.m_intakeWheelSubsystem, () -> this.m_driver2.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER),
                () -> this.m_driver2.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER));
        this.m_intakeWheelSubsystem.setDefaultCommand(this.m_intakeWheelCommand);



        /* Event Commands */


        this.m_TestPose2DTeleOpCommand = new TestPose2DTeleOpCommand(this.m_driveSubsystem, this.m_odo,
                this.leftFront, this.rightFront, this.leftBack,this.rightBack);
        new GamepadButton(this.m_driver1, GamepadKeys.Button.X).whenPressed(this.m_TestPose2DTeleOpCommand);



        this.m_resetHomeCommand = new ResetHomeCommand(this.m_liftArmSubsystem, this.m_slideArmSubsystem);
        this.m_resetHomeButton = (new GamepadButton(this.m_driver2, GamepadKeys.Button.START))
                .whenPressed(this.m_resetHomeCommand);

        this.m_slideFudgeInCommand = new SlideFudgeInCommand(m_slideArmSubsystem);
        this.m_slideFudgeInButton = (new GamepadButton(this.m_driver2, GamepadKeys.Button.BACK))
                .whileHeld(this.m_slideFudgeInCommand);

        this.m_slideFudgeOutCommand = new SlideFudgeOutCommand(m_slideArmSubsystem);
        this.m_slideFudgeOutButton = (new GamepadButton(this.m_driver2, GamepadKeys.Button.LEFT_STICK_BUTTON))
                .whileHeld(this.m_slideFudgeOutCommand);

        this.m_armFudgeFactorUpCommand = new ArmFudgeFactorUpCommand(m_liftArmSubsystem);
        this.m_dpadRight = (new GamepadButton(this.m_driver2, GamepadKeys.Button.DPAD_RIGHT))
                .whenPressed(this.m_armFudgeFactorUpCommand);

        this.m_armFudgeFactorDownCommand = new ArmFudgeFactorDownCommand(m_liftArmSubsystem);
        this.m_dpadLeft = (new GamepadButton(this.m_driver2, GamepadKeys.Button.DPAD_LEFT))
                .whenPressed(this.m_armFudgeFactorDownCommand);

        this.m_armPositionCloseSampleCommand = new ArmPositionCloseSampleCommand(m_liftArmSubsystem, m_slideArmSubsystem, this.m_intakePivotSubsystem);
        this.m_square = (new GamepadButton(this.m_driver2, GamepadKeys.Button.X))
                .whenPressed(this.m_armPositionCloseSampleCommand);

        this.m_armPositionSubmersiblePickUpCommand = new ArmPositionMidSampleCommand(m_liftArmSubsystem, m_slideArmSubsystem, m_intakePivotSubsystem);
        this.m_xButton = (new GamepadButton(this.m_driver2, GamepadKeys.Button.A))
                .whenPressed(this.m_armPositionSubmersiblePickUpCommand);

        this.m_armPositionFarSampleCommand = new ArmPositionFarSampleCommand(m_liftArmSubsystem, m_slideArmSubsystem, m_intakePivotSubsystem);
        this.m_circle = (new GamepadButton(this.m_driver2, GamepadKeys.Button.B))
                .whenPressed(this.m_armPositionFarSampleCommand);

        this.m_armPositionHighBasketCommand = new ArmPositionHighBasketCommand(m_liftArmSubsystem, m_slideArmSubsystem, m_intakePivotSubsystem);
        this.m_triangle = (new GamepadButton(this.m_driver2, GamepadKeys.Button.Y))
                .whenPressed(this.m_armPositionHighBasketCommand);

        this.m_armPositionHighChamberCommand = new ArmPositionHighChamberCommand(m_liftArmSubsystem, m_slideArmSubsystem, this.m_intakePivotSubsystem);
        this.m_dpadTop = (new GamepadButton(this.m_driver2, GamepadKeys.Button.DPAD_UP))
                .whenPressed(this.m_armPositionHighChamberCommand);

        this.m_armPositionScoreHighChamberCommand = new ArmPositionScoreHighChamberCommand
                (m_liftArmSubsystem, m_slideArmSubsystem, m_intakePivotSubsystem, m_intakeWheelSubsystem);
        this.m_dpadBottom = (new GamepadButton(this.m_driver2, GamepadKeys.Button.DPAD_DOWN))
                .whenPressed(this.m_armPositionScoreHighChamberCommand);

        this.m_armPositionTravelCommand = new ArmPositionTravelCommand(this.m_liftArmSubsystem, this.m_slideArmSubsystem, this.m_intakePivotSubsystem);
        this.m_leftBumper = (new GamepadButton(this.m_driver2, GamepadKeys.Button.LEFT_BUMPER))
                .whenPressed(this.m_armPositionTravelCommand);

        this.m_armPositionTravelCommand = new ArmPositionTravelCommand(this.m_liftArmSubsystem, this.m_slideArmSubsystem, this.m_intakePivotSubsystem);
        this.m_xButton = (new GamepadButton(this.m_driver1, GamepadKeys.Button.A))
                .whenPressed(this.m_armPositionTravelCommand);

        this.m_releaseCLimberArmCommand = new ClimbArmReleaseCommand(m_liftArmSubsystem);
        this.m_releaseClimbButton = (new GamepadButton(this.m_driver1, GamepadKeys.Button.Y))
                .whenPressed(this.m_releaseCLimberArmCommand);

        this.m_intakePivotCommand = new IntakePivotCommand(this.m_intakePivotSubsystem);
        this.m_rightBumper = (new GamepadButton(this.m_driver2, GamepadKeys.Button.RIGHT_BUMPER))
                .whenPressed(this.m_intakePivotCommand);

        this.m_ascentArmCommand = new StingrayAscent1ArmCommand(this.m_ascentArmSubsystem);
        this.m_leftBumper = (new GamepadButton(this.m_driver1, GamepadKeys.Button.LEFT_BUMPER))
                .whenPressed(this.m_ascentArmCommand);

        this.m_resetGyroCommand = new ResetGyroCommand(this.m_gyroSubsystem);
        this.m_gyroResetButton = (new GamepadButton(this.m_driver1, GamepadKeys.Button.START))
                .whenPressed(this.m_resetGyroCommand);

        this.m_grabBatCommand = new GrabBatCommand(this.m_grabBatSubsystem);
        this.m_rightBumper = (new GamepadButton(this.m_driver1, GamepadKeys.Button.RIGHT_BUMPER))
                .whenPressed(this.m_grabBatCommand);
    }

//    @Override
//    public void run()
//    {
//        telemetry.addData("Status", m_odo.getDeviceStatus());
//        telemetry.addData("Pinpoint Frequency", m_odo.getFrequency());
//        telemetry.addData("(Position", m_odo.getPosition());
//        telemetry.update();
//    }

//        for (int i = 1; i>0; i+=0)
//        {
//            telemetry.addData("Lift Arm", this.m_liftArmMotor.getCurrentPosition());
//            telemetry.addData("Slide Arm", this.m_slideArmMotor.getCurrentPosition());
//            telemetry.update();
//        }
//    }
}
