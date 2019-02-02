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


import org.usfirst.frc3711.FRC2019.TalonID;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Wrist extends LinkageSubsystem {



    public Wrist() {
     super(Wrist.class.getSimpleName(), TalonID.WRIST.getId());
    }

    @Override
    void configureTalon() {
        super.configureTalon();
        talon.setInverted(true);
        talon.setSensorPhase(false);
    }

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
       super.periodic();

       SmartDashboard.putNumber("Wrist position", talon.getSensorCollection().getQuadraturePosition());
       SmartDashboard.putNumber("Wrist position2", talon.getSelectedSensorPosition());

    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}

