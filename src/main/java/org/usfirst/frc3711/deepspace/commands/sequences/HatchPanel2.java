package org.usfirst.frc3711.deepspace.commands.sequences;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.MyCommandGroup;
import edu.wpi.first.wpilibj.shuffleboard.EventImportance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import org.usfirst.frc3711.deepspace.Robot;
import org.usfirst.frc3711.deepspace.commands.util.MotionMagicSetpoint;

public class HatchPanel2 extends MyCommandGroup {

  public HatchPanel2() {
    super(HatchPanel2.class.getSimpleName());
    requires(Robot.arm);
    requires(Robot.wrist);
    requires(Robot.elevator);

    double elevatorUpTimeout = 1.7;
    double elevatorPosition = 13000;

    // elevator up:
    addParallel(new MotionMagicSetpoint("Wrist Vertical", Robot.wrist, 90), elevatorUpTimeout);
    addParallel(new MotionMagicSetpoint("Arm Vertical", Robot.arm, 40), elevatorUpTimeout);
    addParallel(new MotionMagicSetpoint("bring elevator up", Robot.elevator, elevatorPosition));


    double armOutTimeout = 3.0;
    // Arm out, Wrist down
//    addParallel(new MotionMagicSetpoint("Hold Elevator Position",
//        Robot.elevator, elevatorPosition, armOutTimeout));
    //	addSequential(new MotionMagicSetpoint("Wrist Down",Robot.wrist,-2949),armOutTimeout);
    addSequential(new MotionMagicSetpoint("Arm Out", Robot.arm, 3300, 3.0) {
      @Override
      protected boolean isFinished() {
        return super.isFinished();
        // return isTimedOut() && Math.abs(subsystem.talon.getErrorDerivative()) < 1.0
        // 		|| Math.abs(subsystem.talon.getClosedLoopError()) < 150;
      }
    });
    addSequential(new MotionMagicSetpoint("Wrist Down", Robot.wrist, -1900), armOutTimeout);

    addSequential(new MotionMagicSetpoint("Elevator up", Robot.elevator, 13000));

  }

  @Override
  protected void initialize() {
    super.initialize();
    Shuffleboard.addEventMarker(getName() + "_Init", EventImportance.kNormal);

  }

  @Override
  protected void end() {
    super.end();
    Shuffleboard.addEventMarker(getName() + "_End", EventImportance.kNormal);

   // RestingPose.run();

  }
}
