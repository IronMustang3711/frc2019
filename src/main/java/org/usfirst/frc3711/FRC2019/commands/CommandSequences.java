package org.usfirst.frc3711.FRC2019.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.shuffleboard.EventImportance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc3711.FRC2019.Robot;

public class CommandSequences {

	/*
	hatch 0 fuel: 13000, 200, -600
	cargo station fuel: -4000, 3133, -2949
	hatch 1 fuel : 4000, 3133,-2949
	hatch 1 panel : -7688,3071,-1996

	*/


  public static Command elevatorToHome() {
    return new MotionMagicSetpoint("Home", Robot.elevator, 2000, 1.5);
  }

  public static class PingPong extends Command {
    Ping ping;
    Pong pong;

    public PingPong() {
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

    public Ping() {
      super("Ping", Robot.arm, 2000, 5.0);
    }

    @Override
    protected void end() {
      super.end();
      if (!isCanceled())
        pong.start();
    }
  }


  public static class Pong extends MotionMagicSetpoint {
    Command ping;

    public Pong() {
      super("Pong", Robot.arm, 0, 5.0);
    }

    @Override
    protected void end() {
      super.end();
      if (!isCanceled())
        ping.start();
    }
  }

  public static class HatchPanel0 extends Command {
    Command wristVertical = new MotionMagicSetpoint("Wrist Vertical", Robot.wrist, 10,1.0);
    Command armVertical = new MotionMagicSetpoint("Arm Vertical", Robot.arm, 10,1.0);
    MotionMagicSetpoint elevatorUp = new MotionMagicSetpoint("bring elevator up", Robot.elevator, 2000, 1.0){

      @Override
      protected void execute() {
        super.execute();
//        double motionProgress = getMotionProgress();
//        System.out.println("Motion progress: "+motionProgress);
//        DriverStation.reportWarning("Motion progress: "+motionProgress,false);
//        SmartDashboard.putNumber("motion progress",motionProgress);
      }

      @Override
      protected boolean isFinished() {
        double motionProgress = getMotionProgress();
        //System.out.println("Motion progress: "+motionProgress);
        return motionProgress >= 0.99 || super.isFinished();
      }
    };

    Command elevatorHold = Commands.runWhenTrue(Commands.constantOutput(Robot.elevator,0.1),
        ()-> !elevatorUp.isRunning());

    Command armOut = Commands.runWhenTrue(new MotionMagicSetpoint("Bring arm out", Robot.arm, 600),
        () -> elevatorUp.getMotionProgress() >= 0.5);



    public HatchPanel0() {
      super(HatchPanel0.class.getSimpleName());

    }

    @Override
    protected void initialize() {
      super.initialize();
      Shuffleboard.addEventMarker(getName() + "_Init", EventImportance.kNormal);

      wristVertical.start();
      armVertical.start();
      elevatorUp.start();
      elevatorHold.start();
      armOut.start();

    }

    /**
     * Returns whether this command is finished. If it is, then the command will be removed and {@link
     * Command#end() end()} will be called.
     *
     * <p>It may be useful for a team to reference the {@link Command#isTimedOut() isTimedOut()}
     * method for time-sensitive commands.
     *
     * <p>Returning false will result in the command never ending automatically. It may still be
     * cancelled manually or interrupted by another command. Returning true will result in the
     * command executing once and finishing immediately. We recommend using {@link InstantCommand}
     * for this.
     *
     * @return whether this command is finished.
     * @see Command#isTimedOut() isTimedOut()
     */
    @Override
    protected boolean isFinished() {
      return false;
    }

    @Override
    protected void end() {
      super.end();
      Shuffleboard.addEventMarker(getName() + "_End", EventImportance.kNormal);
     // RestingPose.run();
    }

  }

  public static class HatchFuel0 extends Command {

    Command wristVertical =  new MotionMagicSetpoint("Wrist Vertical", Robot.wrist, 90);
    Command armVertical = new MotionMagicSetpoint("Arm Vertical", Robot.arm, 40);
    MotionMagicSetpoint elevatorUp =  new MotionMagicSetpoint("bring elevator up", Robot.elevator, 13000, 2.5) {
      @Override
      protected boolean isFinished() {
        return getMotionProgress() >= .99 || super.isFinished();
      }
    };

    Command wristDown = Commands.runWhenTrue(new MotionMagicSetpoint("Wrist Down", Robot.wrist, -600),
        ()-> elevatorUp.getMotionProgress() > 0.7);

    Command armOut = Commands.runWhenTrue(new MotionMagicSetpoint("Bring  Out", Robot.arm, 200),
        ()->elevatorUp.getMotionProgress() >= 0.7);

    public HatchFuel0() {
      super(HatchFuel0.class.getSimpleName());
//      requires(Robot.arm);
//      requires(Robot.wrist);
//      requires(Robot.elevator);

//
//      double elevatorUpTimeout = 1.7;
//      double elevatorPosition = 13000;
//
//      addParallel(new MotionMagicSetpoint("Wrist Vertical", Robot.wrist, 90), elevatorUpTimeout);
//      addParallel(new MotionMagicSetpoint("Arm Vertical", Robot.arm, 40), elevatorUpTimeout);
//      addSequential(
//          new MotionMagicSetpoint("bring elevator up", Robot.elevator, elevatorPosition, 2.5) {
//            @Override
//            protected boolean isFinished() {
//              return super.isFinished()
//                         || getElapsedTime() > 0.5 && Math.abs(subsystem.talon.getErrorDerivative()) < 1.0
//                         || Math.abs(subsystem.talon.getClosedLoopError()) < 100;
//            }
//          }
//      );

//      //addParallel(new MotionMagicSetpoint("Hold Elevator Position", Robot.elevator, elevatorPosition));
//      addParallel(new MotionMagicSetpoint("Wrist Down", Robot.wrist, -600));
//      addParallel(new MotionMagicSetpoint("Bring  Out", Robot.arm, 200));

    }

    @Override
    protected void initialize() {
      super.initialize();
      Shuffleboard.addEventMarker(getName() + "_Init", EventImportance.kNormal);
      wristVertical.start();
      armVertical.start();
      elevatorUp.start();
      wristDown.start();
      armOut.start();

    }

    /**
     * Returns whether this command is finished. If it is, then the command will be removed and {@link
     * Command#end() end()} will be called.
     *
     * <p>It may be useful for a team to reference the {@link Command#isTimedOut() isTimedOut()}
     * method for time-sensitive commands.
     *
     * <p>Returning false will result in the command never ending automatically. It may still be
     * cancelled manually or interrupted by another command. Returning true will result in the
     * command executing once and finishing immediately. We recommend using {@link InstantCommand}
     * for this.
     *
     * @return whether this command is finished.
     * @see Command#isTimedOut() isTimedOut()
     */
    @Override
    protected boolean isFinished() {
      return false;
    }

    @Override
    protected void end() {
      super.end();
      Shuffleboard.addEventMarker(getName() + "_End", EventImportance.kNormal);
      RestingPose.run();
    }
  }


  public static class LoadingStationFuel extends CommandGroup {

    public LoadingStationFuel() {
      super(LoadingStationFuel.class.getSimpleName());
      requires(Robot.arm);
      requires(Robot.wrist);
      requires(Robot.elevator);

      double elevatorUpTimeout = 1.7;
      double elevatorPosition = 3000;

      // elevator up:
      addParallel(new MotionMagicSetpoint("Wrist Vertical", Robot.wrist, 90), elevatorUpTimeout);
      addParallel(new MotionMagicSetpoint("Arm Vertical", Robot.arm, 40), elevatorUpTimeout);
      addSequential(new MotionMagicSetpoint("bring elevator up", Robot.elevator, elevatorPosition, 1.5) {
        @Override
        protected boolean isFinished() {
          return super.isFinished() || getMotionProgress() >= 0.5;
          // return isTimedOut() && Math.abs(subsystem.talon.getErrorDerivative()) < 1.0
          // 		|| Math.abs(subsystem.talon.getClosedLoopError()) < 100;
        }
      });

      double armOutTimeout = 3.0;
      // Arm out, Wrist down
      addParallel(new MotionMagicSetpoint("Hold Elevator Position",
          Robot.elevator, elevatorPosition, armOutTimeout));
      //	addSequential(new MotionMagicSetpoint("Wrist Down",Robot.wrist,-2949),armOutTimeout);
      addSequential(new MotionMagicSetpoint("Arm Out", Robot.arm, 3133.0, 3.0) {
        @Override
        protected boolean isFinished() {
          return super.isFinished();
          // return isTimedOut() && Math.abs(subsystem.talon.getErrorDerivative()) < 1.0
          // 		|| Math.abs(subsystem.talon.getClosedLoopError()) < 150;
        }
      });
      addSequential(new MotionMagicSetpoint("Wrist Down", Robot.wrist, -2949), armOutTimeout);

      addSequential(new MotionMagicSetpoint("Elevator Down", Robot.elevator, -4000));

    }

    @Override
    protected void initialize() {
      super.initialize();
      Shuffleboard.addEventMarker(getName() + "_Init", EventImportance.kNormal);

    }

    @Override
    protected void end() {
      super.end();
      Shuffleboard.addEventMarker(getName() + "_End", EventImportance.kNormal);

      RestingPose.run();

    }
  }
  public static class LoadingStationFuelToHome extends CommandGroup {
    public LoadingStationFuelToHome(){
      super(LoadingStationFuelToHome.class.getSimpleName());
      addSequential(new MotionMagicSetpoint("Elevator up",Robot.elevator,4000,2.5));
      addParallel(new MotionMagicSetpoint("Wrist Home",Robot.wrist,10,2.5));
      addParallel(new MotionMagicSetpoint("Arm Home",Robot.arm,10,2.5));
    }
  }
  public static class HatchFuel1 extends CommandGroup {

    public HatchFuel1() {
      super(HatchFuel1.class.getSimpleName());
      requires(Robot.arm);
      requires(Robot.wrist);
      requires(Robot.elevator);

      double elevatorUpTimeout = 1.7;
      double elevatorPosition = 4000;

      // elevator up:
      addParallel(new MotionMagicSetpoint("Wrist Vertical", Robot.wrist, 90), elevatorUpTimeout);
      addParallel(new MotionMagicSetpoint("Arm Vertical", Robot.arm, 40), elevatorUpTimeout);
      addSequential(new MotionMagicSetpoint("bring elevator up", Robot.elevator, elevatorPosition, 1.5) {
        @Override
        protected boolean isFinished() {
          return super.isFinished();
          // return isTimedOut() && Math.abs(subsystem.talon.getErrorDerivative()) < 1.0
          // 		|| Math.abs(subsystem.talon.getClosedLoopError()) < 100;
        }
      });

      double armOutTimeout = 3.0;
      // Arm out, Wrist down
      addParallel(new MotionMagicSetpoint("Hold Elevator Position",
          Robot.elevator, elevatorPosition, armOutTimeout));
      //	addSequential(new MotionMagicSetpoint("Wrist Down",Robot.wrist,-2949),armOutTimeout);
      addSequential(new MotionMagicSetpoint("Arm Out", Robot.arm, 3133.0, 3.0) {
        @Override
        protected boolean isFinished() {
          return super.isFinished();
          // return isTimedOut() && Math.abs(subsystem.talon.getErrorDerivative()) < 1.0
          // 		|| Math.abs(subsystem.talon.getClosedLoopError()) < 150;
        }
      });
      addSequential(new MotionMagicSetpoint("Wrist Down", Robot.wrist, -2949), armOutTimeout);

      //addSequential(new MotionMagicSetpoint("Elevator Down", Robot.elevator, -4000));

    }

    @Override
    protected void initialize() {
      super.initialize();
      Shuffleboard.addEventMarker(getName() + "_Init", EventImportance.kNormal);

    }

    @Override
    protected void end() {
      super.end();
      Shuffleboard.addEventMarker(getName() + "_End", EventImportance.kNormal);

     // RestingPose.run();

    }
  }


  public static class HatchPanel1 extends CommandGroup {

    public HatchPanel1() {
      super(HatchPanel1.class.getSimpleName());
      requires(Robot.arm);
      requires(Robot.wrist);
      requires(Robot.elevator);

      double elevatorUpTimeout = 1.7;
      double elevatorPosition = 3000;

      // elevator up:
      addParallel(new MotionMagicSetpoint("Wrist Vertical", Robot.wrist, 90), elevatorUpTimeout);
      addParallel(new MotionMagicSetpoint("Arm Vertical", Robot.arm, 40), elevatorUpTimeout);
      addSequential(new MotionMagicSetpoint("bring elevator up", Robot.elevator, elevatorPosition, 1.5) {
        @Override
        protected boolean isFinished() {
          return super.isFinished();
          // return isTimedOut() && Math.abs(subsystem.talon.getErrorDerivative()) < 1.0
          // 		|| Math.abs(subsystem.talon.getClosedLoopError()) < 100;
        }
      });

      double armOutTimeout = 3.0;
      // Arm out, Wrist down
      addParallel(new MotionMagicSetpoint("Hold Elevator Position",
          Robot.elevator, elevatorPosition, armOutTimeout));
      //	addSequential(new MotionMagicSetpoint("Wrist Down",Robot.wrist,-2949),armOutTimeout);
      addSequential(new MotionMagicSetpoint("Arm Out", Robot.arm, 3017.0, 3.0) {
        @Override
        protected boolean isFinished() {
          return super.isFinished();
          // return isTimedOut() && Math.abs(subsystem.talon.getErrorDerivative()) < 1.0
          // 		|| Math.abs(subsystem.talon.getClosedLoopError()) < 150;
        }
      });
      addSequential(new MotionMagicSetpoint("Wrist Down", Robot.wrist, -1996), armOutTimeout);

      addSequential(new MotionMagicSetpoint("Elevator Down", Robot.elevator, -7688));

    }

    @Override
    protected void initialize() {
      super.initialize();
      Shuffleboard.addEventMarker(getName() + "_Init", EventImportance.kNormal);

    }

    @Override
    protected void end() {
      super.end();
      Shuffleboard.addEventMarker(getName() + "_End", EventImportance.kNormal);

     // RestingPose.run();

    }
  }

  // public static class LoadingStationFuel extends CommandGroup {

  // 	public LoadingStationFuel() {
  // 		super(LoadingStationFuel.class.getSimpleName());
  // 		requires(Robot.arm);
  // 		requires(Robot.wrist);
  // 		requires(Robot.elevator);

  // 		double elevatorUpTimeout = 1.7;
  // 		double elevatorPosition = 3000;

  // 		// elevator up:
  // 		addParallel(new MotionMagicSetpoint("Wrist Vertical", Robot.wrist, 90), elevatorUpTimeout);
  // 		addParallel(new MotionMagicSetpoint("Arm Vertical", Robot.arm, 40), elevatorUpTimeout);
  // 		addSequential(new MotionMagicSetpoint("bring elevator up", Robot.elevator, elevatorPosition, 1.5) {
  // 			@Override
  // 			protected boolean isFinished() {
  // 				return super.isFinished();
  // 				// return isTimedOut() && Math.abs(subsystem.talon.getErrorDerivative()) < 1.0
  // 				// 		|| Math.abs(subsystem.talon.getClosedLoopError()) < 100;
  // 			}
  // 		});

  // 		double armOutTimeout = 3.0;
  // 		// Arm out, Wrist down
  // 		 addParallel(new MotionMagicSetpoint("Hold Elevator Position",
  // 		 Robot.elevator,elevatorPosition, armOutTimeout));
  // 	//	addSequential(new MotionMagicSetpoint("Wrist Down",Robot.wrist,-2949),armOutTimeout);
  // 		addSequential(new MotionMagicSetpoint("Arm Out", Robot.arm, 3133.0, 3.0) {
  // 			@Override
  // 			protected boolean isFinished() {
  // 				return super.isFinished();
  // 				// return isTimedOut() && Math.abs(subsystem.talon.getErrorDerivative()) < 1.0
  // 				// 		|| Math.abs(subsystem.talon.getClosedLoopError()) < 150;
  // 			}
  // 		});
  // 		addSequential(new MotionMagicSetpoint("Wrist Down", Robot.wrist, -2949), armOutTimeout);

  // 		addSequential(new MotionMagicSetpoint("Elevator Down", Robot.elevator, -4000));

  // 	}

  // 	@Override
  // 	protected void initialize() {
  // 		super.initialize();
  // 		Shuffleboard.addEventMarker(getName() + "_Init", EventImportance.kNormal);

  // 	}

  // 	@Override
  // 	protected void end() {
  // 		super.end();
  // 		Shuffleboard.addEventMarker(getName() + "_End", EventImportance.kNormal);

  // 		RestingPose.run();

  // 	}
  // }


  public static class Resting2 extends CommandGroup {
    public Resting2() {
      super(Resting2.class.getSimpleName());
      requires(Robot.arm);
      requires(Robot.wrist);
      requires(Robot.elevator);

      addSequential(new MotionMagicSetpoint("Elevator->Rest", Robot.elevator, 100,1.5));
      addSequential(new MotionMagicSetpoint("Wrist->Rest", Robot.wrist, 0), 2.0);
      addSequential(new MotionMagicSetpoint("Arm->Rest", Robot.arm, 0), 2.0);

    }

  }

  public static class RestingPose extends CommandGroup {

    static final RestingPose INSTANCE = new RestingPose();

    static Command run() {
      INSTANCE.cancel();
      INSTANCE.start();
      return INSTANCE;
    }

    public RestingPose() {
      super(RestingPose.class.getSimpleName());
      requires(Robot.arm);
      requires(Robot.wrist);
      requires(Robot.elevator);

      addParallel(new MotionMagicSetpoint("Rest", Robot.wrist, 0, 2.0));
      addSequential(new MotionMagicSetpoint("Home", Robot.elevator, 2000, 2.0));

      addSequential(new MotionMagicSetpoint("Rest", Robot.arm, 0));
      addSequential(new MotionMagicSetpoint("Rest", Robot.elevator, 0));


    }

    @Override
    protected void initialize() {
      super.initialize();
      Shuffleboard.addEventMarker(getName() + "_Init", EventImportance.kNormal);
    }

    @Override
    protected void end() {
      super.end();
      Shuffleboard.addEventMarker(getName() + "_End", EventImportance.kNormal);

    }

  }

  public static class StagingPose extends CommandGroup {
    public StagingPose() {
      super(StagingPose.class.getSimpleName());
      requires(Robot.arm);
      requires(Robot.wrist);
      requires(Robot.elevator);

      addParallel(new MotionMagicSetpoint("0", Robot.wrist, 0, 2.0));
      addParallel(new MotionMagicSetpoint("0", Robot.arm, 0, 2.0));
      addSequential(new MotionMagicSetpoint("Home", Robot.elevator, 2000, 2.0));


      addParallel(new MotionMagicSetpoint("Staging", Robot.wrist, 0));
      addParallel(new MotionMagicSetpoint("Home", Robot.elevator, 2000));
      addSequential(new MotionMagicSetpoint("Staging", Robot.arm, 800));

    }

    @Override
    protected void initialize() {
      super.initialize();
      Shuffleboard.addEventMarker(getName() + "_Init", EventImportance.kNormal);

    }

    @Override
    protected void end() {
      Shuffleboard.addEventMarker(getName() + "_End", EventImportance.kNormal);
      Robot.elevator.talon.neutralOutput();
      Robot.arm.talon.neutralOutput();
      Robot.wrist.talon.neutralOutput();
    }
  }

}
