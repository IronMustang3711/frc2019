package edu.wpi.first.wpilibj.command;

import java.lang.reflect.Field;
import java.util.Vector;
import java.util.stream.Collectors;

public class MyCommandGroup extends CommandGroup {



  private static Field childrenField;
  private static Field commandsField;

  static {
    reflective_stuff();
  }

  private Vector<?> children;
  private Vector<?> commands;
  private String prevRunningCommands = "";
  @SuppressWarnings("unused")
  public MyCommandGroup() {
    do_reflection();
  }
  public MyCommandGroup(String name) {
    super(name);
    do_reflection();
  }

  private static void reflective_stuff()  {
    try {
      childrenField = CommandGroup.class.getDeclaredField("m_children");
      childrenField.setAccessible(true);
      commandsField = CommandGroup.class.getDeclaredField("m_commands");
      commandsField.setAccessible(true);

      Class<?> entryClass = Class.forName("edu.wpi.first.wpilibj.command.CommandGroup$Entry");
      Field entryCommandField = entryClass.getDeclaredField("m_command");
      entryCommandField.setAccessible(true);
    } catch (ClassNotFoundException | NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
  }

  private void do_reflection(){
    try {
      children = (Vector) childrenField.get(this);
      commands = (Vector) commandsField.get(this);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  void _execute() {
    super._execute();

    var runningCommands = commands.stream()
                               .map(Command.class::cast)
                               .filter(Command::isRunning)
                               .collect(Collectors.toList());

    var childCommands = children.stream()
                            .map(Command.class::cast)
                            .collect(Collectors.toSet());

    var runningCommandsStr =
        runningCommands.stream()
            .map(command -> childCommands.contains(command) ? "." + command.getName() : ".." + command.getName())
            .collect(Collectors.joining(", "));


    if (!prevRunningCommands.equals(runningCommandsStr)) {
      System.out.println(getName() + "[Running]: " + runningCommandsStr);
      prevRunningCommands = runningCommandsStr;
    }

  }

  @Override
  void _initialize() {
    super._initialize();
    System.out.println(getName()+"[Init]");
  }

  @Override
  void _end() {
    super._end();
    System.out.println(getName()+"[End]");
  }
}
