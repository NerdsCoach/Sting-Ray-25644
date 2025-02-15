//package teamCode;
//
//import androidx.annotation.NonNull;
//
//import com.arcrobotics.ftclib.hardware.motors.CRServo;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//
//import java.util.function.BooleanSupplier;
//
//import teamCode.autoSubsystems.AutoDriveSubsystem;
//
//import teamCode.subsystems.SlideArmSubsystem;
//import teamCode.subsystems.LiftArmSubsystem;
//import teamCode.subsystems.IntakePivotSubsystem;
//import teamCode.subsystems.IntakeWheelSubsystem;
//import teamCode.subsystems.StingRayArmSubsystem;
//
//import teamCode.commands.ArmPositionTravelCommand;
//import teamCode.commands.ArmPositionCloseSampleCommand;
//import teamCode.commands.ArmPositionHighBasketCommand;
//import teamCode.commands.IntakePivotCommand;
//import teamCode.commands.StingrayArmCommand;
//
////package org.firstinspires.ftc.teamcode.Auto;
//
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//
//import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
//import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
//import org.firstinspires.ftc.teamcode.Libs.GoBilda.DriveToPoint;
//import org.firstinspires.ftc.teamcode.Libs.GoBilda.GoBildaPinpointDriver;
//
//import java.util.Locale;
//
//
//@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "AutoSample")
//public class AutoSample extends LinearOpMode
//{
//    private DcMotor m_fLMotor;
//    private DcMotor m_fRMotor;
//    private DcMotor m_bLMotor;
//    private DcMotor m_bRMotor;
//    private DcMotor m_liftArmMotor;
//    private DcMotor m_slideArmMotor;
//    private CRServo m_intakeWheelServo;
//    private IntakePivotSubsystem m_intakePivotSubsystem;
//    private StingRayArmSubsystem m_ascentArmSubsystem;
//    private AutoDriveSubsystem m_autoDriveSubsystem;
//    private LiftArmSubsystem m_liftArmSubsystem;
//    private SlideArmSubsystem m_slideArmSubsystem;
//    private IntakeWheelSubsystem m_intakeWheelSubsystem;
//    private ArmPositionHighBasketCommand m_armPositionHighBasketCommand;
//    private ArmPositionTravelCommand m_armPositionHomeCommand;
//    private ArmPositionCloseSampleCommand m_armPositionCloseSampleCommand;
//    private IntakePivotCommand m_intakePivotCommand;
//    private StingrayArmCommand m_ascentArmCommand;
////    private SensorDigitalTouch m_touch;
//
//    teamCode.GoBildaPinpointDriver odo; // Declare OpMode member for the Odometry Computer
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//
//    }
////    DriveToPoint nav = new DriveToPoint(this); //OpMode member for the point-to-point navigation class
//
//    enum StateMachine {
//        WAITING_FOR_START,
//        AT_TARGET,
//        DRIVE_TO_TARGET_1,
//        DRIVE_TO_TARGET_2,
//        DRIVE_TO_TARGET_3,
//        DRIVE_TO_TARGET_4,
//        DRIVE_TO_TARGET_5;
//
////        static final Pose2D TARGET_1 = new Pose2D(DistanceUnit.MM,200,0,AngleUnit.DEGREES,0);
////        static final Pose2D TARGET_2 = new Pose2D(DistanceUnit.MM, 260, 0, AngleUnit.DEGREES, -90);
////        static final Pose2D TARGET_3 = new Pose2D(DistanceUnit.MM,260,0, AngleUnit.DEGREES,-90);
////        static final Pose2D TARGET_4 = new Pose2D(DistanceUnit.MM, 10, 0, AngleUnit.DEGREES, 90);
////        static final Pose2D TARGET_5 = new Pose2D(DistanceUnit.MM, 10, 0, AngleUnit.DEGREES, 0);
//
//
//    @Override
//    public void runOpMode()
//    {
//        Logic.OpModeType.opMode = "AutoSample";
//        this.m_fLMotor = hardwareMap.get(DcMotor.class, "leftFront");
//        this.m_fRMotor = hardwareMap.get(DcMotor.class, "rightFront_perp");
//        this.m_bLMotor = hardwareMap.get(DcMotor.class, "leftBack_par");
//        this.m_bRMotor = hardwareMap.get(DcMotor.class, "rightBack");
//
//        this.m_fLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        this.m_fRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        this.m_bLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        this.m_bRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//        this.m_fLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        this.m_fRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        this.m_bLMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        this.m_bRMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        this.m_fLMotor.setDirection(DcMotor.Direction.REVERSE);
//        this.m_bLMotor.setDirection(DcMotor.Direction.REVERSE);
//
//        this.m_fLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        this.m_fRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        this.m_bLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        this.m_bRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//
//
//        this.m_liftArmMotor = hardwareMap.get(DcMotor.class, "liftArmMotor");
//        this.m_slideArmMotor = hardwareMap.get(DcMotor.class, "slideArmMotor");
//        this.m_intakeWheelServo = new CRServo(hardwareMap, "intakeWheelServo");
//        this.m_intakePivotSubsystem = new IntakePivotSubsystem(hardwareMap, "intakePivotServo");
//        this.m_ascentArmSubsystem = new StingRayArmSubsystem(hardwareMap, "ascentArmServo");
//
//        this.m_autoDriveSubsystem = new AutoDriveSubsystem(this.m_fLMotor, this.m_fRMotor, this.m_bLMotor, this.m_bRMotor);
//        this.m_liftArmSubsystem = new LiftArmSubsystem(this.m_liftArmMotor);
//        this.m_slideArmSubsystem = new SlideArmSubsystem(this.m_slideArmMotor);
//        this.m_intakeWheelSubsystem = new IntakeWheelSubsystem(this.m_intakeWheelServo/*, m_touch*/);
//
//        this.m_armPositionHomeCommand = new ArmPositionTravelCommand(this.m_liftArmSubsystem, this.m_slideArmSubsystem, this.m_intakePivotSubsystem);
//        this.m_armPositionCloseSampleCommand = new ArmPositionCloseSampleCommand(this.m_liftArmSubsystem, this.m_slideArmSubsystem, this.m_intakePivotSubsystem);
//        this.m_armPositionHighBasketCommand = new ArmPositionHighBasketCommand(this.m_liftArmSubsystem, this.m_slideArmSubsystem, this.m_intakePivotSubsystem);
//        this.m_intakePivotCommand = new IntakePivotCommand(this.m_intakePivotSubsystem);
//        this.m_ascentArmCommand = new StingrayArmCommand(this.m_ascentArmSubsystem);
//
//
//        // Initialize the hardware variables. Note that the strings used here must correspond
//        // to the names assigned during the robot configuration step on the DS or RC devices.
//
//        leftFrontDrive  = hardwareMap.get(DcMotor.class, "left_front_mtr");
//        rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front_mtr");
//        leftBackDrive   = hardwareMap.get(DcMotor.class, "left_back_mtr");
//        rightBackDrive  = hardwareMap.get(DcMotor.class, "right_back_mtr");
//
//        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//
//        leftFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE);
//        leftBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);
//
//        odo = hardwareMap.get(GoBildaPinpointDriver.class,"odo");
//        odo.setOffsets(-142.0, 120.0); //these are tuned for 3110-0002-0001 Product Insight #1
//        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
//        odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED, GoBildaPinpointDriver.EncoderDirection.FORWARD);
//
//        odo.resetPosAndIMU();
//        //nav.setXYCoefficients(0.02,0.002,0.0,DistanceUnit.MM,12);
//        //nav.setYawCoefficients(1,0,0.0, AngleUnit.DEGREES,2);
//        nav.setDriveType(DriveToPoint.DriveType.MECANUM);
//
//        StateMachine stateMachine;
//        stateMachine = StateMachine.WAITING_FOR_START;
//
//        telemetry.addData("Status", "Initialized");
//        telemetry.addData("X offset", odo.getXOffset());
//        telemetry.addData("Y offset", odo.getYOffset());
//        telemetry.addData("Device Version Number:", odo.getDeviceVersion());
//        telemetry.addData("Device Scalar", odo.getYawScalar());
//        telemetry.update();
//
//        // Wait for the game to start (driver presses START)
//        waitForStart();
//        resetRuntime();
//
//        while (opModeIsActive()) {
//            odo.update();
//
//            switch (stateMachine) {
//                case WAITING_FOR_START:
//                    //the first step in the autonomous
//                    stateMachine = StateMachine.DRIVE_TO_TARGET_1;
//                    break;
//                case DRIVE_TO_TARGET_1:
//                    drive the robot to the first target, the nav.driveTo function will return true
//                once
//                the robot has reached the target, and has been there for (holdTime)
//                    seconds.
//                            Once driveTo returns true, it prints a telemetry line and moves
//                the state machine forward.
//                     */
//                if (nav.driveTo(odo.getPosition(), TARGET_1, 0.7, 0)) {
//                    telemetry.addLine("at position #1!");
//                    //stateMachine = StateMachine.DRIVE_TO_TARGET_2;
//                }
//                break;
//                case DRIVE_TO_TARGET_2:
//                    //drive to the second target
//                    if (nav.driveTo(odo.getPosition(), TARGET_2, 0.7, 1)) {
//                        telemetry.addLine("at position #2!");
//                        stateMachine = StateMachine.DRIVE_TO_TARGET_3;
//                    }
//                    break;
//                case DRIVE_TO_TARGET_3:
//                    if (nav.driveTo(odo.getPosition(), TARGET_3, 0.7, 3)) {
//                        telemetry.addLine("at position #3");
//                        stateMachine = StateMachine.DRIVE_TO_TARGET_4;
//                    }
//                    break;
//                case DRIVE_TO_TARGET_4:
//                    if (nav.driveTo(odo.getPosition(), TARGET_4, 0.7, 1)) {
//                        telemetry.addLine("at position #4");
//                        stateMachine = StateMachine.DRIVE_TO_TARGET_5;
//                    }
//                    break;
//                case DRIVE_TO_TARGET_5:
//                    if (nav.driveTo(odo.getPosition(), TARGET_5, 0.7, 1)) {
//                        telemetry.addLine("There!");
//                        stateMachine = StateMachine.AT_TARGET;
//                    }
//                    break;
//            }
//
//
//            //nav calculates the power to set to each motor in a mecanum or tank drive. Use nav.getMotorPower to find that value.
//            leftFrontDrive.setPower(nav.getMotorPower(DriveToPoint.DriveMotor.LEFT_FRONT));
//            rightFrontDrive.setPower(nav.getMotorPower(DriveToPoint.DriveMotor.RIGHT_FRONT));
//            leftBackDrive.setPower(nav.getMotorPower(DriveToPoint.DriveMotor.LEFT_BACK));
//            rightBackDrive.setPower(nav.getMotorPower(DriveToPoint.DriveMotor.RIGHT_BACK));
//
//            telemetry.addData("current state:",stateMachine);
//
//            Pose2D pos = odo.getPosition();
//            String data = String.format(Locale.US, "{X: %.3f, Y: %.3f, H: %.3f}", pos.getX(DistanceUnit.MM), pos.getY(DistanceUnit.MM), pos.getHeading(AngleUnit.DEGREES));
//            telemetry.addData("Position", data);
//
//            telemetry.update();
//
//        }
//    }}
//
//                    /*
//
//        //Initialize
//        this.m_armPositionHomeCommand.execute();
//        this.m_intakePivotSubsystem.pivotIntake(0.85);
//        waitForStart();
//
//        this.m_autoDriveSubsystem.driveRobot(760, -760, -760, 760);//Strafe right
//        this.m_intakePivotSubsystem.pivotIntake(0.5);//Pivot intake
//        wait(()-> this.m_autoDriveSubsystem.atTarget(760));
//
//        this.m_autoDriveSubsystem.stop();
//        this.m_autoDriveSubsystem.driveRobot(640, 640, 640, 640);//Drive forward
//        wait(()-> this.m_autoDriveSubsystem.atTarget(640));
//
//        this.m_autoDriveSubsystem.stop();
//        this.m_autoDriveSubsystem.driveRobot(-512, 512, -512, 512);//45 degree Left turn
//        wait(()-> this.m_autoDriveSubsystem.atTarget(512));
//
//        this.m_autoDriveSubsystem.stop();
//        this.m_autoDriveSubsystem.driveRobot(450, 450, 450, 450);//Drive forward
//        wait(()-> this.m_autoDriveSubsystem.atTarget(450));
//
//        this.m_armPositionHighBasketCommand.execute();
//        wait(()-> this.m_liftArmSubsystem.atTarget(2070) && this.m_slideArmSubsystem.atTarget(-2220));
//
//        this.m_intakeWheelSubsystem.spinIntake(0.5);//Score in high basket
//        sleep(1000);
//        this.m_intakeWheelSubsystem.spinIntake(0.0);
//
//        this.m_autoDriveSubsystem.stop();
//        this.m_autoDriveSubsystem.driveRobot(-460, -460, -460, -460);//Backup
//        wait(()-> this.m_autoDriveSubsystem.atTarget(460));
//
//        this.m_autoDriveSubsystem.stop();
//        this.m_autoDriveSubsystem.driveRobot(512, -512, 512, -512);//45 degree Right turn
//        wait(()-> this.m_autoDriveSubsystem.atTarget(512));
//
//        this.m_armPositionHomeCommand.execute();
//        wait(()-> this.m_liftArmSubsystem.atTarget(0) && this.m_slideArmSubsystem.atTarget(-25));
//
//        this.m_autoDriveSubsystem.stop();
//        this.m_autoDriveSubsystem.driveRobot(-500, -500, -500, -500);//Back up *was 645
//        wait(()-> this.m_autoDriveSubsystem.atTarget(500));
//
//        this.m_autoDriveSubsystem.stop();
//        this.m_autoDriveSubsystem.driveRobot(1150, -1150, -1150, 1150);//Strafe right to samples
//        wait(()-> this.m_autoDriveSubsystem.atTarget(1150));
//
//        this.m_armPositionCloseSampleCommand.execute();
//        this.m_intakeWheelSubsystem.spinIntake(-1.0);//Pick up field sample
//
//        sleep(1000);
//        this.m_intakeWheelSubsystem.spinIntake(0.0);//stop intake
//        sleep(500);
//        this.m_armPositionHomeCommand.execute();
//
//        this.m_autoDriveSubsystem.stop();
//        this.m_autoDriveSubsystem.driveRobot(-1150, 1150, 1150, -1150);// Strafe
//        wait(()-> this.m_autoDriveSubsystem.atTarget(1150));
//
//        this.m_autoDriveSubsystem.stop();
//        this.m_autoDriveSubsystem.driveRobot(425, 425, 425, 425);//Drive
//        wait(()-> this.m_autoDriveSubsystem.atTarget(425));
//
//        this.m_autoDriveSubsystem.stop();
//        this.m_autoDriveSubsystem.driveRobot(-512, 512, -512, 512);//45 degree Left turn
//        wait(()-> this.m_autoDriveSubsystem.atTarget(512));
//
//        this.m_autoDriveSubsystem.stop();
//        this.m_autoDriveSubsystem.driveRobot(450, 450, 450, 450);//Drive forward
//        wait(()-> this.m_autoDriveSubsystem.atTarget(450));
//
//        this.m_armPositionHighBasketCommand.execute();
//        wait(()-> this.m_liftArmSubsystem.atTarget(2070) && this.m_slideArmSubsystem.atTarget(-2220));
//
//        this.m_intakeWheelSubsystem.spinIntake(0.5);//Score in high basket
//        sleep(1000);
//        this.m_intakeWheelSubsystem.spinIntake(0.0);
//
//        this.m_autoDriveSubsystem.stop();
//        this.m_autoDriveSubsystem.driveRobot(-410, -410, -410, -410);
//        wait(()-> this.m_autoDriveSubsystem.atTarget(410));
//
//        this.m_armPositionHomeCommand.execute();
//        wait(()-> this.m_liftArmSubsystem.atTarget(0) && this.m_slideArmSubsystem.atTarget(-25));
//        sleep(2000);
//
//        this.m_autoDriveSubsystem.stop();
//        this.m_autoDriveSubsystem.driveRobot(512, -512, 512, -512);//45 degree Right turn
//        wait(()-> this.m_autoDriveSubsystem.atTarget(512));
//
//        this.m_autoDriveSubsystem.stop();
//        this.m_autoDriveSubsystem.driveRobot(2000, -2000, -2000, 2000);//Strafe right to samples
//        wait(()-> this.m_autoDriveSubsystem.atTarget(2000));
//
//        this.m_autoDriveSubsystem.stop();
//        this.m_autoDriveSubsystem.driveRobot(-1100, -1100, -1100, -1100);//Back up *was 645
//        wait(()-> this.m_autoDriveSubsystem.atTarget(1100));
//
//        this.m_ascentArmCommand.execute();
//        this.m_ascentArmSubsystem.ascentArm(0.78);
//        sleep(2000);
//
//
//    }
//
//    /**
//     * @Params: Wait until parameter event is true.
//     */
//    public void wait(@NonNull BooleanSupplier condition)
//    {
//        while(!condition.getAsBoolean() && opModeIsActive())
//        {
//            telemetry.addData("Waiting: ", condition.getAsBoolean());
//            telemetry.update();
//        }
//    }
//}