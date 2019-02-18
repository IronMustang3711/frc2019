package org.usfirst.frc3711.deepspace.commands.sequences;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.MyCommandGroup;
import edu.wpi.first.wpilibj.shuffleboard.EventImportance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import org.usfirst.frc3711.deepspace.Robot;
import org.usfirst.frc3711.deepspace.commands.util.Commands;
import org.usfirst.frc3711.deepspace.commands.util.MotionMagicSetpoint;

public class LoadingStationFuel extends MyCommandGroup {

  public LoadingStationFuel() {
    super(LoadingStationFuel.class.getSimpleName());
    requires(Robot.arm);
    requires(Robot.wrist);
    requires(Robot.elevator);


    // elevator up:
    addParallel(new MotionMagicSetpoint("Wrist Vertical", Robot.wrist, 90));
    addParallel(new MotionMagicSetpoint("Arm Vertical", Robot.arm, 0));

    var elevatorUp = new MotionMagicSetpoint("bring elevator up", Robot.elevator, 3000);
    addParallel(elevatorUp);

    // Arm out, Wrist down
    addSequential(Commands.delayUntil("Wait for elevator to get 80% way up",
        ()->elevatorUp.isRunning() && elevatorUp.getMotionProgress() >= 0.8));

    addParallel(new MotionMagicSetpoint.ArmSetpoint("Arm Out", 3133));
    addSequential(new MotionMagicSetpoint("Wrist Down", Robot.wrist, -2949));

    //elevator back down
    addSequential(new MotionMagicSetpoint("Elevator Down", Robot.elevator, -3000));

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
  }
}
