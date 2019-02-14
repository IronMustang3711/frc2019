package org.usfirst.frc3711.FRC2019.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc3711.FRC2019.Robot;

public class EjectorCommands {

  public static Command hookHatchpanelCommand() {
    final double hook_setpoint = 100;
    return new Commands.SetpointCommand("HookHatchpanel", Robot.ejector, hook_setpoint, ControlMode.Position) {

      @Override
      protected boolean isFinished() {
        return super.isFinished() || Math.abs(subsystem.talon.getClosedLoopError()) < 10;
      }
    }.withTimeout(2.0);
  }

  public static Command ejectHatchPanelCommand() {
    final double eject_setpoint = -100;
    double currentPosition = Robot.ejector.talon.getSelectedSensorPosition();

    return new Commands.SetpointCommand("EjectorToHome", Robot.ejector, eject_setpoint, ControlMode.Position) {
      @Override
      protected boolean isFinished() {
        return super.isFinished() || Math.abs(subsystem.talon.getClosedLoopError()) < 10;
      }
    }.withTimeout(2.0);

  }

  public static Command ejectorToHome() {
    return new Commands.SetpointCommand("EjectorToHome", Robot.ejector, 0, ControlMode.Position) {
      @Override
      protected boolean isFinished() {
        return super.isFinished() || Math.abs(subsystem.talon.getClosedLoopError()) < 10;
      }
    }.withTimeout(2.0);
  }

  public static class RunEjector extends Command {
    public RunEjector() {
      requires(Robot.ejector);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
      Robot.ejector.run();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
      return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
      Robot.ejector.stop();
    }


  }
}
