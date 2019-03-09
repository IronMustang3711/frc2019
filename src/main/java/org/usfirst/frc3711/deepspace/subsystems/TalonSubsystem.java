package org.usfirst.frc3711.deepspace.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
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

  public void setP(double p) {
    talon.config_kP(0, p, 50);
  }

  public void setI(double i) {
    talon.config_kI(0, i, 50);
  }

  public void setIZone(int iZone) {
    talon.config_IntegralZone(0, iZone, 50);
  }

  public void setD(double d) {
    talon.config_kD(0, d, 50);
  }

  public void setF(double f) {
    talon.config_kF(0, f, 50);
  }

  public double getSetpoint() {
    return talon.getClosedLoopTarget();
  }

  public double getError() {
    return talon.getClosedLoopError(0);
  }

  public double getErrorDelta() {
    return talon.getErrorDerivative();
  }

  @Override
  public void disable() {
    super.disable();
    talon.neutralOutput();
  }

  public void setNeutralMode(NeutralMode brake) {
  }

  public int getClosedLoopError() {
    return 0;
  }

  public int getSelectedSensorVelocity() {
    return 0;
  }

  public int getSelectedSensorPosition() {
    return 0;
  }

  public void set(ControlMode position, double desiredPosition) {
  }

  public void selectProfileSlot(int i, int i1) {
  }

  public double getMotorOutputPercent() {
    return 0;
  }

  public double getMotorOutputVoltage() {
    return 0;
  }

  public double getOutputCurrent() {
    return 0;
  }

  public ControlMode getControlMode() {
    return null;
  }

  public double getClosedLoopTarget() {
    return 0;
  }

  public double getErrorDerivative() {
    return 0;
  }

  public double getIntegralAccumulator() {
    return 0;
  }

  public double getActiveTrajectoryPosition() {
    return 0;
  }

  public double getActiveTrajectoryVelocity() {
    return 0;
  }

  public double getActiveTrajectoryArbFeedFwd() {
    return 0;
  }

  public void neutralOutput() {
  }
}
