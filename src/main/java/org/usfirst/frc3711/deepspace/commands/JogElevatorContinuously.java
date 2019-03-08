package org.usfirst.frc3711.deepspace.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import org.usfirst.frc3711.deepspace.Robot;
import org.usfirst.frc3711.deepspace.commands.util.TalonSubsystemCommand;

public class JogElevatorContinuously extends TalonSubsystemCommand {
  private final boolean up;
  private static final int UP_SETPOINT = 8000;
  private static final int DOWN_SETPOINT = 0;//-8000;
  private static final int STOP_DISTANCE = 600;
  private int position;

  public JogElevatorContinuously(boolean up) {
    super("JogElevatorContinuously" + (up ? "Up" : "Down"), Robot.elevator);
    this.up = up;
  }

  @Override
  protected void initialize() {
    super.initialize();
    subsystem.talon.selectProfileSlot(0, 0); //MM slot

    subsystem.talon.set(ControlMode.MotionMagic, up ? UP_SETPOINT : DOWN_SETPOINT);
  }

  @Override
  protected void execute() {
    super.execute();
    position = subsystem.talon.getSelectedSensorPosition();

  }

  @Override
  protected void end() {
    super.end();
    subsystem.talon.selectProfileSlot(1, 0); //position slot
    subsystem.talon.set(ControlMode.Position, position);

  }

  @Override
  protected boolean isFinished() {
    int position = subsystem.talon.getSelectedSensorPosition();
    return super.isFinished()
           || (up && position >= UP_SETPOINT - STOP_DISTANCE)
           || (!up && position <= DOWN_SETPOINT + STOP_DISTANCE);
  }
}
