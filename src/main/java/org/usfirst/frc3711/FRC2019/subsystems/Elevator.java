package org.usfirst.frc3711.FRC2019.subsystems;


import org.usfirst.frc3711.FRC2019.TalonID;


public class Elevator extends LinkageSubsystem {
    public Elevator() {
     super(Elevator.class.getSimpleName(), TalonID.ELEVATOR.getId());
    }

    @Override
    public void initDefaultCommand() {

    }

    @Override
    public void periodic() {
     super.periodic();
    }

}

