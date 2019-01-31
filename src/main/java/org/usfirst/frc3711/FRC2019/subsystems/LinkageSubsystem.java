package org.usfirst.frc3711.FRC2019.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class LinkageSubsystem extends Subsystem {

  public final WPI_TalonSRX talon;

  public LinkageSubsystem(String name, int talonID){
    super(name);
    this.talon = new WPI_TalonSRX(talonID);
  }

//  @Override
//  protected void initDefaultCommand() {
//
//  }
}
