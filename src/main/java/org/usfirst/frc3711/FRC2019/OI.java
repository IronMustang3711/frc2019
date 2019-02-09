// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc3711.FRC2019;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.usfirst.frc3711.FRC2019.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());


    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public Joystick joystick1;

    JoystickButton elevator;
    JoystickButton arm;
    JoystickButton wrist;

    JoystickButton intake;
    JoystickButton ejector;

    JoystickButton stow;

    JoystickButton elevatorUp;
    JoystickButton elevatorDown;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public OI() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        joystick1 = new Joystick(0);

        stow = new JoystickButton(joystick1, 7);

        elevator = new JoystickButton(joystick1,5);
        arm = new JoystickButton(joystick1,3);
        wrist = new JoystickButton(joystick1,4);
       
        intake = new JoystickButton(joystick1,1);
        ejector = new JoystickButton(joystick1, 2);


        elevatorUp = new JoystickButton(joystick1, 11);
        elevatorDown = new JoystickButton(joystick1, 12);


//        MotionMagicSetpoint top = new MotionMagicSetpoint(Robot.elevator);
//        top.setSetpoint(7000);
//        elevatorUp.whenPressed(top);
//
//        MotionMagicSetpoint home = new MotionMagicSetpoint(Robot.elevator);
//        home.setSetpoint(1000);
//        elevatorDown.whenPressed(home);


        stow.whenPressed(new RobotPoser(RobotPose.STOW));
        elevator.whileHeld(new ManualLinkageControl(Robot.elevator));
        arm.whileHeld(new ManualLinkageControl(Robot.arm));
        wrist.whileHeld(new ManualLinkageControl(Robot.wrist));
       
        intake.whileHeld(new RunIntake());
        ejector.whileHeld(new RunEjector());



        SmartDashboard.putData(new InstantCommand("Disable all",Robot::disableAll));

        


        // SmartDashboard Buttons
        SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
        SmartDashboard.putData("Drive with Joystick", new DrivewithJoystick());

       var tab = Shuffleboard.getTab("Poses");
       tab.add(new RobotPoser(RobotPose.STOW));
       tab.add(new CommandSequences.RestingPose());
       tab.add(new CommandSequences.StagingPose());


       stow.whenPressed(new RobotPoser(RobotPose.STOW));

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    public Joystick getJoystick1() {
        return joystick1;
    }


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
}

