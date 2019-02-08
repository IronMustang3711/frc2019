// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc3711.FRC2019.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc3711.FRC2019.TalonID;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class Intake extends TalonSubsystem {


    public Intake() {
        super(Intake.class.getSimpleName(),TalonID.INTAKE.getId());

    }

    @Override
    public void initDefaultCommand() {

    }

    @Override
    public void periodic() {
       super.periodic();

    }

    public boolean isRunning(){
        return talon.getOutputCurrent() != 0;
    }

    public void run(){
        talon.set(ControlMode.PercentOutput, 1.0);
    }

    public void inhale(){
        talon.set(ControlMode.PercentOutput,1.0);
    }

    public void exhale(){
        talon.set(ControlMode.PercentOutput,-1.0);
    }

    public void stop(){
        talon.neutralOutput();
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}

