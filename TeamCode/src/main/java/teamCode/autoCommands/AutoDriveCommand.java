//package teamCode.autoCommands;
//
//import com.acmerobotics.roadrunner.Pose2d;
//import com.arcrobotics.ftclib.command.CommandBase;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//
////import org.firstinspires.ftc.teamcode.MecanumDrive;
//
//import teamCode.Logic;
//import teamCode.autoSubsystems.AutoDriveSubsystem;
//public class AutoDriveCommand extends CommandBase
//{
//    public AutoDriveSubsystem m_autoDriveSubsystem;
//    public int leftFront;
//    public int rightFront;
//    public int leftBack;
//    public int rightBack;
//    public AutoDriveCommand(AutoDriveSubsystem autoDriveSubsystem, int fL, int fR, int bL, int bR)
//    {
//        this.m_autoDriveSubsystem = autoDriveSubsystem;
//        this.leftFront = fL;
//        this.rightFront = fR;
//        this.leftBack = bL;
//        this.rightBack = bR;
//        addRequirements(this.m_autoDriveSubsystem);
//    }
//
//    @Override
//    public void initialize()
//    {
//    }
//
//    @Override
//    public void execute()
//    {
//        this.m_autoDriveSubsystem.driveRobot(this.leftFront, this.rightFront, this.leftBack, this.rightBack);
//    }
//
//    @Override
//    public void end(boolean interrupted)
//    {
////       this.m_autoDriveSubsystem.stop();
//    }
//
//    @Override
//    public boolean isFinished()
//    {
//        return Logic.OpModeType.opMode.equals("Sting-Ray Auto")
//                && this.m_autoDriveSubsystem.atTarget(this.leftFront);
//    }
//
//}