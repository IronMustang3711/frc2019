package edu.wpi.first.wpilibj.command;

public class Commands {

  static class CommandConfigurator {
    private final Command command;

    CommandConfigurator(Command command) {
      this.command = command;
    }

    public CommandConfigurator withTimeout(double timeout) {
      command.setTimeout(timeout);
      return this;
    }

    public CommandConfigurator withName(String name) {
      command.setName(name);
      return this;
    }

    public CommandConfigurator withRequirements(Subsystem... subsystems) {
      for (Subsystem subsystem : subsystems) {
        command.requires(subsystem);
      }
      return this;
    }

    public CommandConfigurator runsWhenDisabled(boolean runsWhenDisabled) {
      command.setRunWhenDisabled(runsWhenDisabled);
      return this;
    }

    public Command get() {
      return command;
    }
  }


  public static class SequentialFluentCommand extends DelegatingCommand {

    private SequentialFluentCommand next;

    public SequentialFluentCommand(Command delegate) {
      super(delegate);
    }

    public SequentialFluentCommand(Command delegate, double timeout) {
      super(delegate, timeout);
    }


    private SequentialFluentCommand getLast() {
      SequentialFluentCommand it = this;
      while (it.next != null) {
        it = it.next;
      }
      return it;
    }

    public SequentialFluentCommand then(SequentialFluentCommand next) {
      getLast().next = next;
      return this;
    }


    @Override
    public boolean isFinished() {
      return isTimedOut() || isCanceled() || isCompleted();
    }
  }
}
