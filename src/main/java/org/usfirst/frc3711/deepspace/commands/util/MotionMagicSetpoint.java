package org.usfirst.frc3711.deepspace.commands.util;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.EventImportance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import org.usfirst.frc3711.deepspace.Robot;
import org.usfirst.frc3711.deepspace.subsystems.TalonSubsystem;


public class MotionMagicSetpoint extends Command {

  protected final TalonSubsystem subsystem;
  private double setpoint;

  private long startTime;
  private long motionCompleTime=-1;
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
  public double getTimeSinceMotionComplete(){
    long now = System.currentTimeMillis();
    return (now - startTime) / 1000.0;
  }

  public double getMotionProgress(){
    double totalDistance = setpoint - initialPosition;
    double trajPos = subsystem.getActiveTrajectoryPosition();
    return (trajPos - initialPosition) / totalDistance;
  }
  public boolean isMotionFinished(){
    return Math.abs(setpoint - subsystem.getActiveTrajectoryPosition()) < 1.0;
  }

  public boolean isMotionFinished2(){
    double closedLoopTarget = subsystem.getClosedLoopTarget();
    double trajPos = subsystem.getActiveTrajectoryPosition();
    return Math.abs(closedLoopTarget - trajPos) < 1.0;
  }


  @Override
  protected void initialize() {
    super.initialize();
    initialPosition = subsystem.getSelectedSensorPosition();
    startTime = System.currentTimeMillis();
    subsystem.selectProfileSlot(0,0);
    if(Robot.debug)
      Shuffleboard.addEventMarker("MM_"+getName() + "_Init", EventImportance.kNormal);

  }

  @Override
  protected void execute() {
    subsystem.selectProfileSlot(0,0);
    subsystem.set(ControlMode.MotionMagic,setpoint);
    if(isMotionFinished2() && motionCompleTime==-1){
      motionCompleTime = System.currentTimeMillis();
    }
  }

  @Override
  protected void end() {
    if(Robot.debug)
      Shuffleboard.addEventMarker("MM_"+getName() + "_End", EventImportance.kNormal);
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut() || isCanceled() || isMotionFinished2();
  }

  public static class ArmSetpoint extends MotionMagicSetpoint {

    public ArmSetpoint(String name, double setpoint){
      super(name, Robot.arm,setpoint);
    }

    @Override
    protected boolean isFinished() {
      return super.isFinished() && getTimeSinceMotionComplete() > 0.5;
    }
  }


}
