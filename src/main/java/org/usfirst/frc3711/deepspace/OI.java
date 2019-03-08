package org.usfirst.frc3711.deepspace;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.POVButton;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


import org.usfirst.frc3711.deepspace.commands.*;
import org.usfirst.frc3711.deepspace.commands.sequences.*;
import org.usfirst.frc3711.deepspace.commands.util.Commands;
import org.usfirst.frc3711.deepspace.subsystems.TalonSubsystem;


@SuppressWarnings("WeakerAccess")
public class OI {

  static class MyButton extends Button {

    final int controllerId;
    final int buttonsMask;

    MyButton(int controller, int buttonsMask){
      this.controllerId = controller;
      this.buttonsMask = buttonsMask;
    }

    @Override
    public boolean get() {
    return (DriverStation.getInstance().getStickButtons(controllerId) == buttonsMask);
    }

  }

  static class XboxControl {
    static final int XBOX_ID = 1;
    XboxController xbox = new XboxController(XBOX_ID);

    Button groundPickup = new JoystickButton(xbox, 9);
    Button loadingStationPickup = new JoystickButton(xbox, 4);
    
    Button shootBall = new JoystickButton(xbox, 8);
    Button pullBall = new JoystickButton(xbox, 7);
    
    Button stow = new JoystickButton(xbox, 10);
    
    Button jogDown = new JoystickButton(xbox, 6);
    Button jogUp = new JoystickButton(xbox, 5);

    //POV quirks:
    //0 is vertical
    //angle increases clockwise
    static final int POV_RIGHT = 90;
    static final int POV_LEFT = 270;
    static final int POV_DOWN = 180;

    Button fickleFingerOut = new POVButton(xbox, POV_RIGHT);
    Button fickleFingerReallyHook = new POVButton(xbox, POV_LEFT);
    Button fickleFingerEject = new POVButton(xbox, POV_DOWN);
   
    Button level0Fuel = new MyButton(XBOX_ID,1<<0); // button 1
    Button level1Fuel = new MyButton(XBOX_ID,1<<1); //button 2
    Button level2Fuel = new MyButton(XBOX_ID,1<<2); //button 3

    Button level0Panel = new MyButton(XBOX_ID,(1<<0 | 1<<10)); //left stick(btn 11) down & 1
    Button level1Panel = new MyButton(XBOX_ID,(1<<1 | 1<<10)); //...
    Button level2Panel = new MyButton(XBOX_ID,(1<<2 | 1<<10));


    XboxControl(){
      groundPickup.whenPressed(new GroundPickup());
      loadingStationPickup.whenPressed(new LoadingStationFuel());
      
      shootBall.whenPressed(IntakeCommands.eject());
      pullBall.whenPressed(IntakeCommands.intake());

      stow.whenPressed(new Stow());

      jogUp.whileHeld(new JogElevatorContinuously(true));
      jogDown.whileHeld(new JogElevatorContinuously(false));


     
      level0Fuel.whenPressed(new HatchFuel0());
      level1Fuel.whenPressed(new HatchFuel1());
      level2Fuel.whenPressed(new HatchFuel2());
     
      level0Panel.whenPressed(new HatchPanel0());
      level1Panel.whenPressed(new HatchPanel1());
      level2Panel.whenPressed(new HatchPanel2());
   
      fickleFingerEject.whileHeld(FickleFingerCommands.ejectCommand());
      fickleFingerOut.whenPressed(FickleFingerCommands.hookCommand());
      fickleFingerReallyHook.whenPressed(FickleFingerCommands.hookEngageCommand());
    }
  }



  public final Joystick joystick1;
  public final XboxControl xbox ;

 // final JoystickButton elevator;
//  final JoystickButton arm;
//  final JoystickButton wrist;

  final JoystickButton intakeInhale;
  final JoystickButton intakeExhale;
//   final JoystickButton fickleFingerHook;
//   final JoystickButton fickleFingerEject;
//   final JoystickButton fickleFingerHookEngage;
  //final JoystickButton fickleFingerToggle;

  final JoystickButton stow;

//  final JoystickButton elevatorUp;
//  final JoystickButton elevatorDown;

  final JoystickButton doglegDownButton;
  final JoystickButton doglegUpButton;
   final JoystickButton rearJackDownButton;
   final JoystickButton rearJackUp;
  final JoystickButton jogUp;
  final JoystickButton jogDown;


  public OI() {

    joystick1 = new Joystick(0);
    xbox = new XboxControl();

    stow = new JoystickButton(joystick1, 7);
    stow.whenPressed(new Stow());

    jogUp = new JoystickButton(joystick1, 9);
    //jogUp.whenPressed(new JogElevator(true));
    jogUp.whileHeld(new JogElevatorContinuously(true));
    jogDown = new JoystickButton(joystick1, 10);
    //jogDown.whenPressed(new JogElevator(false));
    jogDown.whileHeld(new JogElevatorContinuously(false));

    Robot.elevator.tab.add(new JogElevator(true));
    Robot.elevator.tab.add(new JogElevator(false));


//    Robot.fickleFinger.tab.add(FickleFingerCommands.ejectCommand());
//    Robot.fickleFinger.tab.add(FickleFingerCommands.hookCommand());
//    Robot.fickleFinger.tab.add(FickleFingerCommands.hookEngageCommand());
//    Robot.fickleFinger.tab.add(FickleFingerCommands.fickleFingerToHome());
//    Robot.fickleFinger.tab.add(FickleFingerCommands.fickleFingerExtend());


//    elevator = new JoystickButton(joystick1, 5);
//    elevator.whileHeld(new ManualTalonControl(Robot.elevator));

//    arm = new JoystickButton(joystick1, 3);
//    wrist = new JoystickButton(joystick1, 4);


    intakeInhale = new JoystickButton(joystick1, 1);
    intakeInhale.whileHeld(IntakeCommands.eject());

    intakeExhale = new JoystickButton(joystick1,2);
    intakeExhale.whileHeld(IntakeCommands.intake());


//     fickleFingerHook = new JoystickButton(joystick1, 5);
//     fickleFingerHook.whileHeld(FickleFingerCommands.hookCommand());
//
//     fickleFingerEject = new JoystickButton(joystick1,3);
//     fickleFingerEject.whileHeld(FickleFingerCommands.ejectCommand());
//
//     fickleFingerHookEngage = new JoystickButton(joystick1, 4);
//     fickleFingerHookEngage.whenPressed(FickleFingerCommands.hookEngageCommand());

//    fickleFingerToggle = new JoystickButton(joystick1, 5);
//    fickleFingerToggle.whenPressed(FickleFingerCommands.fickleFingerToggle());


//    elevatorUp = new JoystickButton(joystick1, 11);
//    elevatorDown = new JoystickButton(joystick1, 12);

     rearJackDownButton = new JoystickButton(joystick1,4);
     rearJackDownButton.whileHeld(RearJackCommands.runDown());

     doglegDownButton = new JoystickButton(joystick1,6);
     doglegDownButton.whileHeld(DogLegCommands.dogLegDown());

     doglegUpButton = new JoystickButton(joystick1,11);
     doglegUpButton.whileHeld(DogLegCommands.runUp());

    rearJackUp = new JoystickButton(joystick1,12);
    rearJackUp.whileHeld(RearJackCommands.runUp());




//    arm.whileHeld(new ManualTalonControl(Robot.arm));
//    wrist.whileHeld(new ManualTalonControl(Robot.wrist));


    SmartDashboard.putData(new InstantCommand("Zero Encoders",
        () -> Robot.subsystems.stream()
                  .filter(TalonSubsystem.class::isInstance)
                  .map(TalonSubsystem.class::cast)
                  .forEach(TalonSubsystem::zeroEncoder)));

    SmartDashboard.putData(new InstantCommand("Disable all", Robot::disableAll));


    // SmartDashboard Buttons
   // SmartDashboard.putData("Drive with Joystick", new DrivewithJoystick());

    if(Robot.debug) {
      var tab = Shuffleboard.getTab("Poses");
      var subsys = tab.getLayout("Subsystems", BuiltInLayouts.kList);
      subsys.add(Robot.arm);
      subsys.add(Robot.wrist);
      subsys.add(Robot.elevator);

      tab.add(new Stow());
      tab.add(new StagingPose());
      tab.add(new HatchFuel0());
      tab.add(new HatchPanel0());
      tab.add(new LoadingStationFuel());
      tab.add(new LoadingStationFuelToHome());
      tab.add(new HatchFuel1());
      tab.add(new Level1ToHome());
      tab.add(new HatchPanel1());
      tab.add(new HatchFuel2());
      tab.add(new HatchPanel2());
      tab.add(new GroundPickup());
      // tab.add(new CommandSequences.PingPong());

      tab.add(ZeroEncoder.resetAllEncoders());
      tab.add(Commands.disableAll());
    }



  }

  public Joystick getJoystick1() {
    return joystick1;
  }

}

