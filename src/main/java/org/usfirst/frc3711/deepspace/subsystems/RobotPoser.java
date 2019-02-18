package org.usfirst.frc3711.deepspace.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import org.usfirst.frc3711.deepspace.RobotPose;
import org.usfirst.frc3711.deepspace.commands.sequences.*;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class RobotPoser extends RobotSubsystem {

  private RobotPose pose = RobotPose.STOW;

  private Map<RobotPose, Supplier<Command>> factories;

  private Command currentCommand = new InstantCommand();

  private static Map<RobotPose,Set<RobotPose>> VALID_TRANSITIONS = validTransitions();


  private static Map<RobotPose, Set<RobotPose>> validTransitions(){
    var valid = new EnumMap<RobotPose,Set<RobotPose>>(RobotPose.class);
    for(var pose : RobotPose.values()){
      valid.put(pose,EnumSet.allOf(RobotPose.class));
    }
    //TODO: only return transitions that are known to work
    return valid;
  }
  public RobotPoser(){
    super(RobotPoser.class.getSimpleName());

    factories = new EnumMap<>(RobotPose.class);
    factories.put(RobotPose.STOW,this::stowCommandForCurrentPose );

    factories.put(RobotPose.FUEL_0, HatchFuel0::new);
    factories.put(RobotPose.FUEL_1, HatchFuel1::new);
    factories.put(RobotPose.FUEL_2, HatchFuel2::new);

    factories.put(RobotPose.HATCH_0, HatchPanel0::new);
    factories.put(RobotPose.HATCH_1,HatchPanel1::new);
    factories.put(RobotPose.HATCH_2,HatchPanel2::new);

    factories.put(RobotPose.FUEL_PICKUP,LoadingStationFuel::new);

    factories.put(RobotPose.GROUND_PICKUP,GroundPickup::new);

    assert factories.keySet().equals(EnumSet.allOf(RobotPose.class))
        : "missing a robot pose command factory";

  }

  public boolean isPosing(){
    return currentCommand.isRunning();
  }

  public void setPose(RobotPose newPose){
    if(isPosing()){
      DriverStation.reportWarning("Attempting to change poses while already posing",false);
      return; // TODO:can make delayed command?
    }

  }

  private static EnumSet<RobotPose> upperLevelPoses = EnumSet.of(
      RobotPose.HATCH_1,RobotPose.HATCH_2,
      RobotPose.FUEL_1,RobotPose.FUEL_2);

  private Command stowCommandForCurrentPose(){
    return null; //TODO: implement!
  }

}
