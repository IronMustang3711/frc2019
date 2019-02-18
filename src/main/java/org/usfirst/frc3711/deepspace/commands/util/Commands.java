package org.usfirst.frc3711.deepspace.commands.util;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import org.usfirst.frc3711.deepspace.Robot;
import org.usfirst.frc3711.deepspace.subsystems.TalonSubsystem;

import java.util.function.BooleanSupplier;

public class Commands {

  public static Command setpointCommand(final String name, final TalonSubsystem subsystem, final double setpoint, final ControlMode mode) {
    return new SetpointCommand(name, subsystem, setpoint, mode);
  }

  public static Command positionSetpointCommand(String name, TalonSubsystem subsystem, double setpoint) {
    return new SetpointCommand(name, subsystem, setpoint, ControlMode.Position);
  }

//  public static Command motionMagicSetpointSetpointCommand(String name, TalonSubsystem subsystem, double setpoint) {
//    return new SetpointCommand(name, subsystem, setpoint, ControlMode.MotionMagic);
//  }

  static class SetpointCommand extends TalonSubsystemCommand {
    final double setpoint;
    final ControlMode mode;

    SetpointCommand(String name, TalonSubsystem subsystem, double setpoint, ControlMode mode) {
      super(name, subsystem);
      this.setpoint = setpoint;
      this.mode = mode;
    }

    @Override
    protected void initialize() {
      if (ControlMode.MotionMagic.equals(mode)) {
        subsystem.talon.selectProfileSlot(0, 0);
      } else if (ControlMode.Position.equals(mode)) {
        subsystem.talon.selectProfileSlot(1, 0);
      }
    }

    @Override
    protected void execute() {
      subsystem.talon.set(mode, setpoint);
    }
  }

  public static Command enableCurrentLimitCommand(TalonSubsystem subsystem) {
    return new CurrentLimit(subsystem, true);
  }

  public static Command disableCurrentLimitCommand(final TalonSubsystem subsystem) {
    return new CurrentLimit(subsystem, false);
  }

  private static class CurrentLimit extends InstantCommand {
    final TalonSubsystem subsystem;
    final boolean enable;

    CurrentLimit(TalonSubsystem subsystem, boolean enable) {
      super(subsystem.getName() + "CurrentLimit" + enable);
      requires(subsystem);
      this.subsystem = subsystem;
      this.enable = enable;
    }

    @Override
    protected void initialize() {
      if (enable) subsystem.enableCurrentLimiting();
      else subsystem.disableCurrentLimiting();

    }

  }

//  public static Command disableAll() {
//    return new InstantCommand("Disable All") {
//      @Override
//      protected void execute() {
//        Robot.disableAll();
//      }
//    };
//
//  }

//  public static Command disableCommand(final TalonSubsystem subsystem) {
//    return new DisableTalon(subsystem);
//  }

//   static class DisableTalon extends InstantCommand {
//    final TalonSubsystem subsystem;
//
//    DisableTalon(TalonSubsystem subsystem) {
//      super(subsystem.getName() + "Disable");
//      this.subsystem = subsystem;
//      requires(subsystem);
//    }
//
//    @Override
//    protected void initialize() {
//      subsystem.disable();
//    }
//
//  }

  public static Command runWhenTrue(Command c, BooleanSupplier guard){
    return new Command(){
      @Override
      protected boolean isFinished() {
        return guard.getAsBoolean();
      }

      @Override
      protected void end() {
       c.start();
      }
    };
  }

  public static Command delayUntil(String name,BooleanSupplier guard){
    return new Command(name) {
      @Override
      protected boolean isFinished() {
        return guard.getAsBoolean();
      }
    };
  }

  public static Command delayUntil(BooleanSupplier guard){
    return delayUntil("DelayUntil",guard);
  }
  public static Command constantOutput(TalonSubsystem subsystem, double output){
    return new ConstantOutput(subsystem, output);
  }

   static class ConstantOutput extends Command {
    final TalonSubsystem subsystem;
    final double output;

     ConstantOutput(TalonSubsystem subsystem, double output) {
      super(subsystem.getName() + "Hold");
      this.subsystem = subsystem;
      this.output = output;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
      subsystem.talon.set(ControlMode.PercentOutput, output);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
      return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

  }
}
