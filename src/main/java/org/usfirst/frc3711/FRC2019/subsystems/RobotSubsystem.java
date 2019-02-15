package org.usfirst.frc3711.FRC2019.subsystems;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

@SuppressWarnings("WeakerAccess")
public abstract class RobotSubsystem extends Subsystem {

  public final ShuffleboardTab tab;

  /**
   * Creates a subsystem with the given name.
   *
   * @param name the name of the subsystem
   */
  public RobotSubsystem(String name) {
    super(name);
    this.tab = Shuffleboard.getTab(name);
    tab.add(new InstantCommand("Disable " + getName(), this::disable));

  }

  /**
   * Creates a subsystem. This will set the name to the name of the class.
   */
  public RobotSubsystem() {
    super();
    this.tab = Shuffleboard.getTab(this.getClass().getSimpleName());
    tab.add(new InstantCommand("Disable " + getName(), this::disable));
  }

  public void disable() {
  }
}
