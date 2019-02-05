package org.usfirst.frc3711.FRC2019.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class TalonSubsystem extends Subsystem {

  public final WPI_TalonSRX talon;

  public TalonSubsystem(String name, int talonID){
    super(name);
    this.talon = new WPI_TalonSRX(talonID);
    configureTalon();
  }

  void configureTalon(){
    talon.configFactoryDefault();
    talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
   // talon.setSensorPhase(true);


  }
  void telemetry(){
    double busVoltage = talon.getBusVoltage();
    double motorOutputVoltage = talon.getMotorOutputVoltage();
    double motorOutputPercent = talon.getMotorOutputPercent();
    double position = talon.getSelectedSensorPosition();
    double velocity = talon.getSelectedSensorVelocity();
    SmartDashboard.putNumber(getName() + ": Bus Voltage", busVoltage);
    SmartDashboard.putNumber(getName() + ": Output Voltage", motorOutputVoltage);
    SmartDashboard.putNumber(getName() + ": Output Percent", motorOutputPercent);
    SmartDashboard.putNumber(getName() + ": Position", position);
    SmartDashboard.putNumber(getName() + ": Velocity", velocity);
  }

//  @Override
//  protected void initDefaultCommand() {
//
//  }
}
