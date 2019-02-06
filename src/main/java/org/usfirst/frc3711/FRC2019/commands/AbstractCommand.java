package org.usfirst.frc3711.FRC2019.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class AbstractCommand extends Command {
	public AbstractCommand() {
	}

	public AbstractCommand(String name) {
		super(name);
	}

	public AbstractCommand(double timeout) {
		super(timeout);
	}

	public AbstractCommand(Subsystem subsystem) {
		super(subsystem);
	}

	public AbstractCommand(String name, Subsystem subsystem) {
		super(name, subsystem);
	}

	public AbstractCommand(double timeout, Subsystem subsystem) {
		super(timeout, subsystem);
	}

	public AbstractCommand(String name, double timeout) {
		super(name, timeout);
	}

	public AbstractCommand(String name, double timeout, Subsystem subsystem) {
		super(name, timeout, subsystem);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
