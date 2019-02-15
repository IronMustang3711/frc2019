package org.usfirst.frc3711.FRC2019.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTableEntry;
import org.usfirst.frc3711.FRC2019.commands.util.Commands;

public abstract class TalonSubsystem extends RobotSubsystem {

  final NetworkTableEntry ntSetpoint;
  public final WPI_TalonSRX talon;
  private final NetworkTableEntry currentLimitingEnabled;

  public TalonSubsystem(String name, int talonID) {
    super(name);
    this.talon = new WPI_TalonSRX(talonID);

    tab.add(Commands.disableCommand(this));
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
}
