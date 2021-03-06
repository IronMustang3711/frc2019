package org.usfirst.frc3711.deepspace.commands.sequences;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc3711.deepspace.Robot;
import org.usfirst.frc3711.deepspace.commands.util.MotionMagicSetpoint;

public class CommandSequences {

  public static class PingPong extends Command {
    final Ping ping;
    final Pong pong;

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

    Ping() {
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

    Pong() {
      super("Pong", Robot.arm, 0, 5.0);
    }

    @Override
    protected void end() {
      super.end();
      if (!isCanceled())
        ping.start();
    }
  }
}
