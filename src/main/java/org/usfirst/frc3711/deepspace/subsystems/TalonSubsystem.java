package org.usfirst.frc3711.deepspace.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import org.usfirst.frc3711.deepspace.commands.util.Commands;
import org.usfirst.frc3711.deepspace.commands.util.TalonSubsystemCommand;

public abstract class TalonSubsystem extends RobotSubsystem {

  final NetworkTableEntry ntSetpoint;
  protected final TalonSRX talon;
  private final NetworkTableEntry currentLimitingEnabled;

  private final StickyFaults[] stickyFaults = {new StickyFaults(),new StickyFaults()};

  private final Faults[] faults = {new Faults(), new Faults()};

  static class TalonSetpointSpec {
    final double value;
    final boolean motion;

    TalonSetpointSpec(double value, boolean motion) {
      this.value = value;
      this.motion = motion;
    }

    @Override
    public String toString() {
      return "{" +
             "value=" + value +
             ", motion=" + motion +
             '}';
    }
  }

  private TalonSetpointSpec lastPosition;


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


    talon.getStickyFaults(stickyFaults[0]);
    talon.getStickyFaults(stickyFaults[1]);

    if(stickyFaults[0].hasAnyFault()){
      DriverStation.reportError(getName() + "FAULT: "+stickyFaults[0].toString(),false);
    }

  }

  protected void onSetpointChange(double newSetpoint) {
  }


  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new TalonSubsystemCommand("Hold",this){
      @Override
      protected void initialize() {
        super.initialize();

        DriverStation.reportWarning("["+TalonSubsystem.this.getName()+"] last setpoint = "+lastPosition,false);

        if(lastPosition == null){
          cancel();
        }
      }

      @Override
      protected void execute() {
        super.execute();
        if(lastPosition!= null){
          setPosition(lastPosition.value,lastPosition.motion);
        }
      }
    });

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
    talon.getStickyFaults(stickyFaults[0]);
    talon.getFaults(faults[0]);


    if(stickyFaults[0].toBitfield() != stickyFaults[1].toBitfield()){

      stickyFaults[1].update(stickyFaults[0].toBitfield());
      DriverStation.reportError(getName() + "STICKY FAULT: "+stickyFaults[1].toString(),false);
    }

    if(faults[0].toBitfield() != faults[1].toBitfield()){

      faults[1].update(faults[0].toBitfield());
      DriverStation.reportError(getName() + "FAULT: "+faults[1].toString(),false);

    }

  }

  public void replayLastSetpoint(){
    DriverStation.reportWarning("["+TalonSubsystem.this.getName()+"] Replay last setpoint = "+lastPosition,false);
    if(lastPosition!= null){
      setPosition(lastPosition.value,lastPosition.motion);
    }

  }


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


  public int getVelocity() {
    return talon.getSelectedSensorVelocity();
  }

  public int getPosition() {
    return talon.getSelectedSensorPosition();
  }


  public void setOutput(double output){
    talon.selectProfileSlot(TalonUtil.POSITION_SLOT,TalonUtil.PRIMARY_PID);
    talon.set(ControlMode.PercentOutput,output);
  }
  public void setPosition(double position){
    setPosition(position,true);
  }

  public void setPosition(double position, boolean useMotionProfiling){
    lastPosition = new TalonSetpointSpec(position,useMotionProfiling);

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
