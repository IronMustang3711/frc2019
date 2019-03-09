package org.usfirst.frc3711.deepspace.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import org.usfirst.frc3711.deepspace.commands.util.Commands;

public abstract class TalonSubsystem extends RobotSubsystem {

  final NetworkTableEntry ntSetpoint;
  protected final TalonSRX talon;
  private final NetworkTableEntry currentLimitingEnabled;

  final StickyFaults tmpStickyFaults = new StickyFaults();
  final StickyFaults stickyFaults = new StickyFaults();
  final Faults tmpFaults = new Faults();
  final Faults faults = new Faults();


  public TalonSubsystem(String name, int talonID) {
    super(name);
    this.talon = new TalonSRX(talonID);
    addChild("Talon["+talon.getDeviceID()+"]:"+name);

    tab.add(Commands.disableCommand(this));
    tab.add(Commands.disableCurrentLimitCommand(this));
    tab.add(Commands.enableCurrentLimitCommand(this));

    ntSetpoint = tab.add("setpoint", 0.0).getEntry();
    currentLimitingEnabled = tab.add("Current Limiting", true).getEntry();

    ntSetpoint.addListener(entryNotification -> onSetpointChange(entryNotification.value.getDouble()),
        EntryListenerFlags.kUpdate);
    configureTalon();


    talon.getStickyFaults(stickyFaults);
    tmpStickyFaults.update(stickyFaults.toBitfield());

    if(stickyFaults.hasAnyFault()){
      DriverStation.reportError(getName() + "FAULT: "+stickyFaults.toString(),false);
    }

  }

  protected void onSetpointChange(double newSetpoint) {
  }


  @Override
  protected void initDefaultCommand() {

  }

  void configureTalon() {
    talon.configFactoryDefault();
    talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    talon.selectProfileSlot(0, 0);
  }

  public void enableCurrentLimiting() {
    currentLimitingEnabled.setBoolean(true);
    talon.enableCurrentLimit(true);
  }

  public void disableCurrentLimiting() {
    currentLimitingEnabled.setBoolean(false);
    talon.enableCurrentLimit(false);
  }

  public void zeroEncoder(){
    talon.setSelectedSensorPosition(0, 0, 50);
    talon.getSensorCollection().setQuadraturePosition(0, 50);
  }

  @Override
  public void periodic() {
    super.periodic();

    talon.getStickyFaults(tmpStickyFaults);
    if(stickyFaults.toBitfield() != tmpStickyFaults.toBitfield()){
      stickyFaults.update(tmpStickyFaults.toBitfield());

      DriverStation.reportError(getName() + "STICKY FAULT: "+stickyFaults.toString(),false);
    }

    talon.getFaults(tmpFaults);
    if(faults.toBitfield() != tmpFaults.toBitfield()){
      faults.update(tmpFaults.toBitfield());
      DriverStation.reportError(getName() + "FAULT: "+faults.toString(),false);

    }

  }

//  public void setP(double p) {
//    talon.config_kP(0, p, 50);
//  }
//
//  public void setI(double i) {
//    talon.config_kI(0, i, 50);
//  }
//
//  public void setIZone(int iZone) {
//    talon.config_IntegralZone(0, iZone, 50);
//  }
//
//  public void setD(double d) {
//    talon.config_kD(0, d, 50);
//  }
//
//  public void setF(double f) {
//    talon.config_kF(0, f, 50);
//  }

  public boolean isClosedLoop(){
    return TalonUtil.isClosedLoopMode(talon.getControlMode());
  }

  public double getSetpoint() {
    return isClosedLoop() ? talon.getClosedLoopTarget() : 0;
  }

  public double getError() {
    return isClosedLoop() ? talon.getClosedLoopError() : 0;
  }

  public double getErrorDelta() {
    return isClosedLoop() ? talon.getErrorDerivative() : 0;
  }

  @Override
  public void disable() {
    super.disable();
    talon.neutralOutput();
  }

  public void enableBraking(boolean enable){
    talon.setNeutralMode(enable ? NeutralMode.Brake : NeutralMode.Coast);
  }

//  public void setNeutralMode(NeutralMode brake) {
//    talon.setNeutralMode(brake);
//  }

  public int getVelocity() {
    return talon.getSelectedSensorVelocity();
  }

  public int getPosition() {
    return talon.getSelectedSensorPosition();
  }

//  public void set(ControlMode position, double desiredPosition) {
//    talon.set(position, desiredPosition);
//  }

//  public void selectProfileSlot(int slotidx, int pididx) {
//    talon.selectProfileSlot(slotidx, pididx);
//  }

  public void setOutput(double output){
    talon.selectProfileSlot(TalonUtil.POSITION_SLOT,TalonUtil.PRIMARY_PID);
    talon.set(ControlMode.PercentOutput,output);
  }
  public void setPosition(double position){
    setPosition(position,true);
  }

  public void setPosition(double position, boolean useMotionProfiling){
    if(useMotionProfiling){
      talon.selectProfileSlot(TalonUtil.MM_SLOT,TalonUtil.PRIMARY_PID);
      talon.set(ControlMode.MotionMagic,position);
    }
    else {
      talon.selectProfileSlot(TalonUtil.POSITION_SLOT,TalonUtil.PRIMARY_PID);
      talon.set(ControlMode.Position,position);
    }
  }

  double getOutput() {
    return talon.getMotorOutputPercent();
  }

  double getOutputVoltage() {
    return talon.getMotorOutputVoltage();
  }

  double getOutputCurrent() {
    return talon.getOutputCurrent();
  }

  ControlMode getControlMode() {
    return talon.getControlMode();
  }

//  public double getClosedLoopTarget() {
//    return talon.getClosedLoopTarget();
//  }

//  double getErrorDerivative() {
//    return talon.getErrorDerivative();
//  }

  double getIntegralAccumulator() {
    return talon.getIntegralAccumulator();
  }

  public double getActiveTrajectoryPosition() {
    return talon.getActiveTrajectoryPosition();
  }

  double getActiveTrajectoryVelocity() {
    return talon.getActiveTrajectoryVelocity();
  }

  double getActiveTrajectoryArbFeedFwd() {
    return talon.getActiveTrajectoryArbFeedFwd();
  }

  public void neutralOutput() {
    disable();
  }
}
