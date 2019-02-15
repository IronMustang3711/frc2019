package org.usfirst.frc3711.deepspace;

public enum RobotPose {
  GROUND_PICKUP(0, 0, 0),
  LOWER_HATCHPANEL(0, 0, 0),
  STOW(400, 0, 40),
  HATCH_0(2000, -10, 600),
  HATCH_1(4600, -10, 600);

  RobotPose(double elevator, double arm, double wrist) {
    this.elevatorSetpoint = elevator;
    this.armSetpoint = arm;
    this.wristSetpoint = wrist;
  }

  public final double elevatorSetpoint;
  public final double armSetpoint;
  public final double wristSetpoint;
}
