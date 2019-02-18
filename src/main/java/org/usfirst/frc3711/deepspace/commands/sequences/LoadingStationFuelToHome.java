package org.usfirst.frc3711.deepspace.commands.sequences;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.MyCommandGroup;
import edu.wpi.first.wpilibj.command.WaitForChildren;
import org.usfirst.frc3711.deepspace.Robot;
import org.usfirst.frc3711.deepspace.commands.util.MotionMagicSetpoint;

public class LoadingStationFuelToHome extends MyCommandGroup {
  public LoadingStationFuelToHome(){
    super(LoadingStationFuelToHome.class.getSimpleName());
    addSequential(new MotionMagicSetpoint("Elevator up", Robot.elevator,5000));
    addParallel(new MotionMagicSetpoint("Wrist Home",Robot.wrist,10));
    addParallel(new MotionMagicSetpoint.ArmSetpoint("Arm Home",0));
    addSequential(new WaitForChildren());
    addSequential(new MotionMagicSetpoint("Elevator Home", Robot.elevator,0));
  }
}
