package org.usfirst.frc3711.deepspace.commands.sequences;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.MyCommandGroup;
import edu.wpi.first.wpilibj.shuffleboard.EventImportance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import org.usfirst.frc3711.deepspace.Robot;
import org.usfirst.frc3711.deepspace.commands.util.Commands;
import org.usfirst.frc3711.deepspace.commands.util.MotionMagicSetpoint;

public class HatchPanel2 extends MyCommandGroup {

  public HatchPanel2() {
    super(HatchPanel2.class.getSimpleName());
    requires(Robot.arm);
    requires(Robot.wrist);
    requires(Robot.elevator);

    double elevatorPosition = 12000;

    // elevator up:
    addParallel(new MotionMagicSetpoint("Wrist Vertical", Robot.wrist, 90));
    //addParallel(new MotionMagicSetpoint("Arm Vertical", Robot.arm, 40), elevatorUpTimeout);
    addParallel(new MotionMagicSetpoint("bring elevator up", Robot.elevator, elevatorPosition));



    var armOut = new MotionMagicSetpoint("Arm Out", Robot.arm, 3200);
    addParallel(armOut);

    addSequential(Commands.delayUntil("wait unitl arm is about horizontal",
        ()->armOut.isRunning() && Robot.arm.talon.getSelectedSensorPosition() >= 1500));

    addParallel(new MotionMagicSetpoint("Wrist Down", Robot.wrist, -1500)); // was -1900

   // addSequential(new MotionMagicSetpoint("Elevator up", Robot.elevator, 13000));

  }

  @Override
  protected void initialize() {
    super.initialize();

  }

  @Override
  protected void end() {
    super.end();
    if(Robot.debug)
      Shuffleboard.addEventMarker(getName() + "_End", EventImportance.kNormal);

   // RestingPose.run();

  }
}
