package edu.wpi.first.wpilibj.command;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Vector;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MyCommandGroup extends CommandGroup {

  @SuppressWarnings("WeakerAccess")
  public Consumer<CharSequence> debugSink = System.out::println;

  enum Reflection {
    INSTANCE;
      Field childrenField;
      Field commandsField;
      Field entryCommandField;
      Reflection(){
        try {
          childrenField = CommandGroup.class.getDeclaredField("m_children");
          childrenField.setAccessible(true);
          commandsField = CommandGroup.class.getDeclaredField("m_commands");
          commandsField.setAccessible(true);

          Class<?> entryClass = Class.forName("edu.wpi.first.wpilibj.command.CommandGroup$Entry");
          entryCommandField = entryClass.getDeclaredField("m_command");
          entryCommandField.setAccessible(true);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
          throw new RuntimeException(e);
        }
      }

    /**
     *
     * @return Vector of Entries
     */
    Vector<?> getCommandsVector(CommandGroup group){
      try {
        return (Vector<?>) commandsField.get(group);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }

    /**
     *
     * @return Vector of Entries
     */
    Vector<?> getChildrenVector(CommandGroup group){
      try {
        return (Vector<?>) childrenField.get(group);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }

    Command extractCommandFromEntry(Object entry){
      try {
        return (Command) entryCommandField.get(entry);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }

    List<Command> getCommands(CommandGroup cg){
      return getCommandsVector(cg).stream().map(this::extractCommandFromEntry).collect(Collectors.toList());
    }
    List<Command> getChildren(CommandGroup cg){
      return getChildrenVector(cg).stream().map(this::extractCommandFromEntry).collect(Collectors.toList());
    }

  }

  @SuppressWarnings("unused")
  public MyCommandGroup() {
  }

  public MyCommandGroup(String name) {
    super(name);
  }

   private String debugString(){
    var commands = Reflection.INSTANCE.getCommands(this);
    var children = Reflection.INSTANCE.getChildren(this);

    var runningCommands = commands.stream().filter(Command::isRunning).collect(Collectors.toList());



    var runningChildrenStr = runningCommands.stream()
                                 .filter(children::contains)
                                 .map(Command::getName)
                                 .collect(Collectors.joining(", ", ".[", "]"));

    var runningSiblingsStr = runningCommands.stream()
                                 .filter(c -> !children.contains(c))
                                 .map(Command::getName)
                                 .collect(Collectors.joining(", ","..[","]"));

    return getName()+ "[Running] " + runningChildrenStr +" "+ runningSiblingsStr;
  }

  private String prevDebugString = "";

  @Override
  void _execute() {
    super._execute();
    var debugStr = debugString();

    if (!prevDebugString.equals(debugStr)) {
      debugSink.accept(debugStr);
      prevDebugString = debugStr;
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
