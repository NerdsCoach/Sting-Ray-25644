package teamCode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import teamCode.subsystems.GyroSubsystem;

public class HoldBasketPositionCommand extends CommandBase
{
    public GyroSubsystem m_gyroSubsystem;

    public HoldBasketPositionCommand(GyroSubsystem gyroSubsystem)
    {
        this.m_gyroSubsystem = gyroSubsystem;
        addRequirements(this.m_gyroSubsystem);
    }

    @Override
    public void initialize()
    {

    }

    @Override
    public void execute()
    {
        m_gyroSubsystem.holdBasketPose();
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
