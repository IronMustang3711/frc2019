package org.usfirst.frc3711.deepspace.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc3711.deepspace.Robot;
import org.usfirst.frc3711.deepspace.commands.util.TalonSubsystemCommand;

public class FickleFingerCommands {

  private static final int ENCODER_TICKS_PER_REV = 1680;


  public static Command ejectCommand(){
    return new TalonSubsystemCommand("Eject",Robot.fickleFinger) {

      @Override
      protected boolean isFinished() {
        return isTimedOut() || isCanceled();
      }

      @Override
      protected void initialize() {
        subsystem.talon.selectProfileSlot(0,0);
      }

      @Override
      protected void execute() {
        super.execute();
        subsystem.talon.set(ControlMode.PercentOutput,1.0);
      }

      @Override
      protected void end() {
        super.end();
        subsystem.disable();

        int currentPosition = subsystem.talon.getSelectedSensorPosition();
        int desiredPosition = ENCODER_TICKS_PER_REV *((currentPosition + ENCODER_TICKS_PER_REV/2) / ENCODER_TICKS_PER_REV);
        subsystem.talon.set(ControlMode.Position,desiredPosition);

      }
    };
  }

  public static Command hookCommand(){
    return new TalonSubsystemCommand("Hook" ,Robot.fickleFinger){
      int baseRev;
      @Override
      protected boolean isFinished() {
        return subsystem.talon.getClosedLoopError() < 30;
      }

      @Override
      protected void initialize() {
        super.initialize();
        baseRev = subsystem.talon.getSelectedSensorPosition() / ENCODER_TICKS_PER_REV;
      }

      @Override
      protected void execute() {
        super.execute();
        subsystem.talon.set(ControlMode.Position,252 + ENCODER_TICKS_PER_REV*baseRev);
      }

      @Override
      protected void end() {
        super.end();
        subsystem.disable();
      }
    };
  }

  public static Command hookEngageCommand(){
    return new TalonSubsystemCommand("Hook Engage" ,Robot.fickleFinger){

      int baseRev;

      @Override
      protected void initialize() {
        super.initialize();
        baseRev = subsystem.talon.getSelectedSensorPosition() / ENCODER_TICKS_PER_REV;

      }

      @Override
      protected boolean isFinished() {
        return subsystem.talon.getClosedLoopError() < 30;
      }

      @Override
      protected void execute() {
        super.execute();
        subsystem.talon.set(ControlMode.Position,120 + ENCODER_TICKS_PER_REV*baseRev);
      }

      @Override
      protected void end() {
        super.end();
        subsystem.disable();
      }
    };
  }
}
