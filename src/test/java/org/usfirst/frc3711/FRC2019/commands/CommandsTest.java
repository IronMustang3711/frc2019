package org.usfirst.frc3711.FRC2019.commands;

import edu.wpi.first.wpilibj.command.Scheduler;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class CommandsTest {

	@Test
	public void testMakeACommand() {
		Scheduler.getInstance().removeAll();
		Scheduler.getInstance().run();

	}
}
