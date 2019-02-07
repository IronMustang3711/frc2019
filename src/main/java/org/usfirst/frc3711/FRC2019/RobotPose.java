package org.usfirst.frc3711.FRC2019;

public enum RobotPose {
	GROUND_PICKUP(0,0,0),
	LOWER_HATCHPANEL(0,0,0),
	STOW(100,10,10);

	RobotPose(double elevator,double arm,double wrist){
		this.elevatorSetpoint = elevator;
		this.armSetpoint = arm;
		this.wristSetpoint = wrist;
	}

	public final double elevatorSetpoint;
	public final double armSetpoint;
	public final double wristSetpoint;
}
