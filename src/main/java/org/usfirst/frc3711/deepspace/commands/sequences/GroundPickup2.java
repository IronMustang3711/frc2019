package org.usfirst.frc3711.deepspace.commands.sequences;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc3711.deepspace.Robot;
import org.usfirst.frc3711.deepspace.commands.util.DeferedCommandBuilder;
import org.usfirst.frc3711.deepspace.commands.util.MotionMagicSetpoint;

public class GroundPickup2 extends CommandGroup {
  private MotionMagicSetpoint elevatorUp = new MotionMagicSetpoint("bring elevator up", Robot.elevator, 10000, 2.5);
  private MotionMagicSetpoint armOut = new MotionMagicSetpoint("Arm  Out", Robot.arm, 1800);
  private MotionMagicSetpoint elevatorDown = new MotionMagicSetpoint("bring elevator down", Robot.elevator, -9000, 2.5);

  public GroundPickup2(){
    super("GroundPickup2");
    addParallel(new MotionMagicSetpoint("Wrist Vertical", Robot.wrist, 90),0.2);
    addParallel(new MotionMagicSetpoint("Arm Vertical", Robot.arm, 40),0.2);
    addParallel(elevatorUp);
    addParallel(new DeferedCommandBuilder(armOut)
                    .runWhen(() -> elevatorUp.isRunning() && elevatorUp.getMotionProgress() >= 0.3));
    addSequential(new DeferedCommandBuilder(elevatorDown)
                      .runWhen(() -> armOut.isRunning() && armOut.getMotionProgress() >= 0.7));

  }


}
