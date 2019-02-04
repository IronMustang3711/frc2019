package org.usfirst.frc3711.FRC2019.commands;


import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc3711.FRC2019.Robot;
import org.usfirst.frc3711.FRC2019.subsystems.TalonSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ManualClosedLoopLinkageControl extends Command {


    private final TalonSubsystem subsystem;

    public ManualClosedLoopLinkageControl(TalonSubsystem subsystem) {
      this.subsystem = subsystem;
      requires(subsystem);
      requires(Robot.chassis);
    }

    @Override
    protected void initialize() {
      subsystem.talon.config_kP(0, 1);
      subsystem.talon.config_kF(0, 1, 10);
    }

    @Override
    protected void execute() {
        double output = Robot.oi.joystick1.getY();

        output *= -2000;
    
        SmartDashboard.putNumber(subsystem.getName() + " output",output);
    
        subsystem.talon.set(ControlMode.Position,output);
    
        
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}
