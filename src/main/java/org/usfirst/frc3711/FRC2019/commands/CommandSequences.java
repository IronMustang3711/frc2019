package org.usfirst.frc3711.FRC2019.commands;

import org.usfirst.frc3711.FRC2019.Robot;
import org.usfirst.frc3711.FRC2019.RobotPose;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class CommandSequences {

	public static Command elevatorToHome() {
		return new MotionMagicSetpoint("Home", Robot.elevator, 2000, 1.5);
	}

	public static class PingPong extends Command{
		Ping ping;
		Pong pong;
		public PingPong(){
			super("PingPong");
			ping = new Ping();
			pong = new Pong();
			ping.pong = pong;
			pong.ping = ping;
		}

		@Override
		protected void initialize() {
			Robot.arm.disableCurrentLimiting();
			ping.start();
		}

		@Override
		protected void end() {
			ping.cancel();
			pong.cancel();
			Robot.arm.enableCurrentLimiting();
		}

		@Override
		protected boolean isFinished() {
			return false;
		}
	}

	public static class Ping extends MotionMagicSetpoint {
		Command pong;
		public Ping(){
			super("Ping", Robot.arm, 2000,5.0);
		}
		@Override
		protected void end() {
			super.end();
			if(!isCanceled())
				pong.start();
		}
	}

	
	public static class Pong extends MotionMagicSetpoint {
		Command ping;
		public Pong(){
			super("Pong", Robot.arm, 0,5.0);
		}

		@Override
		protected void end() {
			super.end();
			if(!isCanceled())
				ping.start();
		}
	}

	public static class Hatch0 extends CommandGroup {
		public Hatch0(){
		super(Hatch0.class.getSimpleName());
		requires(Robot.arm);
		requires(Robot.wrist);
		requires(Robot.elevator);


		double elevatorUpTimeout = 1.0;
		double elevatorPosition = 2000;
		
		addParallel(new MotionMagicSetpoint("Wrist Vertical",Robot.wrist,10),elevatorUpTimeout);
		addParallel(new MotionMagicSetpoint("Arm Vertical",Robot.arm,10),elevatorUpTimeout);
		addSequential(
			new MotionMagicSetpoint("bring elevator up", Robot.elevator, elevatorPosition,0.5){
				@Override
				protected boolean isFinished() {
					return isTimedOut() && Math.abs(subsystem.talon.getSelectedSensorVelocity()) < 2.0 ;
				}
			}
		);


		addParallel(new MotionMagicSetpoint("Hold Elevator Position", Robot.elevator, elevatorPosition));
		addParallel(new MotionMagicSetpoint("Bring arm out", Robot.arm, 600));		
		addSequential(new MotionMagicSetpoint("Hold Wrist",Robot.wrist,-10));


		}

		@Override
		protected void end() {
			super.end();
			new RobotPoser(RobotPose.STOW).start();
		}

	}

	public static class Hatch1 extends CommandGroup {
		public Hatch1(){
			super(Hatch1.class.getSimpleName());
			requires(Robot.arm);
			requires(Robot.wrist);
			requires(Robot.elevator);
	
	
			double elevatorUpTimeout = 1.4;
			double elevatorPosition = 7000;
			
			addParallel(new MotionMagicSetpoint("Wrist Vertical",Robot.wrist,100),elevatorUpTimeout);
			addParallel(new MotionMagicSetpoint("Arm Vertical",Robot.arm,40),elevatorUpTimeout);
			addSequential(
			new MotionMagicSetpoint("bring elevator up", Robot.elevator, elevatorPosition,0.5){
				@Override
				protected boolean isFinished() {
					return isTimedOut() && Math.abs(subsystem.talon.getSelectedSensorVelocity()) <  2 ;
				}
			}
		);
	
			addParallel(new MotionMagicSetpoint("Hold Elevator Position", Robot.elevator, elevatorPosition));
			addParallel(new MotionMagicSetpoint("Hold Wrist",Robot.wrist,-10));
			addParallel(new MotionMagicSetpoint("Bring arm out", Robot.arm, 600));
	
			}
	
			@Override
			protected void end() {
				super.end();
				new RobotPoser(RobotPose.STOW).start();
			}


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

}
