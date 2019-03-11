package org.usfirst.frc3711.deepspace.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc3711.deepspace.Robot;
import org.usfirst.frc3711.deepspace.commands.util.TalonSubsystemCommand;
import org.usfirst.frc3711.deepspace.subsystems.TalonSubsystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.MyCommandGroup;
import edu.wpi.first.wpilibj.command.WaitForChildren;

public class MaintainSetpoint extends TalonSubsystemCommand {
    double target;
    public MaintainSetpoint(TalonSubsystem subsystem){
        super("Hold",subsystem);
    }

    @Override
    protected void initialize() {
        super.initialize();
        subsystem.talon.selectProfileSlot(1, 0);
        target = subsystem.talon.getClosedLoopTarget();
    }

    @Override
    protected void execute() {
        super.execute();
        subsystem.talon.set(ControlMode.Position, target);
    }

    @Override
    protected void end() {
        super.end();
        subsystem.talon.selectProfileSlot(1, 0);
    }


   public static class HoldAll extends MyCommandGroup {
        public HoldAll(){
            super("Hold All");
            addParallel(new MaintainSetpoint(Robot.arm));
            addParallel(new MaintainSetpoint(Robot.elevator));
            addParallel(new MaintainSetpoint(Robot.wrist));
            addSequential(new WaitForChildren());
            
        }


    }


}