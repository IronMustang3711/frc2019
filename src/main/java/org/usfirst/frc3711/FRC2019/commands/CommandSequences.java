package org.usfirst.frc3711.FRC2019.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc3711.FRC2019.Robot;
import org.usfirst.frc3711.FRC2019.subsystems.TalonSubsystem;

public class CommandSequences {

	public static Command elevatorToHome(){
		return new MotionMagicSetpoint("Home",Robot.elevator,500,1.5);
	}

	public static class RestingPose extends CommandGroup {

		public RestingPose(){
			super(RestingPose.class.getSimpleName());
			addSequential(elevatorToHome());
			addSequential(new MotionMagicSetpoint("Rest",Robot.arm,0));
			addParallel(new MotionMagicSetpoint("Rest",Robot.wrist,0));
			addSequential(new MotionMagicSetpoint("Rest",Robot.elevator,0));
		}

	}

	public static class StagingPose extends CommandGroup {
		public StagingPose(){
			super(StagingPose.class.getSimpleName());
			addSequential(elevatorToHome());
			addSequential(new MotionMagicSetpoint("Staging",Robot.arm,400));
			addParallel(new MotionMagicSetpoint("Staging",Robot.wrist,400));
			addParallel(new MotionMagicSetpoint("Home",Robot.elevator,500));
		}
	}

//	public static class RestPosition extends Command {
//		TalonSubsystem subsystem;
//		public RestPosition(TalonSubsystem sub){this(sub,1.0);}
//		public RestPosition(TalonSubsystem subsystem, double timeout) {
//			super(subsystem.getName()+"Rest",timeout,subsystem);
//			requires(subsystem);
//			this.subsystem = subsystem;
//		}
//
//		@Override
//		protected void execute() {
//			subsystem.talon.set(ControlMode.MotionMagic,)
//		}
//
//		@Override
//		protected boolean isFinished() {
//			return isTimedOut();
//		}
//	}
}
