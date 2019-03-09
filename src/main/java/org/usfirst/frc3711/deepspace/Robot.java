// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc3711.deepspace;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobotBase;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc3711.deepspace.subsystems.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;


public class Robot extends TimedRobot {

  public static final boolean debug = true;

  public static OI oi;
  public static Chassis chassis;
  public static Elevator elevator;
  public static Arm arm;
  public static Wrist wrist;
  public static Intake intake;
  // public static Misc misc;
  public static FickleFinger fickleFinger;
  public static DogLeg dogLeg;
  public static RearJack rearJack;
  public static Lights lights;

  public static RobotPoser poser;

  public static List<RobotSubsystem> subsystems;

  private long disableStartTime;


  private void disableWatchdog() {
    try {
      Field watchdogField = IterativeRobotBase.class.getDeclaredField("m_watchdog");
      watchdogField.setAccessible(true);
      Watchdog watchdog = (Watchdog) watchdogField.get(this);
      watchdog.setTimeout(1e8);
      watchdog.close();
    } catch (IllegalAccessException | NoSuchFieldException e) {
      System.err.println("Watchdog disable failed :(");
    }
  }

  public Robot() {
    super(0.02);
    disableWatchdog();


  }

  @Override
  public void robotInit() {

    chassis = new Chassis();
    elevator = new Elevator();
    arm = new Arm();
    wrist = new Wrist();
    intake = new Intake();
    // misc = new Misc();
    fickleFinger = new FickleFinger();
    dogLeg = new DogLeg();
    rearJack = new RearJack();
    poser = new RobotPoser();
    lights = new Lights();

    subsystems = Arrays.asList(chassis, elevator, arm, wrist, intake/*,misc*/, fickleFinger, dogLeg, rearJack,poser, lights);


    oi = new OI();

    CameraServer.getInstance().startAutomaticCapture();
  }

  @Override
  public void disabledInit() {
    Shuffleboard.stopRecording();
    disableAll();
    disableStartTime = System.currentTimeMillis();
  }

  public static void disableAll() {
    Robot.subsystems.forEach(RobotSubsystem::disable);
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
    if((System.currentTimeMillis() - disableStartTime) > 5000){
      subsystems.stream()
        .filter(TalonSubsystem.class::isInstance)
        .map(TalonSubsystem.class::cast)
        .forEach(subsystem -> subsystem.setNeutralMode(NeutralMode.Coast));
    }
  }

  @Override
  public void autonomousInit() {
    subsystems.stream()
        .filter(TalonSubsystem.class::isInstance)
        .map(TalonSubsystem.class::cast)
        .forEach(subsystem -> subsystem.setNeutralMode(NeutralMode.Brake));
  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    Shuffleboard.startRecording();
    subsystems.stream()
        .filter(TalonSubsystem.class::isInstance)
        .map(TalonSubsystem.class::cast)
        .forEach(subsystem -> subsystem.setNeutralMode(NeutralMode.Brake));
    lights.updateAllianceColor();
  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void robotPeriodic() {
//    SmartDashboard.putString("buttons",
//    Integer.toBinaryString(DriverStation.getInstance().getStickButtons(1)));
  }
}
