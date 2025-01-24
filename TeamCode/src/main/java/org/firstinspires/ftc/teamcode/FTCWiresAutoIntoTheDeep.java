/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.util.ElapsedTime.Resolution.SECONDS;

import static teamCode.Constants.LiftArmConstants.kLiftArmCloseSample;
import static teamCode.Constants.LiftArmConstants.kLiftArmHighBasket;
import static teamCode.Constants.LiftArmConstants.kLiftArmIntakeReset;
import static teamCode.Constants.PivotIntakeConstants.kIntakePivotPickUp;
import static teamCode.Constants.PivotIntakeConstants.kIntakePivotScore;
import static teamCode.Constants.SlideArmConstants.kSlideArmCloseSample;
import static teamCode.Constants.SlideArmConstants.kSlideArmHighBasket;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import teamCode.autoSubsystems.AutoDriveSubsystem;
import teamCode.commands.ArmIntakeResetCommand;
import teamCode.commands.ArmPositionCloseSampleCommand;
import teamCode.commands.ArmPositionHighBasketCommand;
import teamCode.commands.ArmPositionHighChamberCommand;
import teamCode.commands.ArmPositionHomeCommand;
import teamCode.commands.IntakePivotCommand;
import teamCode.commands.ScoreSpecimenCommand;
import teamCode.commands.StingrayArmCommand;
import teamCode.subsystems.AscentArmSubsystem;
import teamCode.subsystems.IntakePivotSubsystem;
import teamCode.subsystems.IntakeWheelSubsystem;
import teamCode.subsystems.LiftArmSubsystem;
import teamCode.subsystems.SlideArmSubsystem;

/**
 * FTC WIRES Autonomous Example for only vision detection using tensorflow and park
 */
@Autonomous(name = "StingRayWires", group = "00-Autonomous", preselectTeleOp = "FTC Wires TeleOp")
public class FTCWiresAutoIntoTheDeep extends LinearOpMode
{
    private DcMotor m_liftArmMotor;
    private DcMotor m_slideArmMotor;
    private CRServo m_intakeWheelServo;
    private IntakePivotSubsystem m_intakePivotSubsystem;
    private AscentArmSubsystem m_ascentArmSubsystem;
    private AutoDriveSubsystem m_autoDriveSubsystem;
    private LiftArmSubsystem m_liftArmSubsystem;
    private SlideArmSubsystem m_slideArmSubsystem;
    private IntakeWheelSubsystem m_intakeWheelSubsystem;
    private ArmPositionHighBasketCommand m_armPositionHighBasketCommand;
    private ArmPositionHighChamberCommand m_armPositionHighChamberCommand;
    private ArmPositionHomeCommand m_armPositionHomeCommand;
    private ArmPositionCloseSampleCommand m_armPositionCloseSampleCommand;
    private ArmIntakeResetCommand m_armIntakeResetCommand;
    private IntakePivotCommand m_intakePivotCommand;
    private StingrayArmCommand m_ascentArmCommand;
   // private ArmFudgeFactorUpCommand m_armFudgeFactorUpCommand;
    private ScoreSpecimenCommand m_scoreSpecimenCommand;

    public static String TEAM_NAME = "Nerds On A Mission"; //TODO: Enter team Name
    public static int TEAM_NUMBER = 25644; //TODO: Enter team Number

    //Define and declare Robot Starting Locations
    public enum START_POSITION
    {
        LEFT,
        RIGHT
    }
    public static START_POSITION startPosition;

    @Override
    public void runOpMode() throws InterruptedException
    {
        this.m_liftArmMotor = hardwareMap.get(DcMotor.class, "liftArmMotor");
        this.m_slideArmMotor = hardwareMap.get(DcMotor.class, "slideArmMotor");
        this.m_intakeWheelServo = new CRServo(hardwareMap, "intakeWheelServo");

        this.m_intakePivotSubsystem = new IntakePivotSubsystem(hardwareMap, "intakePivotServo");
        this.m_ascentArmSubsystem = new AscentArmSubsystem(hardwareMap, "ascentArmServo");

        this.m_liftArmSubsystem = new LiftArmSubsystem(this.m_liftArmMotor);
        this.m_slideArmSubsystem = new SlideArmSubsystem(this.m_slideArmMotor);
        this.m_intakeWheelSubsystem = new IntakeWheelSubsystem(this.m_intakeWheelServo/*,this.m_touch*/);

        this.m_armPositionHomeCommand = new ArmPositionHomeCommand(this.m_liftArmSubsystem, this.m_slideArmSubsystem, this.m_intakePivotSubsystem);
        this.m_armPositionCloseSampleCommand = new ArmPositionCloseSampleCommand(this.m_liftArmSubsystem, this.m_slideArmSubsystem, this.m_intakePivotSubsystem);
        this.m_armPositionHighBasketCommand = new ArmPositionHighBasketCommand(this.m_liftArmSubsystem, this.m_slideArmSubsystem, this.m_intakePivotSubsystem);
        this.m_armPositionHighChamberCommand = new ArmPositionHighChamberCommand(this.m_liftArmSubsystem,this.m_slideArmSubsystem, this.m_intakePivotSubsystem);
        this.m_intakePivotCommand = new IntakePivotCommand(this.m_intakePivotSubsystem);
        this.m_ascentArmCommand = new StingrayArmCommand(this.m_ascentArmSubsystem);
        //this.m_armFudgeFactorUpCommand = new ArmFudgeFactorUpCommand(this.m_liftArmSubsystem);
        this.m_scoreSpecimenCommand = new ScoreSpecimenCommand(this.m_liftArmSubsystem);


        //Key Pad input to selecting Starting Position of robot
        telemetry.setAutoClear(true);
        telemetry.clearAll();
        while(!isStopRequested()){
            telemetry.addData("Initializing FTC Wires (ftcwires.org) Autonomous adopted for Team:",
                    TEAM_NAME, " ", TEAM_NUMBER);
            telemetry.addData("---------------------------------------","");
            telemetry.addData("Select Starting Position using XYAB on Logitech (or ▢ΔOX on Playstayion) on gamepad 1:","");
            telemetry.addData("    Left   ", "(perp / ▢)");
            telemetry.addData("    Right ", "(par / Δ)");

            if(gamepad1.x){
                startPosition = START_POSITION.LEFT;
                break;
            }
            if(gamepad1.y){
                startPosition = START_POSITION.RIGHT;
                break;
            }
            telemetry.update();
        }
        telemetry.setAutoClear(false);
        telemetry.clearAll();

        telemetry.addData("Selected Starting Position", startPosition);
        telemetry.update();

        waitForStart();

        //Game Play Button  is pressed
        if (opModeIsActive() && !isStopRequested()) {
            //Build parking trajectory based on last detected target by vision
            runAutonoumousMode();
        }
    }   // end runOpMode()


    public void runAutonoumousMode()
    {
        //Auto Left Positions - Samples

        Pose2d initPose = new Pose2d(0, -6, Math.toRadians(0)); // Starting Pose
        Pose2d submersibleSpecimen = new Pose2d(28,-1,Math.toRadians(0) );
        Pose2d netZone = new Pose2d(9  ,16,Math.toRadians(-45));
        Pose2d yellowSampleOne = new Pose2d(24,9,Math.toRadians(0));//(18,12,Math.toRadians(-14)
        Pose2d yellowSampleTwo = new Pose2d(24,19,Math.toRadians(1));
        Pose2d preSubmersiblePark = new Pose2d(58,11,Math.toRadians(0));
        Pose2d submersiblePark = new Pose2d(59,-15,Math.toRadians(90));

        //Auto Right Positions - Specimens
        Pose2d observationZone = new Pose2d(8,-37,Math.toRadians(0));
        Pose2d specimenPickup = new Pose2d(3,-30,Math.toRadians(0));
        Pose2d preColorSampleOne = new Pose2d(28,-27,Math.toRadians(0));
        Pose2d prePushColorSampleOne = new Pose2d(51,-27,Math.toRadians(0));
        Pose2d colorSampleOne = new Pose2d(51,-40,Math.toRadians(0));
        Pose2d observationPark = new Pose2d(5,-30,Math.toRadians(0));

        double waitSecondsBeforeDrop = 0;
        MecanumDrive drive = new MecanumDrive(hardwareMap, initPose);

        if (startPosition == START_POSITION.LEFT)
        {
//            this.m_armIntakeResetCommand.execute();
            this.m_liftArmSubsystem.liftArm(kLiftArmIntakeReset);
            safeWaitSeconds(.5);

//            this.m_intakePivotSubsystem.pivotIntake(kIntakePivotScore);

            //Move robot to netZone with preloaded sample ready to drop in basket
            Actions.runBlocking(
                    drive.actionBuilder(initPose)
                            .strafeToLinearHeading(netZone.position, netZone.heading)
                            .build());
//            safeWaitSeconds(.5);
            telemetry.addLine("Move robot to netZone");
            telemetry.update();

            this.m_liftArmSubsystem.liftArm(kLiftArmHighBasket);
            safeWaitSeconds(.5);
            this.m_slideArmSubsystem.slideArm(kSlideArmHighBasket);
            safeWaitSeconds(.5);
            this.m_intakePivotSubsystem.pivotIntake(kIntakePivotScore);

            //Add code to drop sample in basket
            safeWaitSeconds(.5);
            this.m_intakeWheelSubsystem.spinIntake(0.5);//Spit out

            safeWaitSeconds(1.5);
            this.m_intakeWheelSubsystem.spinIntake(0.0);
//
            telemetry.addLine("Drop sample in basket");
            telemetry.update();
            safeWaitSeconds(1);

            this.m_intakePivotSubsystem.pivotIntake(kIntakePivotPickUp);
            safeWaitSeconds(.5);
            this.m_slideArmSubsystem.slideArm(kSlideArmCloseSample);
            safeWaitSeconds(.5);
            this.m_liftArmSubsystem.liftArm(kLiftArmCloseSample);

            safeWaitSeconds(.5);
            this.m_intakeWheelSubsystem.spinIntake(-0.5);//Pick Up

//            safeWaitSeconds(1.5);
//            this.m_intakeWheelSubsystem.spinIntake(0.0);

            //Move robot to pick yellow sample one
            Actions.runBlocking(
                    drive.actionBuilder(netZone)
                            .strafeToLinearHeading(yellowSampleOne.position, yellowSampleOne.heading)
                            .build());
            safeWaitSeconds(1);
            telemetry.addLine("Move robot to pick yellow sample one");
            telemetry.update();

            this.m_intakeWheelSubsystem.spinIntake(0.0);
            //Add code to pick up yellow sample
            safeWaitSeconds(1);
            telemetry.addLine("Pick up yellow sample");
            telemetry.update();



            //Move robot to net zone to drop sample
            Actions.runBlocking(
                    drive.actionBuilder(yellowSampleOne)
                            .strafeToLinearHeading(netZone.position, netZone.heading)
                            .build());
            safeWaitSeconds(1);
            telemetry.addLine("Move robot to net zone to drop sample");
            telemetry.update();

            //Add code to drop sample in bucket
            safeWaitSeconds(1);
            telemetry.addLine("Drop sample in bucket");
            telemetry.update();

            //Move robot to yellow sample two
            Actions.runBlocking(
                    drive.actionBuilder(netZone)
                            .strafeToLinearHeading(yellowSampleTwo.position, yellowSampleTwo.heading)
                            .build());
            safeWaitSeconds(1);
            telemetry.addLine("Move robot to yellow sample two");
            telemetry.update();

            //Add code to pick up yellow sample
            safeWaitSeconds(1);
            telemetry.addLine("Pick up yellow sample");
            telemetry.update();

            //Move robot to net zone
            Actions.runBlocking(
                    drive.actionBuilder(yellowSampleTwo)
                            .strafeToLinearHeading(netZone.position, netZone.heading)
                            .build());
            safeWaitSeconds(1);
            telemetry.addLine("Move robot to net zone");
            telemetry.update();

            //Add code to drop sample in bucket
            safeWaitSeconds(1);
            telemetry.addLine("Drop sample in bucket");
            telemetry.update();

            //Move robot to submersible parking
            Actions.runBlocking(
                    drive.actionBuilder(netZone)
                            .strafeToLinearHeading(preSubmersiblePark.position, preSubmersiblePark.heading)
                            .build());
            safeWaitSeconds(1);
            telemetry.addLine("Move robot to preSubmersible parking");
            telemetry.update();
            Actions.runBlocking(
                    drive.actionBuilder(preSubmersiblePark)
                            .strafeToLinearHeading(submersiblePark.position, submersiblePark.heading)
                            .build());
            safeWaitSeconds(1);
            telemetry.addLine("hitting bottom rung");
            telemetry.update();

            this.m_ascentArmSubsystem.ascentArm(0.6);
            //add code to hit bottom rung


        } else { // RIGHT

            //Move robot with preloaded specimen to submersible to place specimen
            Actions.runBlocking(
                    drive.actionBuilder(drive.pose)
                            .strafeTo(submersibleSpecimen.position)
                            .build());
            safeWaitSeconds(1);
            telemetry.addLine("Move robot to submersible to place specimen");
            telemetry.update();
            //add code to hang specimen
            safeWaitSeconds(1);
            telemetry.addLine("Hang specimen");
            telemetry.update();



            //Move robot to color sample 1 (pushing - can change for your liking)
            Actions.runBlocking(
                    drive.actionBuilder(submersibleSpecimen)
                            .strafeToLinearHeading(preColorSampleOne.position, preColorSampleOne.heading)
                            .build());
            safeWaitSeconds(1);
            telemetry.addLine("Move robot to preColor Sample");
            telemetry.update();
            Actions.runBlocking(
                    drive.actionBuilder(preColorSampleOne)
                            .strafeToLinearHeading(prePushColorSampleOne.position, prePushColorSampleOne.heading)
                            .build());
            safeWaitSeconds(1);
            telemetry.addLine("Move robot to preColor Sample");
            telemetry.update();
            Actions.runBlocking(
                    drive.actionBuilder(prePushColorSampleOne)
                            .strafeToLinearHeading(colorSampleOne.position, colorSampleOne.heading)
                            .build());
            safeWaitSeconds(1);
            telemetry.addLine("Move robot to Color Sample");
            telemetry.update();

            Actions.runBlocking(
                    drive.actionBuilder(colorSampleOne)
                            .strafeToLinearHeading(observationZone.position, observationZone.heading)
                            .build());
            safeWaitSeconds(1);
            telemetry.addLine("Move robot and pushing sample to observation zone");
            telemetry.update();

            //Move robot to pickup specimen
            Actions.runBlocking(
                    drive.actionBuilder(observationZone)
                            .strafeToLinearHeading(specimenPickup.position, specimenPickup.heading)
                            .build());
            safeWaitSeconds(1);
            telemetry.addLine("Move robot specimen pickup ");
            telemetry.update();
            //add code to pick up specimen
            safeWaitSeconds(1);
            telemetry.addLine("Pick up specimen");
            telemetry.update();

            //Move robot to  submersible specimen
            Actions.runBlocking(
                    drive.actionBuilder(specimenPickup)
                            .strafeToLinearHeading(submersibleSpecimen.position, submersibleSpecimen.heading)
                            .build());
            safeWaitSeconds(1);
            telemetry.addLine("Move robot to submersible specimen");
            telemetry.update();
            //add code to hang specimen
            safeWaitSeconds(1);
            telemetry.addLine("Hang specimen");
            telemetry.update();

            //Move robot to pickup specimen
            Actions.runBlocking(
                    drive.actionBuilder(submersibleSpecimen)
                            .strafeToLinearHeading(specimenPickup.position, specimenPickup.heading)
                            .build());
            safeWaitSeconds(1);
            telemetry.addLine("Move robot specimen pickup ");
            telemetry.update();
            //add code to pick up specimen
            safeWaitSeconds(1);
            telemetry.addLine("Pick up specimen");
            telemetry.update();



            //Move robot to submersible
            Actions.runBlocking(
                    drive.actionBuilder(specimenPickup)
                            .strafeToLinearHeading(submersibleSpecimen.position, submersibleSpecimen.heading)
                            .build());
            safeWaitSeconds(1);
            telemetry.addLine("Move robot to submersible");
            telemetry.update();

            //add code to hang specimen
            safeWaitSeconds(1);
            telemetry.addLine("Hang specimen");
            telemetry.update();


            //Move robot to observation parking
            Actions.runBlocking(
                    drive.actionBuilder(submersibleSpecimen)
                            .strafeToLinearHeading(observationPark.position, observationPark.heading)
                            .build());
            safeWaitSeconds(1);
            telemetry.addLine("Move robot to observation parking");
            telemetry.update();
        }

    }

    //method to wait safely with stop button working if needed. Use this instead of sleep
    public void safeWaitSeconds(double time) {
        ElapsedTime timer = new ElapsedTime(SECONDS);
        timer.reset();
        while (!isStopRequested() && timer.time() < time) {
        }
    }
}   // end class
