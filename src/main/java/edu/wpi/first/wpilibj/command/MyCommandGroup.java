package edu.wpi.first.wpilibj.command;

import java.lang.reflect.Field;
import java.util.Vector;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MyCommandGroup extends CommandGroup {

  @SuppressWarnings("WeakerAccess")
  public Consumer<CharSequence> debugSink = System.out::println;


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

  private static void reflective_stuff() {
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

  private void do_reflection() {
    try {
      children = (Vector) childrenField.get(this);
      commands = (Vector) commandsField.get(this);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings("WeakerAccess")
  public String debugString(){
    var runningCommands = commands.stream()
                              .map(Command.class::cast)
                              .filter(Command::isRunning)
                              .collect(Collectors.toList());

    var childCommands = children.stream()
                            .map(Command.class::cast)
                            .collect(Collectors.toSet());

    var runningChildrenStr = runningCommands.stream()
                                 .filter(childCommands::contains)
                                 .map(Command::getName)
                                 .collect(Collectors.joining(", ", "@[", "]"));

    var runningSiblingsStr = runningCommands.stream()
                                 .filter(c -> !childCommands.contains(c))
                                 .map(Command::getName)
                                 .collect(Collectors.joining(", "));

    return getName() + "[Running] " + runningChildrenStr + runningSiblingsStr;
  }

  @Override
  void _execute() {
    super._execute();



//    var runningChildren = commands.stream()
//                              .map(Command.class::cast)
//                              .filter(Command::isRunning).filter(childCommands::contains)
//                              .sorted(Comparator.comparing(SendableBase::getName))
//                              .map(Command::getName)
//                              .collect(Collectors.joining(", ", "@[", "]"));
//    var childCommands = children.stream()
//                            .map(Command.class::cast)
//                            .collect(Collectors.toSet());
//
//    var runningCommandsSorted =
//        commands.stream()
//            .map(Command.class::cast)
//            .filter(Command::isRunning)
//            .sorted((a, b) -> childCommands.contains(a)
//                                  && !childCommands.contains(b)
//                                  ? -1
//                                  : a.getName().compareTo(b.getName()))
//            .map(Command::getName)
//            .collect(Collectors.joining(", "));

    //.collect(Collectors.groupingBy(childCommands::contains,);
    //.map(command -> childCommands.contains(command) ? "." + command.getName() : ".." + command.getName())
    //.collect(Collectors.joining(", "));

    var debugStr = debugString();

    if (!prevRunningCommands.equals(debugStr)) {
      debugSink.accept(getName() + "[Running]" + debugStr);
      prevRunningCommands = debugStr;
      //debugSink.accept(getName() + "[Running]: " + runningCommandsSorted);
     // prevRunningCommands = runningCommandsSorted;
    }

  }

  @Override
  void _initialize() {
    super._initialize();
    debugSink.accept(getName() + "[Init]");
  }

  @Override
  void _end() {
    super._end();
    debugSink.accept(getName() + "[End]");
  }
}
