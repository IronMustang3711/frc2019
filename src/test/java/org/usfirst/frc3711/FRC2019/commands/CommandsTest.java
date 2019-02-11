package org.usfirst.frc3711.FRC2019.commands;

import org.junit.Test;

import edu.wpi.first.wpilibj.command.Scheduler;

public class CommandsTest {

	@Test
	public void testMakeACommand() {
		Scheduler.getInstance().removeAll();
		Scheduler.getInstance().run();

	}
}
