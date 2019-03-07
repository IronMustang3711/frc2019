package org.usfirst.frc3711.deepspace.commands.sequences;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.EventImportance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import org.usfirst.frc3711.deepspace.Robot;
import org.usfirst.frc3711.deepspace.commands.util.Commands;
import org.usfirst.frc3711.deepspace.commands.util.MotionMagicSetpoint;

public class GroundPickup extends Command {

  int armOutFinishedCound = 0;


  private final Command wristVertical =  new MotionMagicSetpoint("Wrist Vertical", Robot.wrist, 90);
  private final Command armVertical = new MotionMagicSetpoint("Arm Vertical", Robot.arm, 40);
  private final MotionMagicSetpoint elevatorUp =  new MotionMagicSetpoint("bring elevator up", Robot.elevator, 10000, 2.5) {
    @Override
    protected boolean isFinished() {
      return isMotionFinished() || super.isFinished();
    }
  };

  private final Command wristDown = Commands.runWhenTrue(
      new MotionMagicSetpoint("Wrist Down", Robot.wrist, -2200),
      ()-> elevatorUp.getMotionProgress() > 0.7);

  private final Command armOut = Commands.runWhenTrue(
      new MotionMagicSetpoint("Bring  Out", Robot.arm, 1800),
      ()->elevatorUp.getMotionProgress() >= 0.5);


      private final Command elevatorDown = Commands.runWhenTrue( new MotionMagicSetpoint("bring elevator down", Robot.elevator, -6000, 2.5) {
        @Override
        protected boolean isFinished() {
          return isMotionFinished() || super.isFinished();
        }
      }, ()-> {
        if(armOut.isCompleted()) armOutFinishedCound++;
        return armOutFinishedCound >= 10;
      });

  public GroundPickup() {
    super(GroundPickup.class.getSimpleName(),5.0);
//      requires(Robot.arm);
//      requires(Robot.wrist);
//      requires(Robot.elevator);

//
//      double elevatorUpTimeout = 1.7;
//      double elevatorPosition = 13000;
//
//      addParallel(new MotionMagicSetpoint("Wrist Vertical", Robot.wrist, 90), elevatorUpTimeout);
//      addParallel(new MotionMagicSetpoint("Arm Vertical", Robot.arm, 40), elevatorUpTimeout);
//      addSequential(
//          new MotionMagicSetpoint("bring elevator up", Robot.elevator, elevatorPosition, 2.5) {
//            @Override
//            protected boolean isFinished() {
//              return super.isFinished()
//                         || getElapsedTime() > 0.5 && Math.abs(subsystem.talon.getErrorDerivative()) < 1.0
//                         || Math.abs(subsystem.talon.getClosedLoopError()) < 100;
//            }
//          }
//      );

//      //addParallel(new MotionMagicSetpoint("Hold Elevator Position", Robot.elevator, elevatorPosition));
//      addParallel(new MotionMagicSetpoint("Wrist Down", Robot.wrist, -600));
//      addParallel(new MotionMagicSetpoint("Bring  Out", Robot.arm, 200));

  }

  @Override
  protected void initialize() {
    super.initialize();
    if(Robot.debug)
      Shuffleboard.addEventMarker(getName() + "_Init", EventImportance.kNormal);
    wristVertical.start();
    armVertical.start();
    elevatorUp.start();
    wristDown.start();
    armOut.start();
    elevatorDown.start();
    armOutFinishedCound = 0;

  }


  @Override
  protected boolean isFinished() {
    return isTimedOut() || elevatorDown.isCompleted();
  }

  @Override
  protected void end() {
    super.end();
    if(Robot.debug)
      Shuffleboard.addEventMarker(getName() + "_End", EventImportance.kNormal);
   // RestingPose.run();
  }
}
