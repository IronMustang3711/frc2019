package org.usfirst.frc3711.deepspace.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
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
  public void updateAllianceColor(){
    switch (DriverStation.getInstance().getAlliance()){
      case Red:
        red.set(true);
        green.set(false);
        blue.set(false);
        break;
      case Blue:
        red.set(false);
        green.set(false);
        blue.set(true);
        break;
      case Invalid:
        red.set(true);
        green.set(true);
        blue.set(true);
        break;
    }

  }
}
