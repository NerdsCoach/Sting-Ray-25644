package teamCode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.DcMotor;

import teamCode.Constants;
import teamCode.subsystems.IntakePivotSubsystem;
import teamCode.subsystems.LiftArmSubsystem;
import teamCode.subsystems.SlideArmSubsystem;

public class ArmPositionSubmersiblePickUpCommand extends CommandBase
{
    private LiftArmSubsystem m_liftArmSubsystem;
    private SlideArmSubsystem m_slideArmSubsystem;
    private IntakePivotSubsystem m_intakePivotSubsytem;
    private DcMotor m_slideArmMotor;
    private DcMotor m_liftArmMotor;
    private double m_reach;

    public ArmPositionSubmersiblePickUpCommand(LiftArmSubsystem liftArmSubsystem,
                                               SlideArmSubsystem slideArmSubsystem, IntakePivotSubsystem intakePivotSubsystem)
    {
        this.m_liftArmSubsystem = liftArmSubsystem;
        this.m_slideArmSubsystem = slideArmSubsystem;
        this.m_intakePivotSubsytem = intakePivotSubsystem;
        this.m_reach = 1;

        addRequirements(m_liftArmSubsystem, m_slideArmSubsystem, m_intakePivotSubsytem);
    }

    @Override
    public void initialize()
    {

    }

    @Override
    public void execute()
    {
        this.m_liftArmSubsystem.liftArm(325);//Lift arm to 52
//        if (this.m_liftArmSubsystem.atTarget(300))//Lift arm to 52)
//        {
            this.m_intakePivotSubsytem.pivotIntake(0.45);
            this.m_slideArmSubsystem.slideArm(120);//SLide to 12
//        }

//        this.m_liftArmSubsystem.liftArm(Constants.LiftArmConstants.kLiftArmFarSample);
//        if (m_liftArmSubsystem.atTarget(Constants.LiftArmConstants.kLiftArmFarSample))
//        {
//            this.m_slideArmSubsystem.slideArm(Constants.SlideArmConstants.kSlideArmFarSample);
//            this.m_intakePivotSubsytem.pivotIntake(Constants.PivotIntakeConstants.kIntakePivotFarSample);
//        }


//        // from Subsystem
//        public void slideFudgeFactor(int pull)
//        {
//            this.m_slideArmMotor.setTargetPosition(this.m_slideArmMotor.getCurrentPosition() + pull);
//            this.m_slideArmMotor.setPower(0.75);
//            this.m_slideArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        }
        //from Command
//        public void execute()
//        {


//        }



    }

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
