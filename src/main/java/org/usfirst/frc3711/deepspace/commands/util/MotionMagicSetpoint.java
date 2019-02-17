package org.usfirst.frc3711.deepspace.commands.util;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.EventImportance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import org.usfirst.frc3711.deepspace.subsystems.TalonSubsystem;


public class MotionMagicSetpoint extends Command {

  protected final TalonSubsystem subsystem;
  private double setpoint;

  private long startTime;
  private double initialPosition;

  public MotionMagicSetpoint(String name, TalonSubsystem subsystem, double setpoint) {
    super(subsystem.getName() + ":" + name, subsystem);
    this.subsystem = subsystem;
    this.setpoint = setpoint;
    requires(subsystem);
  }

  public MotionMagicSetpoint(String name, TalonSubsystem subsystem, double setpoint, double timeout) {
    super(subsystem.getName() + ":" + name, timeout, subsystem);
    this.subsystem = subsystem;
    this.setpoint = setpoint;
    requires(subsystem);
  }

  public void setSetpoint(double setpoint) {
    this.setpoint = setpoint;
    //execute();
  }

  public double getSetpoint() {
    return setpoint;
  }

  public double getElapsedTime(){
    long now = System.currentTimeMillis();
    return (now - startTime) / 1000.0;
  }

  public double getMotionProgress(){
    double totalDistance = setpoint - initialPosition;
    double trajPos = subsystem.talon.getActiveTrajectoryPosition();
    return (trajPos - initialPosition) / totalDistance;
  }
  public boolean isMotionFinished(){
    return Math.abs(setpoint - subsystem.talon.getActiveTrajectoryPosition()) < 1.0;
  }

  public boolean isMotionFinished2(){
    double closedLoopTarget = subsystem.talon.getClosedLoopTarget();
    double trajPos = subsystem.talon.getActiveTrajectoryPosition();
    return Math.abs(closedLoopTarget - trajPos) < 1.0;
  }


  @Override
  protected void initialize() {
    super.initialize();
    initialPosition = subsystem.talon.getSelectedSensorPosition();
    startTime = System.currentTimeMillis();
    subsystem.talon.selectProfileSlot(0, 0);
    Shuffleboard.addEventMarker("MM_"+getName() + "_Init", EventImportance.kNormal);

  }

  @Override
  protected void execute() {
    subsystem.talon.selectProfileSlot(0, 0);
    subsystem.talon.set(ControlMode.MotionMagic, setpoint);
  }

  @Override
  protected void end() {
    Shuffleboard.addEventMarker("MM_"+getName() + "_End", EventImportance.kNormal);
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut() || isCanceled() || isMotionFinished2();
  }


}
