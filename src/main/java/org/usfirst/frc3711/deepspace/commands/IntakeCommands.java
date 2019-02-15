package org.usfirst.frc3711.deepspace.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc3711.deepspace.Robot;

public class IntakeCommands {
  public static Command intake() {
    return new RunIntake(true);
  }

  public static Command eject() {
    return new RunIntake(false);
  }

  static class RunIntake extends Command {
    final boolean forward;

    public RunIntake(boolean forward) {
      requires(Robot.intake);
      this.forward = forward;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
      Robot.intake.run(forward);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
      return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
      Robot.intake.stop();
    }


  }
}
