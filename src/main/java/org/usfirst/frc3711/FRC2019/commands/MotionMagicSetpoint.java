package org.usfirst.frc3711.FRC2019.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.SendableImpl;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.usfirst.frc3711.FRC2019.subsystems.TalonSubsystem;

public class MotionMagicSetpoint extends Command {

  public static class Wrapper extends SendableImpl {
    public final MotionMagicSetpoint command;

    public Wrapper(TalonSubsystem subsystem){
      this.command = new MotionMagicSetpoint(subsystem);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
     command.initSendable(builder); //TODO????


    }
  }

  private final TalonSubsystem subsystem;
  private double setpoint;

  public MotionMagicSetpoint(TalonSubsystem subsystem) {
    super(subsystem.getName()+"MotionMagicSetpoint");
    this.subsystem = subsystem;
    requires(subsystem);
  }

  @Override
  protected void initialize() {

    this.setpoint = subsystem.talon.getSelectedSensorPosition();

   subsystem.talon.config_kP(0,0,50);
   subsystem.talon.config_kI(0,0,50);
   subsystem.talon.config_IntegralZone(0,0,50);
   subsystem.talon.config_kD(0,0,50);


   subsystem.talon.configMotionCruiseVelocity(100);
   subsystem.talon.configMotionAcceleration(100);
  }

  public void setSetpoint(double setpoint){
    this.setpoint = setpoint;
  }

  @Override
  protected void execute() {
    subsystem.talon.set(ControlMode.MotionMagic,setpoint);
  }

  @Override
  protected void end() {
    subsystem.talon.neutralOutput();
  }

  @Override
  protected boolean isFinished() {
    return false;
  }


}
