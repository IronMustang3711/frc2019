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
    double initialPosition = subsystem.getSelectedSensorPosition();
    setpoint = up ? initialPosition + 4 * JOG_DISTANCE : initialPosition - JOG_DISTANCE;
  }

  @Override
  protected void execute() {
    super.execute();
    subsystem.set(ControlMode.MotionMagic,setpoint);
  }

  @Override
  protected boolean isFinished() {
    return Math.abs(setpoint - subsystem.getActiveTrajectoryPosition()) < 2.0 ;
  }
}
