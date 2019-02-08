package org.usfirst.frc3711.FRC2019.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
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
	protected void initialize() {
//		Robot.elevator.configMotionMagicClosedLoop();
//		Robot.arm.configMotionMagicClosedLoop();
//		Robot.wrist.configMotionMagicClosedLoop();

	}

	@Override
	protected void execute() {
		Robot.elevator.talon.set(ControlMode.MotionMagic,pose.elevatorSetpoint);
		Robot.arm.talon.set(ControlMode.MotionMagic,pose.armSetpoint);
		Robot.wrist.talon.set(ControlMode.MotionMagic,pose.wristSetpoint);
	}

	@Override
	protected void end() {

		Robot.elevator.talon.neutralOutput();
		Robot.arm.talon.neutralOutput();
		Robot.wrist.talon.neutralOutput();

	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
