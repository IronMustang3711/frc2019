package org.usfirst.frc3711.deepspace.commands.sequences;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc3711.deepspace.Robot;
import org.usfirst.frc3711.deepspace.commands.util.MotionMagicSetpoint;

public class LoadingStationFuelToHome extends CommandGroup {
  public LoadingStationFuelToHome(){
    super(LoadingStationFuelToHome.class.getSimpleName());
    addSequential(new MotionMagicSetpoint("Elevator up", Robot.elevator,4000,2.5));
    addParallel(new MotionMagicSetpoint("Wrist Home",Robot.wrist,10,2.5));
    addParallel(new MotionMagicSetpoint("Arm Home",Robot.arm,10,2.5));
  }
}