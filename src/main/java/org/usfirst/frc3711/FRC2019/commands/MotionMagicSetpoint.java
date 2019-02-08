package org.usfirst.frc3711.FRC2019.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.SendableImpl;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.usfirst.frc3711.FRC2019.subsystems.TalonSubsystem;

public class MotionMagicSetpoint extends Command {

//  public static class Wrapper extends SendableImpl {
//    public final MotionMagicSetpoint command;
//
//    public Wrapper(TalonSubsystem subsystem){
//
//      this.command = new MotionMagicSetpoint(subsystem);
//    }
//
//    @Override
//    public void initSendable(SendableBuilder builder) {
//     command.initSendable(builder); //TODO????
//      builder.addDoubleProperty("setpoint", command::getSetpoint,command::setSetpoint);
//    }
//  }

  private final TalonSubsystem subsystem;
  private double setpoint;

  public MotionMagicSetpoint(String name,TalonSubsystem subsystem, double setpoint,double timeout) {
    super(subsystem.getName()+":"+name,timeout,subsystem);
    this.subsystem = subsystem;
    this.setpoint = setpoint;
    requires(subsystem);


   // this.setpoint = subsystem.talon.getSelectedSensorPosition();

  }

//  @Override
//  protected void initialize() {
//      subsystem.configMotionMagicClosedLoop();
//  }

  public void setSetpoint(double setpoint){
    this.setpoint = setpoint;
    execute();
  }

  public double getSetpoint(){
    return setpoint;
  }

  @Override
  protected void execute() {
    subsystem.talon.selectProfileSlot(0,0);
    subsystem.talon.set(ControlMode.MotionMagic,setpoint);
  }

  @Override
  protected void end() {
    subsystem.talon.neutralOutput();
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }


}
