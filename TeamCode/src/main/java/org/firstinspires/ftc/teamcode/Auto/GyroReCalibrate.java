package org.firstinspires.ftc.teamcode.Auto;

import static teamCode.Constants.LiftArmConstants.kLiftArmCloseSample;
import static teamCode.Constants.LiftArmConstants.kLiftArmHighBasket;
import static teamCode.Constants.LiftArmConstants.kLiftArmIntakeReset;
import static teamCode.Constants.PivotIntakeConstants.kIntakePivotPickUp;
import static teamCode.Constants.PivotIntakeConstants.kIntakePivotScore;
import static teamCode.Constants.SlideArmConstants.kSlideArmCloseSample;
import static teamCode.Constants.SlideArmConstants.kSlideArmHighBasket;

import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.TouchSensor;

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

@Autonomous(name="Gyro ReCalibrate", group="Pinpoint")
//@Disabled

public class GyroReCalibrate extends LinearOpMode
{


    GoBildaPinpointDriver odo; // Declare OpMode member for the Odometry Computer
    DriveToPoint nav = new DriveToPoint(this); //OpMode member for the point-to-point navigation class

    @Override
    public void runOpMode()
    {

        odo = hardwareMap.get(GoBildaPinpointDriver.class, "odo");
        odo.setOffsets(68, -178);//these are tuned for 3110-0002-0001 Product Insight #1
        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED, GoBildaPinpointDriver.EncoderDirection.FORWARD);

        odo.resetPosAndIMU();
        waitForStart();

        odo.recalibrateIMU();
        sleep(2500);
        telemetry.addLine("Gyro Reset");
        resetRuntime();
    }
}
