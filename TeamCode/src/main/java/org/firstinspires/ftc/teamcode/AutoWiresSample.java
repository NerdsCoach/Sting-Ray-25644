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

//package org.firstinspires.ftc.teamcode;
//
//import static com.qualcomm.robotcore.util.ElapsedTime.Resolution.SECONDS;
//import static teamCode.Constants.LiftArmConstants.kLiftArmCloseSample;
//import static teamCode.Constants.LiftArmConstants.kLiftArmHighBasket;
//import static teamCode.Constants.LiftArmConstants.kLiftArmIntakeReset;
//import static teamCode.Constants.PivotIntakeConstants.kIntakePivotPickUp;
//import static teamCode.Constants.PivotIntakeConstants.kIntakePivotScore;
//import static teamCode.Constants.SlideArmConstants.kSlideArmCloseSample;
//import static teamCode.Constants.SlideArmConstants.kSlideArmHighBasket;
//
//import com.acmerobotics.roadrunner.Pose2d;
//import com.acmerobotics.roadrunner.ftc.Actions;
//import com.arcrobotics.ftclib.hardware.motors.CRServo;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.TouchSensor;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//import teamCode.autoSubsystems.AutoDriveSubsystem;
//import teamCode.commands.ArmIntakeResetCommand;
//import teamCode.commands.ArmPositionCloseSampleCommand;
//import teamCode.commands.ArmPositionHighBasketCommand;
//import teamCode.commands.ArmPositionHighChamberCommand;
//import teamCode.commands.ArmPositionTravelCommand;
//import teamCode.commands.IntakePivotCommand;
//import teamCode.commands.ScoreSpecimenCommand;
//import teamCode.commands.SlideFudgeInCommand;
//import teamCode.commands.StingrayArmCommand;
//import teamCode.subsystems.IntakePivotSubsystem;
//import teamCode.subsystems.IntakeWheelSubsystem;
//import teamCode.subsystems.LiftArmSubsystem;
//import teamCode.subsystems.SlideArmSubsystem;
//import teamCode.subsystems.StingRayArmSubsystem;
//

import static com.qualcomm.robotcore.util.ElapsedTime.Resolution.SECONDS;

import com.qualcomm.robotcore.util.ElapsedTime;

///**
// * FTC WIRES Autonomous Example for only vision detection using tensorflow and park
// */
//@Autonomous(name = "StingRayAutoSamples", group = "00-Autonomous", preselectTeleOp = "FTC Wires TeleOp")
//public class AutoWiresSample extends LinearOpMode
//{
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
//    private ArmPositionHighChamberCommand m_armPositionHighChamberCommand;
//    private ArmPositionTravelCommand m_armPositionHomeCommand;
//    private ArmPositionCloseSampleCommand m_armPositionCloseSampleCommand;
//    private ArmIntakeResetCommand m_armIntakeResetCommand;
//    private IntakePivotCommand m_intakePivotCommand;
//    private StingrayArmCommand m_ascentArmCommand;
//    private SlideFudgeInCommand m_slideFudgeInCommand;
//    private ScoreSpecimenCommand m_scoreSpecimenCommand;
//    private TouchSensor m_touch;
//
//    public static String TEAM_NAME = "Nerds On A Mission"; //TODO: Enter team Name
//    public static int TEAM_NUMBER = 25644; //TODO: Enter team Number
//
//    @Override
//    public void runOpMode() throws InterruptedException
//    {
//        this.m_liftArmMotor = hardwareMap.get(DcMotor.class, "liftArmMotor");
//        this.m_slideArmMotor = hardwareMap.get(DcMotor.class, "slideArmMotor");
//
//        this.m_intakeWheelServo = new CRServo(hardwareMap, "intakeWheelServo");
//        this.m_intakePivotSubsystem = new IntakePivotSubsystem(hardwareMap, "intakePivotServo");
//        this.m_ascentArmSubsystem = new StingRayArmSubsystem(hardwareMap, "ascentArmServo");
//
//        this.m_liftArmSubsystem = new LiftArmSubsystem(this.m_liftArmMotor);
//        this.m_slideArmSubsystem = new SlideArmSubsystem(this.m_slideArmMotor);
//        this.m_intakeWheelSubsystem = new IntakeWheelSubsystem(this.m_intakeWheelServo,this.m_touch);
//
//        this.m_armPositionHomeCommand = new ArmPositionTravelCommand(this.m_liftArmSubsystem, this.m_slideArmSubsystem, this.m_intakePivotSubsystem);
//        this.m_armPositionCloseSampleCommand = new ArmPositionCloseSampleCommand(this.m_liftArmSubsystem, this.m_slideArmSubsystem, this.m_intakePivotSubsystem);
//        this.m_armPositionHighBasketCommand = new ArmPositionHighBasketCommand(this.m_liftArmSubsystem, this.m_slideArmSubsystem, this.m_intakePivotSubsystem);
//        this.m_armPositionHighChamberCommand = new ArmPositionHighChamberCommand(this.m_liftArmSubsystem,this.m_slideArmSubsystem, this.m_intakePivotSubsystem);
//        this.m_intakePivotCommand = new IntakePivotCommand(this.m_intakePivotSubsystem);
//        this.m_ascentArmCommand = new StingrayArmCommand(this.m_ascentArmSubsystem);
//        //this.m_armFudgeFactorUpCommand = new ArmFudgeFactorUpCommand(this.m_liftArmSubsystem);
//        this.m_scoreSpecimenCommand = new ScoreSpecimenCommand(this.m_liftArmSubsystem);
//
//        waitForStart();
//            runAutonoumousMode();
//    }
//
//
//    public void runAutonoumousMode()
//    {
//        //Auto Left Positions - Samples
//
//        Pose2d initPose = new Pose2d(0,-6, Math.toRadians(0)); // Starting Pose
//        Pose2d netZone = new Pose2d(10,16,Math.toRadians(-45));//8 //-50
//        Pose2d netZoneTwo = new Pose2d(8,16,Math.toRadians(-40));
//        Pose2d yellowSampleOne = new Pose2d(21,9, Math.toRadians(-2));//(18,12,Math.toRadians(-14)
//        Pose2d sampleFudgeDrive = new Pose2d(25, 9, Math.toRadians(-2));
//        Pose2d yellowSampleTwo = new Pose2d(20,20,Math.toRadians(1));
//        Pose2d sampleTwoFudgeDrive = new Pose2d(29,20,Math.toRadians(1));
//        Pose2d preSubmersiblePark = new Pose2d(53,11,Math.toRadians(90));
//        Pose2d submersiblePark = new Pose2d(53,-23,Math.toRadians(90));
//
//        //Auto Right Positions - Specimens
//        Pose2d submersibleSpecimen = new Pose2d(28,-1,Math.toRadians(-180));//moved from above
//        Pose2d secondSubmersibleSpecimen = new Pose2d(28,5,Math.toRadians(-180));
//        Pose2d observationZone = new Pose2d(2,-37,Math.toRadians(-90));
//        Pose2d specimenPrePickup = new Pose2d(6,-25,Math.toRadians(-90));
//        Pose2d preColorSampleOne = new Pose2d(20,-34,Math.toRadians(-90));
//        Pose2d prePushColorSampleOne = new Pose2d(51,-34,Math.toRadians(-90));
//        Pose2d colorSampleOne = new Pose2d(51,-40,Math.toRadians(-90));
//        Pose2d observationPark = new Pose2d(5,-40,Math.toRadians(-90));
//        Pose2d scoreSpecimen = new Pose2d(20, -1, Math.toRadians(-180));
//        Pose2d pickUpSpecimen = new Pose2d(6,-50, Math.toRadians(-90));
//        Pose2d specimenFudgeDrive = new Pose2d(6, -51,Math.toRadians(-90));
//
//        double waitSecondsBeforeDrop = 0;
//        MecanumDrive drive = new MecanumDrive(hardwareMap, initPose);
//
//        {
//            this.m_liftArmSubsystem.liftArm(kLiftArmIntakeReset);
//            safeWaitSeconds(.5);
//
//            //Move robot to netZone with preloaded sample ready to drop in basket
//            Actions.runBlocking(
//                    drive.actionBuilder(initPose)
//                            .strafeToLinearHeading(netZone.position, netZone.heading)
//                            .build());
//
//            telemetry.addLine("Move robot to netZone");
//            telemetry.update();
//
//            this.m_liftArmSubsystem.liftArm(kLiftArmHighBasket);
//            safeWaitSeconds(.5);
//            this.m_slideArmSubsystem.slideArm(kSlideArmHighBasket);
//            safeWaitSeconds(.75);
//            this.m_intakePivotSubsystem.pivotIntake(kIntakePivotScore);
//
//            //Add code to drop sample in basket
//            safeWaitSeconds(.5);
//            this.m_intakeWheelSubsystem.spinIntake(0.5);//Score in High Basket #1
//
//            safeWaitSeconds(1);
//            this.m_intakeWheelSubsystem.spinIntake(0.0);
//
//            telemetry.addLine("Drop sample in basket");
//            telemetry.update();
//
//            this.m_intakePivotSubsystem.pivotIntake(kIntakePivotPickUp);
//            safeWaitSeconds(.5);
//            this.m_slideArmSubsystem.slideArm(kSlideArmCloseSample);
//
//            safeWaitSeconds(.5);
//            this.m_intakeWheelSubsystem.spinIntake(-0.5);//Pick Up Floor #1
//
//            this.m_liftArmSubsystem.liftArm(kLiftArmCloseSample);
//            safeWaitSeconds(.5);
//
//            //Move robot to pick yellow sample one
//            Actions.runBlocking(
//                    drive.actionBuilder(netZone)
//                            .strafeToLinearHeading(yellowSampleOne.position, yellowSampleOne.heading)
//                            .build());
//            safeWaitSeconds(.75);
//            telemetry.addLine("Move robot to pick yellow sample one");
//            telemetry.update();
//
//            this.m_slideArmSubsystem.autoSlideArm(200);
//            safeWaitSeconds(.1);
//
//            Actions.runBlocking(
//                    drive.actionBuilder(yellowSampleOne)
//                            .strafeToLinearHeading(sampleFudgeDrive.position, sampleFudgeDrive.heading)
//                            .build());
//            safeWaitSeconds(.5);//
//            telemetry.addLine("Fudge robot to pick yellow sample one");
//            telemetry.update();
//
//            this.m_intakeWheelSubsystem.spinIntake(0.0);
//            //Add code to pick up yellow sample
//            telemetry.addLine("Pick up yellow sample");
//            telemetry.update();
//
//            this.m_intakeWheelSubsystem.spinIntake(-0.5);
//            safeWaitSeconds(0.2);
//            this.m_intakeWheelSubsystem.spinIntake(0.0);
//
//
//            this.m_liftArmSubsystem.liftArm(kLiftArmIntakeReset);
//            safeWaitSeconds(.5);
//
//            //Move robot to net zone to drop sample
//            Actions.runBlocking(
//                    drive.actionBuilder(yellowSampleOne)
//                            .strafeToLinearHeading(netZoneTwo.position, netZoneTwo.heading)
//                            .build());
//            telemetry.addLine("Move robot to net zone to drop sample");
//            telemetry.update();
//
//            this.m_liftArmSubsystem.liftArm(kLiftArmHighBasket);
//            safeWaitSeconds(.5);//
//            this.m_slideArmSubsystem.slideArm(kSlideArmHighBasket);
//            safeWaitSeconds(.5);
//            this.m_intakePivotSubsystem.pivotIntake(kIntakePivotScore);
//            //Add code to drop sample in bucket
//            safeWaitSeconds(.5);//
//            this.m_intakeWheelSubsystem.spinIntake(0.5);//Score in High Basket #2
//
//            safeWaitSeconds(1);
//            this.m_intakeWheelSubsystem.spinIntake(0.0);
//
//
//            telemetry.addLine("Drop sample in bucket");
//            telemetry.update();
//
//            this.m_intakePivotSubsystem.pivotIntake(kIntakePivotPickUp);
//            safeWaitSeconds(.5);
//            this.m_slideArmSubsystem.slideArm(kSlideArmCloseSample);
//
//            safeWaitSeconds(.5);
//            this.m_intakeWheelSubsystem.spinIntake(-0.5);//Pick Up Floor #2
//
//            this.m_liftArmSubsystem.liftArm(kLiftArmCloseSample);
//            safeWaitSeconds(.5);
//
//
//            //Move robot to yellow sample two
//            Actions.runBlocking(
//                    drive.actionBuilder(netZone)
//                            .strafeToLinearHeading(yellowSampleTwo.position, yellowSampleTwo.heading)
//                            .build());
//            safeWaitSeconds(1);
//            telemetry.addLine("Move robot to yellow sample two");
//            telemetry.update();
//
//            this.m_slideArmSubsystem.autoSlideArm(200);
//            safeWaitSeconds(.1);
//
//            Actions.runBlocking(
//                    drive.actionBuilder(yellowSampleTwo)
//                            .strafeToLinearHeading(sampleTwoFudgeDrive.position, sampleTwoFudgeDrive.heading)
//                            .build());
//            safeWaitSeconds(0.5);//
//            telemetry.addLine("Fudge robot to pick yellow sample one");
//            telemetry.update();
//
//            this.m_intakeWheelSubsystem.spinIntake(0.0);
//
//            //Add code to pick up yellow sample
//            telemetry.addLine("Pick up yellow sample");
//            telemetry.update();
//
//            this.m_liftArmSubsystem.liftArm(kLiftArmIntakeReset);
//            safeWaitSeconds(.5);
//
//            //Move robot to net zone
//            Actions.runBlocking(
//                    drive.actionBuilder(yellowSampleTwo)
//                            .strafeToLinearHeading(netZoneTwo.position, netZoneTwo.heading)
//                            .build());
//
//            telemetry.addLine("Move robot to net zone");
//            telemetry.update();
//
//            this.m_liftArmSubsystem.liftArm(kLiftArmHighBasket);
//            safeWaitSeconds(.5);
//            this.m_slideArmSubsystem.slideArm(kSlideArmHighBasket);
//            safeWaitSeconds(.75);
//            this.m_intakePivotSubsystem.pivotIntake(kIntakePivotScore);
//            //Add code to drop sample in bucket
//            safeWaitSeconds(.5);
//            this.m_intakeWheelSubsystem.spinIntake(0.5);//Spit out
//
//            safeWaitSeconds(1);
//            this.m_intakeWheelSubsystem.spinIntake(0.0);
//
//            //Add code to drop sample in bucket
//            telemetry.addLine("Drop sample in bucket");
//            telemetry.update();
//
//            this.m_slideArmSubsystem.slideArm(kSlideArmCloseSample);
//
//            this.m_liftArmSubsystem.liftArm(kLiftArmCloseSample);
//            safeWaitSeconds(.65);
//
//
//            safeWaitSeconds(.75);
//            //Move robot to submersible parking
//            Actions.runBlocking(
//                    drive.actionBuilder(netZone)
//                            .strafeToLinearHeading(preSubmersiblePark.position, preSubmersiblePark.heading)
//                            .build());
//            telemetry.addLine("Move robot to preSubmersible parking");
//            telemetry.update();
//
//            this.m_ascentArmSubsystem.ascentArm(0.6);
//
//            Actions.runBlocking(
//                    drive.actionBuilder(preSubmersiblePark)
//                            .strafeToLinearHeading(submersiblePark.position, submersiblePark.heading)
//                            .build());
//            telemetry.addLine("hitting bottom rung");
//            telemetry.update();
//        }
//    }
//
//    //method to wait safely with stop button working if needed. Use this instead of sleep
//    public void safeWaitSeconds(double time)
//    {
//        ElapsedTime timer = new ElapsedTime(SECONDS);
//        timer.reset();
//        while (!isStopRequested() && timer.time() < time) {
//        }
//    }
//}   // end class
