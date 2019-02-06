package org.usfirst.frc3711.FRC2019.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc3711.FRC2019.Robot;
import org.usfirst.frc3711.FRC2019.RobotPose;



public class RobotPoser extends Command {

	private final RobotPose pose;

	public RobotPoser(RobotPose pose) {
		super(pose.toString());
		this.pose = pose;
		requires(Robot.elevator);
		requires(Robot.arm);
		requires(Robot.wrist);
	}



	@Override
	protected void end() {

	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
