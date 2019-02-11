package org.usfirst.frc3711.FRC2019.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Sendable;

public abstract class TalonSubsystem extends RobotSubsystem {

  final NetworkTableEntry ntSetpoint;
  //final NetworkTableEntry ntClosedLoopEnabled;



  public final WPI_TalonSRX talon;


  //private double setpoint;

  public TalonSubsystem(String name, int talonID){
    super(name);
    this.talon = new WPI_TalonSRX(talonID);

    ntSetpoint = tab.add("setpoint", 0.0).getEntry();

    ntSetpoint.addListener(entryNotification -> {
      onSetpointChange(entryNotification.value.getDouble());
    }, EntryListenerFlags.kUpdate);


    // ntClosedLoopEnabled = tab.add("setpoint enabled",false)
    //                           .withWidget(BuiltInWidgets.kToggleSwitch)
    //                           .getEntry();

    // ntClosedLoopEnabled.addListener(entryNotification -> {
    //   if(entryNotification.value.getBoolean())
    //     onEnableClosedLoop();
    //   else
    //     onDisableClosedLoop();
    // },EntryListenerFlags.kUpdate);

    //TalonTelemetry.installMototIOTelemetry(this);
   // TalonTelemetry.installSensorCollectionTelemetry(this);


    configureTalon();
  }

  protected void onSetpointChange(double newSetpoint){
   // this.setpoint = newSetpoint;
    System.out.println(getName() + ": new setpoint("+newSetpoint+")");

  }

  protected void onEnableClosedLoop(){
    System.out.println(getName() + ": Enable closed loop");
  }
  protected void onDisableClosedLoop(){
    System.out.println(getName() + ": Disable closed loop");
    talon.neutralOutput();
  }

  public <T extends Sendable> T addChildItem(String name,T sendable){
    addChild(name,sendable);
    return sendable;
  }
  public <T extends Sendable> T addChildItem(T sendable){
    addChild(sendable);
    return sendable;
  }


  @Override
  protected void initDefaultCommand() {

  }

  void configureTalon(){
    talon.configFactoryDefault();
    talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    talon.selectProfileSlot(0,0);
  }

  public void enableCurrentLimiting(){
    talon.enableCurrentLimit(true);
  }

  public void disableCurrentLimiting(){
    talon.enableCurrentLimit(false);
  }



  public void setP(double p){
    talon.config_kP(0,p,50);
  }

  public void setI(double i){
    talon.config_kI(0,i,50);
  }
  public void setIZone(int iZone){
    talon.config_IntegralZone(0,iZone,50);
  }
  public void setD(double d){
    talon.config_kD(0,d,50);
  }
  public void setF(double f){
    talon.config_kF(0,f,50);
  }

  public double getSetpoint(){
    return talon.getClosedLoopTarget();
  }

  public double getError(){
    return talon.getClosedLoopError(0);
  }
  public double getErrorDelta(){
    return talon.getErrorDerivative();
  }


  void telemetry(){
//    double busVoltage = talon.getBusVoltage();
//    double motorOutputVoltage = talon.getMotorOutputVoltage();
//    double motorOutputPercent = talon.getMotorOutputPercent();
//    double position = talon.getSelectedSensorPosition();
//    double velocity = talon.getSelectedSensorVelocity();
//    SmartDashboard.putNumber(getName() + ": Bus Voltage", busVoltage);
//    SmartDashboard.putNumber(getName() + ": Output Voltage", motorOutputVoltage);
//    SmartDashboard.putNumber(getName() + ": Output Percent", motorOutputPercent);
//    SmartDashboard.putNumber(getName() + ": Position", position);
//    SmartDashboard.putNumber(getName() + ": Velocity", velocity);
  }

//  @Override
//  protected void initDefaultCommand() {
//
//  }


  @Override
  public void disable() {
    super.disable();
    talon.neutralOutput();
  }
}
