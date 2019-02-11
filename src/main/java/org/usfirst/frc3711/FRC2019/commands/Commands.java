package org.usfirst.frc3711.FRC2019.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc3711.FRC2019.subsystems.TalonSubsystem;

public class Commands {
  public abstract static class AbstractCommand extends Command {
    public AbstractCommand() {
    }

    public AbstractCommand(String name) {
      super(name);
    }

    public AbstractCommand(double timeout) {
      super(timeout);
    }

    public AbstractCommand(Subsystem subsystem) {
      super(subsystem);
    }

    public AbstractCommand(String name, Subsystem subsystem) {
      super(name, subsystem);
    }

    public AbstractCommand(double timeout, Subsystem subsystem) {
      super(timeout, subsystem);
    }

    public AbstractCommand(String name, double timeout) {
      super(name, timeout);
    }

    public AbstractCommand(String name, double timeout, Subsystem subsystem) {
      super(name, timeout, subsystem);
    }

    @Override
    protected boolean isFinished() {
      return isTimedOut() || isCanceled();
    }
  }
  public static class TalonSubsystemCommand extends AbstractCommand {
    protected final TalonSubsystem subsystem;
    public TalonSubsystemCommand(String name,TalonSubsystem subsystem, double timeout){
      super(subsystem.getName()+":"+name,timeout,subsystem);
      this.subsystem = subsystem;
    }
    public TalonSubsystemCommand(String name,TalonSubsystem subsystem){
      super(subsystem.getName()+":"+name,subsystem);
      this.subsystem = subsystem;
    }
  }

  public static Command setpointCommand(final String name,final TalonSubsystem subsystem,final double setpoint,final ControlMode mode){
    return new SetpointCommand(name, subsystem, setpoint, mode);
  }
  public static Command positionSetpointCommand(String name,TalonSubsystem subsystem,double setpoint){
    return new SetpointCommand(name,subsystem,setpoint,ControlMode.Position);
  }
  public static Command motionMagicSetpointSetpointCommand(String name,TalonSubsystem subsystem,double setpoint){
    return new SetpointCommand(name,subsystem,setpoint,ControlMode.MotionMagic);
  }

   static class SetpointCommand extends TalonSubsystemCommand {
      final double setpoint;
      final ControlMode mode;

      public SetpointCommand(String name, TalonSubsystem subsystem,double setpoint,ControlMode mode){
          super(name,subsystem);
          this.setpoint = setpoint;
          this.mode = mode;
      }

      @Override
      protected void initialize() {
         if(ControlMode.MotionMagic.equals(mode)){
             subsystem.talon.selectProfileSlot(0,0);
         }
         else if(ControlMode.Position.equals(mode)){
             subsystem.talon.selectProfileSlot(1,0);
         }
      }
      @Override
      protected void execute() {
         subsystem.talon.set(mode, setpoint);
      }


  }
}
