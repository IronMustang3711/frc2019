package org.usfirst.frc3711.deepspace;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.CommandGroup;
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

 // final JoystickButton elevator;
//  final JoystickButton arm;
//  final JoystickButton wrist;

  final JoystickButton intakeInhale;
  final JoystickButton intakeExhale;
  // final JoystickButton fickleFingerHook;
  // final JoystickButton fickleFingerEject;
  // final JoystickButton fickleFingerHookEngage;
  final JoystickButton fickleFingerToggle;

  final JoystickButton stow;

//  final JoystickButton elevatorUp;
//  final JoystickButton elevatorDown;

  // final JoystickButton doglegDownButton;
  // final JoystickButton rearJackDownButton;
  // final JoystickButton liftingMechanismsUp;
  final JoystickButton jogUp;
  final JoystickButton jogDown;


  public OI() {

    joystick1 = new Joystick(0);

    stow = new JoystickButton(joystick1, 7);
    stow.whenPressed(new RobotPoser(RobotPose.STOW));

    jogUp = new JoystickButton(joystick1, 9);
    jogUp.whenPressed(new JogElevator(true));
    jogDown = new JoystickButton(joystick1, 10);
    jogDown.whenPressed(new JogElevator(false));

    Robot.elevator.tab.add(new JogElevator(true));
    Robot.elevator.tab.add(new JogElevator(false));


    Robot.fickleFinger.tab.add(FickleFingerCommands.ejectCommand());
    Robot.fickleFinger.tab.add(FickleFingerCommands.hookCommand());
    Robot.fickleFinger.tab.add(FickleFingerCommands.hookEngageCommand());
    Robot.fickleFinger.tab.add(FickleFingerCommands.fickleFingerToHome());
    Robot.fickleFinger.tab.add(FickleFingerCommands.fickleFingerExtend());


//    elevator = new JoystickButton(joystick1, 5);
//    elevator.whileHeld(new ManualTalonControl(Robot.elevator));

//    arm = new JoystickButton(joystick1, 3);
//    wrist = new JoystickButton(joystick1, 4);


    intakeInhale = new JoystickButton(joystick1, 1);
    intakeInhale.whileHeld(IntakeCommands.eject());

    intakeExhale = new JoystickButton(joystick1,2);
    intakeExhale.whileHeld(IntakeCommands.intake());


    // fickleFingerHook = new JoystickButton(joystick1, 5);
    // fickleFingerHook.whileHeld(FickleFingerCommands.hookCommand());

    // fickleFingerEject = new JoystickButton(joystick1,3);
    // fickleFingerEject.whileHeld(FickleFingerCommands.ejectCommand());

    // fickleFingerHookEngage = new JoystickButton(joystick1, 4);
    // fickleFingerHookEngage.whenPressed(FickleFingerCommands.hookEngageCommand());

    fickleFingerToggle = new JoystickButton(joystick1, 5);
    fickleFingerToggle.whenPressed(FickleFingerCommands.fickleFingerToggle());


//    elevatorUp = new JoystickButton(joystick1, 11);
//    elevatorDown = new JoystickButton(joystick1, 12);

    // rearJackDownButton = new JoystickButton(joystick1,4);
    // rearJackDownButton.whileHeld(RearJackCommands.runDown());

    // doglegDownButton = new JoystickButton(joystick1,6);
    // doglegDownButton.whileHeld(DogLegCommands.dogLegDown());

    // liftingMechanismsUp = new JoystickButton(joystick1,12);
    // liftingMechanismsUp.whileHeld(new CommandGroup("Lifters up"){
    //   {
    //     addParallel(DogLegCommands.runUp());
    //     addParallel(RearJackCommands.runUp());

    //   }
    // });




//    arm.whileHeld(new ManualTalonControl(Robot.arm));
//    wrist.whileHeld(new ManualTalonControl(Robot.wrist));


    SmartDashboard.putData(new InstantCommand("Zero Encoders",
        () -> Robot.subsystems.stream()
                  .filter(TalonSubsystem.class::isInstance)
                  .map(TalonSubsystem.class::cast)
                  .forEach(TalonSubsystem::zeroEncoder)));

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
    tab.add(new HatchPanel0_2());
    tab.add(new LoadingStationFuel());
    tab.add(new LoadingStationFuelToHome());
    tab.add(new HatchFuel1());
    tab.add(new Level1ToHome());
    tab.add(new HatchPanel1());
    tab.add(new HatchFuel2());
    tab.add(new HatchPanel2());
    tab.add(new GroundPickup());
    tab.add(new GroundPickup2());


    // tab.add(new CommandSequences.PingPong());

    tab.add(ZeroEncoder.resetAllEncoders());
    tab.add(Commands.disableAll());
  }

  public Joystick getJoystick1() {
    return joystick1;
  }

}

