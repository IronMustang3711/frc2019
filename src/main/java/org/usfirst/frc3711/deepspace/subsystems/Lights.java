package org.usfirst.frc3711.deepspace.subsystems;

import edu.wpi.first.wpilibj.Solenoid;

public class Lights extends RobotSubsystem {

  Solenoid red, green, blue;
  public Lights(){
    super ("Lights");
    red = new Solenoid(0, 0);
    green = new Solenoid(0, 1);
    blue = new Solenoid(0,2);
    addChild("red", red);
    addChild("green", green);
    addChild("blue", blue);

  }
  @Override
  protected void initDefaultCommand() {

  }
}
