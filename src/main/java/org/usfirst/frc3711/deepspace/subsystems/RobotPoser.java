package org.usfirst.frc3711.deepspace.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import org.usfirst.frc3711.deepspace.RobotPose;
import org.usfirst.frc3711.deepspace.commands.sequences.*;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class RobotPoser extends RobotSubsystem {

  private RobotPose currentPose = RobotPose.STOW;

  private final Map<RobotPose, Supplier<Command>> factories;

  private Command currentCommand = new InstantCommand();

  private static final Map<RobotPose,Set<RobotPose>> VALID_TRANSITIONS = validTransitions();


  private static Map<RobotPose, Set<RobotPose>> validTransitions(){
    var valid = new EnumMap<RobotPose,Set<RobotPose>>(RobotPose.class);
    for(var pose : RobotPose.values()){
      valid.put(pose,EnumSet.allOf(RobotPose.class));
    }
    //TODO: only return transitions that are known to work
    return valid;
  }

  private Map<RobotPose, Supplier<Command>> makeFactoryMap(){
    Map<RobotPose, Supplier<Command>> factoryMap = new EnumMap<>(RobotPose.class);
    factoryMap.put(RobotPose.STOW,this::stowCommandForCurrentPose );

    factoryMap.put(RobotPose.FUEL_0, HatchFuel0::new);
    factoryMap.put(RobotPose.FUEL_1, HatchFuel1::new);
    factoryMap.put(RobotPose.FUEL_2, HatchFuel2::new);

    factoryMap.put(RobotPose.HATCH_0, HatchPanel0::new);
    factoryMap.put(RobotPose.HATCH_1,HatchPanel1::new);
    factoryMap.put(RobotPose.HATCH_2,HatchPanel2::new);

    factoryMap.put(RobotPose.FUEL_PICKUP,LoadingStationFuel::new);

    factoryMap.put(RobotPose.GROUND_PICKUP,GroundPickup::new);

    assert factoryMap.keySet().equals(EnumSet.allOf(RobotPose.class))
        : "missing a robot pose command factory";

    return factoryMap;
  }

  public RobotPoser(){
    super(RobotPoser.class.getSimpleName());

    factories = makeFactoryMap();

    var container = tab.getLayout("Poses", BuiltInLayouts.kList); //TODO figure out grid constraints

    for(var p : RobotPose.values()) {
      //TODO change to CommandGroup to see completion/debugging?
      container.add(new InstantCommand(p.name(),() -> setPose(p)));
    }

  }

  @SuppressWarnings("WeakerAccess")
  public boolean isPosing(){
    return currentCommand.isRunning();
  }

  public void setPose(RobotPose newPose){
    if(isPosing()){
      DriverStation.reportError("Attempting to change poses while already posing",false);
      return; // TODO:can make delayed command?
    }
    if(!VALID_TRANSITIONS.get(currentPose).contains(newPose)){
      DriverStation.reportError("Invalid pose transition",false);
      return;
    }
    currentPose = newPose;
    currentCommand= factories.get(newPose).get();
    currentCommand.start();
  }

  private static EnumSet<RobotPose> upperLevelPoses = EnumSet.of(
      RobotPose.HATCH_1,RobotPose.HATCH_2,
      RobotPose.FUEL_1,RobotPose.FUEL_2);

  private Command stowCommandForCurrentPose(){
    return new Stow(); //TODO: upper level & Loading station
  }

}
