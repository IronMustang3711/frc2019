package org.usfirst.frc3711.deepspace.commands.sequences;

import edu.wpi.first.wpilibj.command.MyCommandGroup;
import edu.wpi.first.wpilibj.command.WaitForChildren;
import org.usfirst.frc3711.deepspace.Robot;
import org.usfirst.frc3711.deepspace.commands.util.Commands;
import org.usfirst.frc3711.deepspace.commands.util.MotionMagicSetpoint;

public class HatchFuel1 extends MyCommandGroup {

  public HatchFuel1() {
    super(HatchFuel1.class.getSimpleName());
    requires(Robot.arm);
    requires(Robot.wrist);
    requires(Robot.elevator);


    // elevator up:
    addParallel(new MotionMagicSetpoint("Wrist Vertical", Robot.wrist, 0));
    addParallel(new MotionMagicSetpoint.ArmSetpoint("Arm Vertical", 0));

    var elevatorUp = new MotionMagicSetpoint("bring elevator up", Robot.elevator, 5000);
    addParallel(elevatorUp);
    addSequential(Commands.delayUntil(()->elevatorUp.isRunning() && elevatorUp.getMotionProgress() > 0.7));

    var armOut = new MotionMagicSetpoint.ArmSetpoint("Arm Out", 3100);
    addParallel(armOut);
    addSequential(Commands.delayUntil(()->armOut.isRunning() && armOut.getMotionProgress() >= 0.4));
    addSequential(new MotionMagicSetpoint("Wrist Down", Robot.wrist, -2949));
    addSequential(new WaitForChildren());

  }

}
