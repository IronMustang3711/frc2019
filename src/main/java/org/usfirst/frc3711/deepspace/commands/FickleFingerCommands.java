package org.usfirst.frc3711.deepspace.commands;

import edu.wpi.first.wpilibj.DriverStation;
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
        DriverStation.reportWarning(getName()+".initialize()",false);
      }

      @Override
      protected void execute() {
        super.execute();
        subsystem.setOutput(1.0);
      }

      @Override
      protected void end() {
        super.end();
        DriverStation.reportWarning(getName()+".end()",false);

      stowCommand().start();
      }
    };
  }

  static Command stowCommand(){
    return new TalonSubsystemCommand("Stow",Robot.fickleFinger) {
      @Override
      protected void initialize() {
        super.initialize();
        int currentPosition = subsystem.getPosition();
        int desiredPosition = ENCODER_TICKS_PER_REV *((currentPosition + ENCODER_TICKS_PER_REV/2) / ENCODER_TICKS_PER_REV);
        subsystem.setPosition(desiredPosition,false);
        DriverStation.reportWarning(getName()+".initialize()",false);

      }

      @Override
      protected boolean isFinished() {
        return Math.abs(subsystem.getError()) < 20
               && Math.abs(subsystem.getVelocity()) < 2.0;
      }

      @Override
      protected void end() {
        super.end();
        subsystem.disable();
        DriverStation.reportWarning(getName()+".end()",false);

      }
    };

  }

  public static Command hookCommand(){
    return new TalonSubsystemCommand("Hook" ,Robot.fickleFinger){
      int baseRev;
      @Override
      protected boolean isFinished() {
        return Math.abs(subsystem.getError()) < 20
                   && Math.abs(subsystem.getVelocity()) < 2.0;
      }

      @Override
      protected void initialize() {
        super.initialize();
        baseRev = subsystem.getPosition() / ENCODER_TICKS_PER_REV;
        DriverStation.reportWarning(getName()+".initialize()",false);

      }

      @Override
      protected void execute() {
        super.execute();
        subsystem.setPosition(252 + ENCODER_TICKS_PER_REV*baseRev, false);

      }

      @Override
      protected void end() {
        super.end();
        subsystem.disable();
        DriverStation.reportWarning(getName()+".end()",false);

      }
    };
  }

  public static Command hookEngageCommand(){
    return new TalonSubsystemCommand("Hook Engage" ,Robot.fickleFinger){

      int baseRev;

      @Override
      protected void initialize() {
        super.initialize();
        baseRev = subsystem.getPosition() / ENCODER_TICKS_PER_REV;

      }

      @Override
      protected boolean isFinished() {
        return Math.abs(subsystem.getError()) < 20
                   && Math.abs(subsystem.getVelocity()) < 2.0;
      }

      @Override
      protected void execute() {
        super.execute();
        subsystem.setPosition(120 + ENCODER_TICKS_PER_REV*baseRev,false);
      }

      @Override
      protected void end() {
        super.end();
        subsystem.disable();
      }
    };
  }
  // public static Command fickleFingerToHome(){
  //   return new TalonSubsystemCommand("Fickle Finger to Home", Robot.fickleFinger, 1.5){
  //     @Override
  //     protected void execute() {
  //       super.execute();
  //       subsystem.talon.set(ControlMode.Position, 0);

  //     }
  //     @Override
  //       protected boolean isFinished() {
  //         return super.isFinished() || Math.abs(subsystem.talon.getPosition()) < 40;

  //       }
  //       @Override
  //         protected void end() {
  //           super.end();
  //           subsystem.disable();
  //         }
  //   };
  // }
  // public static Command fickleFingerExtend(){
  //   return new TalonSubsystemCommand("Fickle Finger Extend", Robot.fickleFinger, 1.5){
  //     @Override
  //     protected void execute() {
  //       super.execute();
  //       subsystem.talon.set(ControlMode.Position, -2500);

  //     }
  //     @Override
  //       protected boolean isFinished() {
  //         return super.isFinished() || Math.abs(subsystem.talon.getPosition()) < 40;

  //       }
  //       @Override
  //         protected void end() {
  //           super.end();
  //           subsystem.disable();
  //         }
  //   };
  // }
  // public static Command fickleFingerToggle(){
  //   return new TalonSubsystemCommand("Fickle Finger Toggle", Robot.fickleFinger, 1.5){
  //     @Override
  //     protected void execute() {
  //       super.execute();
  //       subsystem.talon.set(ControlMode.Position, out ? -2500:0);

  //     }
  //     @Override
  //       protected boolean isFinished() {
  //         return super.isFinished() /*|| Math.abs(subsystem.talon.getPosition()) < 40*/;

  //       }
  //       @Override
  //         protected void end() {
  //           super.end();
  //           subsystem.disable();
  //         }
  //         boolean out;
  //         @Override
  //           protected void initialize() {
  //             super.initialize();
  //             out =! Robot.fickleFinger.isOut();

  //           }
  //   };
  // }
}

