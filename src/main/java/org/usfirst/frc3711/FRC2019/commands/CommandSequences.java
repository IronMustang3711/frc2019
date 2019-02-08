package org.usfirst.frc3711.FRC2019.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc3711.FRC2019.Robot;
import org.usfirst.frc3711.FRC2019.subsystems.TalonSubsystem;

public class CommandSequences {

	public static Command elevatorToHome(){
		return new MotionMagicSetpoint("Home",Robot.elevator,2000,1.5);
	}

	public static class RestingPose extends CommandGroup {

		public RestingPose(){
			super(RestingPose.class.getSimpleName());
			requires(Robot.arm);
			requires(Robot.wrist);
			requires(Robot.elevator);

			addParallel(new MotionMagicSetpoint("Rest",Robot.wrist,0,2.0));
			addSequential(new MotionMagicSetpoint("Home",Robot.elevator,2000,2.0));

			addSequential(new MotionMagicSetpoint("Rest",Robot.arm,0));
			addSequential(new MotionMagicSetpoint("Rest",Robot.elevator,0));


		}

	}

	public static class StagingPose extends CommandGroup {
		public StagingPose(){
			super(StagingPose.class.getSimpleName());
			requires(Robot.arm);
			requires(Robot.wrist);
			requires(Robot.elevator);

			addParallel(new MotionMagicSetpoint("0",Robot.wrist,0,2.0));
			addParallel(new MotionMagicSetpoint("0",Robot.arm,0,2.0));
			addSequential(new MotionMagicSetpoint("Home",Robot.elevator,2000,2.0));


			addParallel(new MotionMagicSetpoint("Staging",Robot.wrist,0));
			addParallel(new MotionMagicSetpoint("Home",Robot.elevator,2000));
			addSequential(new MotionMagicSetpoint("Staging",Robot.arm,800));

		}

		@Override
		protected void end() {
			Robot.elevator.talon.neutralOutput();
			Robot.arm.talon.neutralOutput();
			Robot.wrist.talon.neutralOutput();
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
