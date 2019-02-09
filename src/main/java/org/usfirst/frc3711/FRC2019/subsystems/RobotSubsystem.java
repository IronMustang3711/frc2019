package org.usfirst.frc3711.FRC2019.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class RobotSubsystem extends Subsystem {

	/**
	 * Creates a subsystem with the given name.
	 *
	 * @param name the name of the subsystem
	 */
	public RobotSubsystem(String name) {
		super(name);
	}

	/**
	 * Creates a subsystem. This will set the name to the name of the class.
	 */
	public RobotSubsystem() {
	}

	public void disable(){}
}
