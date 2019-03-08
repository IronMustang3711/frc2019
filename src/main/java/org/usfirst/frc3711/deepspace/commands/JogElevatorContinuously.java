package org.usfirst.frc3711.deepspace.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import org.usfirst.frc3711.deepspace.Robot;
import org.usfirst.frc3711.deepspace.commands.util.TalonSubsystemCommand;

public class JogElevatorContinuously extends TalonSubsystemCommand {
  private final boolean up;
  public JogElevatorContinuously(boolean up){
    super("JogElevatorContinuously" + (up ? "Up" : "Down"), Robot.elevator);
    this.up = up;
  }

  @Override
  protected void execute() {
    super.execute();
    subsystem.talon.set(ControlMode.MotionMagic, up? 10000:-8000);

  }

  @Override
  protected void end() {
    super.end();
    int position = subsystem.talon.getSelectedSensorPosition();
    subsystem.talon.set(ControlMode.MotionMagic, position);
  }

  @Override
  protected boolean isFinished() {
    int position = subsystem.talon.getSelectedSensorPosition();
    return super.isFinished()||(up&&position>=10000)||(!up&&position<=-8000);

  }
}
