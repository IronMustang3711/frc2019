package org.usfirst.frc3711.deepspace.subsystems;

import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import org.usfirst.frc3711.deepspace.TalonID;
import org.usfirst.frc3711.deepspace.commands.ArcadeDrive;
import org.usfirst.frc3711.deepspace.commands.CurvatureDrive;

import java.util.Arrays;
import java.util.List;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

public class Chassis extends RobotSubsystem {


  private final WPI_TalonSRX leftFront;
  private final WPI_TalonSRX leftRear;

  private final WPI_TalonSRX rightFront;
  private final WPI_TalonSRX rightRear;

  private final DifferentialDrive drive;
  public final ShuffleboardTab tab;
  //private NetworkTableEntry leftOutput, leftRearOutput, rightOutput,rightRearOutput;


  private final List<WPI_TalonSRX> talons;


  public Chassis() {
    leftFront = new WPI_TalonSRX(TalonID.LEFT_FRONT.getId());
    addChild("left front", leftFront);

    leftRear = new WPI_TalonSRX(TalonID.LEFT_REAR.getId());
    addChild("left rear", leftRear);

    rightFront = new WPI_TalonSRX(TalonID.RIGHT_FRONT.getId());
    addChild("right front", rightFront);

    rightRear = new WPI_TalonSRX(TalonID.RIGHT_REAR.getId());
    addChild("right rear", rightRear);


    talons = Arrays.asList(leftFront, leftRear, rightFront, rightRear);


    drive = new DifferentialDrive(leftFront, rightFront);
    addChild("Drive", drive);

    drive.setSafetyEnabled(false);


    drive.setExpiration(0.1);
    drive.setMaxOutput(1.0);

    drive.setRightSideInverted(false);

    configureTalons();

    tab = Shuffleboard.getTab(Chassis.class.getSimpleName());

    var leftContainer = tab.getLayout("left", BuiltInLayouts.kList);
    leftContainer.add(leftFront);
    leftContainer.add(leftRear);

    var rightContainer = tab.getLayout("right", BuiltInLayouts.kList);
    rightContainer.add(rightFront);
    rightContainer.add(rightRear);


    tab.add(drive);

    tab.add(new ArcadeDrive());
    tab.add(new CurvatureDrive());
    //  tab.add(new DrivewithJoystick());

  }

  private void configureTalons() {

    for (var talon : talons) {
      talon.setSafetyEnabled(false);
      talon.configFactoryDefault();

      talon.configOpenloopRamp(0.0);
      talon.setNeutralMode(NeutralMode.Brake);
      talon.configNeutralDeadband(0.04);
      talon.configNominalOutputForward(0.15);
      talon.configNominalOutputReverse(-0.15);


    }


    rightRear.follow(rightFront);
    leftRear.follow(leftFront);

    rightFront.setInverted(true);
    leftFront.setInverted(false);

    rightRear.setInverted(InvertType.FollowMaster);
    leftRear.setInverted(InvertType.FollowMaster);

    rightFront.setSensorPhase(true);
    leftFront.setSensorPhase(true);


  }

  private void telemetry() {

    Faults leftFaults = new Faults();
    Faults rightFaults = new Faults();
    leftFront.getFaults(leftFaults);
    rightFront.getFaults(rightFaults);
    if (leftFaults.hasAnyFault()) {
      DriverStation.reportWarning(leftFaults.toString(), false);
    }
    if (rightFaults.hasAnyFault()) {
      DriverStation.reportWarning(rightFaults.toString(), false);
    }
  }

  public void arcadeDrive(double forward, double turn) {
    drive.arcadeDrive(forward, turn);
  }
  public void curvatureDrive(double fwd,double rotate,boolean quickTurn){
    drive.curvatureDrive(fwd, rotate, quickTurn);
  }
  public void curvatureDrive(double fwd,double rotate){
    curvatureDrive(fwd, rotate,false);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ArcadeDrive());
  }

  @Override
  public void periodic() {
    telemetry();
    double quickStopAlpha = Preferences.getInstance().getDouble("chassis.quickStopAlpha", DifferentialDrive.kDefaultQuickStopAlpha);
    double quickStopThreshold = Preferences.getInstance().getDouble("chassis.quickStopThreshold",DifferentialDrive.kDefaultQuickStopThreshold);
  
    drive.setQuickStopAlpha(quickStopAlpha);
    drive.setQuickStopThreshold(quickStopThreshold);
  }

  @Override
  public void disable() {
    super.disable();
    leftFront.neutralOutput();
    rightFront.neutralOutput();
  }
}

