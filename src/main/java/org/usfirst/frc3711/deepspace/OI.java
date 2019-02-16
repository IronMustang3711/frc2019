package org.usfirst.frc3711.deepspace;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc3711.deepspace.commands.*;
import org.usfirst.frc3711.deepspace.commands.sequences.*;
import org.usfirst.frc3711.deepspace.commands.util.Commands;
import org.usfirst.frc3711.deepspace.subsystems.TalonSubsystem;

import java.util.Arrays;


@SuppressWarnings("WeakerAccess")
public class OI {



  public final Joystick joystick1;

  final JoystickButton elevator;
//  final JoystickButton arm;
//  final JoystickButton wrist;

  final JoystickButton intakeInhale;
  final JoystickButton intakeExhale;
  final JoystickButton fickleFingerHook;
  final JoystickButton fickleFingerEject;

  final JoystickButton stow;

  final JoystickButton elevatorUp;
  final JoystickButton elevatorDown;

  final JoystickButton doglegDownButton;
  final JoystickButton rearJackDownButton;


  public OI() {

    joystick1 = new Joystick(0);

    stow = new JoystickButton(joystick1, 7);

    elevator = new JoystickButton(joystick1, 5);
//    arm = new JoystickButton(joystick1, 3);
//    wrist = new JoystickButton(joystick1, 4);


    intakeInhale = new JoystickButton(joystick1, 1);
    intakeExhale = new JoystickButton(joystick1,2);

    fickleFingerHook = new JoystickButton(joystick1, 5);
    fickleFingerEject = new JoystickButton(joystick1,3);

    elevatorUp = new JoystickButton(joystick1, 11);
    elevatorDown = new JoystickButton(joystick1, 12);

    rearJackDownButton = new JoystickButton(joystick1,4);
    doglegDownButton = new JoystickButton(joystick1,6);


    stow.whenPressed(new RobotPoser(RobotPose.STOW));


    elevator.whileHeld(new ManualTalonControl(Robot.elevator));
//    arm.whileHeld(new ManualTalonControl(Robot.arm));
//    wrist.whileHeld(new ManualTalonControl(Robot.wrist));

    intakeInhale.whileHeld(IntakeCommands.eject());
    intakeExhale.whileHeld(IntakeCommands.intake());

    fickleFingerHook.whileHeld(FickleFingerCommands.hookingDirectionCommand());
    fickleFingerEject.whileHeld(FickleFingerCommands.ejectingDirectionCommand());


    SmartDashboard.putData(new InstantCommand("Disable all", Robot::disableAll));


    // SmartDashboard Buttons
    SmartDashboard.putData("Drive with Joystick", new DrivewithJoystick());

    var tab = Shuffleboard.getTab("Poses");
    var subsys = tab.getLayout("Subsystems", BuiltInLayouts.kList);
    subsys.add(Robot.arm);
    subsys.add(Robot.wrist);
    subsys.add(Robot.elevator);

    tab.add(new RobotPoser(RobotPose.STOW));
    tab.add(new RestingPose());
    tab.add(new Resting2());
    tab.add(new StagingPose());
    tab.add(new HatchFuel0());
    tab.add(new HatchPanel0());
    tab.add(new LoadingStationFuel());
    tab.add(new LoadingStationFuelToHome());
    tab.add(new HatchFuel1());
    tab.add(new Level1ToHome());
    tab.add(new HatchPanel1());


    // tab.add(new CommandSequences.PingPong());

    tab.add(new InstantCommand("Zero Encoders", () -> {

      for (TalonSubsystem s : Arrays.asList(Robot.elevator, Robot.wrist, Robot.arm)) {
        var t = s.talon;
        t.setSelectedSensorPosition(0);
        t.getSensorCollection().setQuadraturePosition(0, 50);
      }
    }));
    tab.add(Commands.disableAll());
  }

  public Joystick getJoystick1() {
    return joystick1;
  }

}

