package org.usfirst.frc3711.deepspace.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StickyFaults;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import org.usfirst.frc3711.deepspace.commands.Disable;
import org.usfirst.frc3711.deepspace.commands.util.Commands;

import java.util.HashMap;

public abstract class TalonSubsystem extends RobotSubsystem {

  final NetworkTableEntry ntSetpoint;
  public final WPI_TalonSRX talon;
  private final NetworkTableEntry currentLimitingEnabled;
  private final StickyFaults initialFaults = new StickyFaults();
  private final StickyFaults tmpFaults = new StickyFaults();

  public TalonSubsystem(String name, int talonID) {
    super(name);
    this.talon = new WPI_TalonSRX(talonID);
    talon.getStickyFaults(initialFaults);
    if (initialFaults.hasAnyFault())
      DriverStation.reportError(getName() + " Has Sticky Faults!!!" + initialFaults.toString(), false);

    //  tab.add(Commands.disableCommand(this));
    tab.add(Commands.disableCurrentLimitCommand(this));
    tab.add(Commands.enableCurrentLimitCommand(this));

    ntSetpoint = tab.add("setpoint", 0.0).getEntry();
    currentLimitingEnabled = tab.add("Current Limiting", true).getEntry();

    ntSetpoint.addListener(entryNotification -> onSetpointChange(entryNotification.value.getDouble()),
        EntryListenerFlags.kUpdate);
    configureTalon();

  }

  protected void onSetpointChange(double newSetpoint) {
  }


  @Override
  protected void initDefaultCommand() {
    tab.add(new Disable(this));
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

  public void zeroEncoder() {
    talon.setSelectedSensorPosition(0, 0, 50);
    talon.getSensorCollection().setQuadraturePosition(0, 50);
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
  public void periodic() {
    super.periodic();
    talon.getStickyFaults(tmpFaults);
    if(!stickyFaultsEq(initialFaults,tmpFaults)){
      DriverStation.reportError("New Sticky Fault @"+getName() + ":"+tmpFaults.toString(),false);
    }
  }
  @Override
  public void disable() {
    super.disable();
    talon.neutralOutput();
  }
  private static boolean stickyFaultsEq(StickyFaults a, StickyFaults b){
    return a.toBitfield() == b.toBitfield();

  }
}
