package org.usfirst.frc3711.deepspace.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import org.usfirst.frc3711.deepspace.Robot;
import org.usfirst.frc3711.deepspace.commands.util.TalonSubsystemCommand;

public class JogElevator extends TalonSubsystemCommand {
  private static final double JOG_DISTANCE = 500;

  private final boolean up;

  private double setpoint;

  public JogElevator(boolean up){
    super("JogElevator" + (up ? "Up" : "Down"), Robot.elevator);
    this.up = up;
  }



  @Override
  protected void initialize() {
    super.initialize();
    double initialPosition = subsystem.talon.getSelectedSensorPosition();
    setpoint = up ? initialPosition + JOG_DISTANCE : initialPosition - JOG_DISTANCE;
  }

  @Override
  protected void execute() {
    super.execute();
    subsystem.talon.set(ControlMode.MotionMagic,setpoint);
  }

  @Override
  protected boolean isFinished() {
    return (setpoint - subsystem.talon.getActiveTrajectoryPosition()) == 0;
  }
}
