package teamCode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import teamCode.subsystems.DriveSubsystem;

public class DriveSpeedCommand extends CommandBase
{
    public DriveSubsystem m_driveSubsystem;
    private static final double m_fullSpeed = 1.0;
    private static final double m_slow = 0.7;
    public double m_turbo;

    public DriveSpeedCommand(DriveSubsystem driveSubsystem)
    {
        m_turbo = m_slow;
        this.m_driveSubsystem = driveSubsystem;
        addRequirements(this.m_driveSubsystem);
    }

    @Override
    public void initialize()
    {
    }
    @Override
    public void execute()
    {
        if (m_turbo == m_fullSpeed)
        {
            m_turbo = m_slow;
        }
        else if (m_turbo == m_slow)
        {
            m_turbo = m_fullSpeed;
        }

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