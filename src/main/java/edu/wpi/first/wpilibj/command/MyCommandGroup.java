package edu.wpi.first.wpilibj.command;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MyCommandGroup extends CommandGroup {


  public MyCommandGroup() {
    do_reflection();
  }
  private static Field childrenField;
  private static Field commandsField;
  private static Field entryCommandField;


  private static void reflective_stuff()  {
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

  static {
    reflective_stuff();
  }

  private static Iterable<Command> entryExtractor(Vector v){
    return () -> {
      Iterator src = v.iterator();
      return new Iterator<>() {
        @Override
        public boolean hasNext() {
          return src.hasNext();
        }

        @Override
        public Command next() {
          Object nextEntry = src.next();
          try {
            return (Command) entryCommandField.get(nextEntry);
          } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
          }
        }
      };
    };
  }

  private Vector children;
  private Vector commands;
  private void do_reflection(){
    try {
      children = (Vector) childrenField.get(this);
      commands = (Vector) commandsField.get(this);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

//  Command[] children(){
//    return StreamSupport.stream(entryExtractor(children).spliterator(),false).toArray(Command[]::new);
//  }
   private String[] childrenNames(){
    var lst = new ArrayList<String>();

    for(var c : entryExtractor(children)){
      lst.add(c.getName());
    }
    return lst.toArray(new String[0]);
  }
//  Command[] commands(){
//    return StreamSupport.stream(entryExtractor(commands).spliterator(),false).toArray(Command[]::new);
//  }
//  private String[] commandNames(){
//    var lst = new ArrayList<String>();
//
//    for(var c : entryExtractor(commands)){
//      lst.add(c.getName());
//    }
//    return lst.toArray(new String[0]);
//  }


  public MyCommandGroup(String name) {
    super(name);
    do_reflection();
  }

//  @Override
//  public void initSendable(SendableBuilder builder) {
//    super.initSendable(builder);
//    builder.addStringArrayProperty("childCommands",this::childrenNames,null);
//    builder.addStringArrayProperty("commands",this::commandNames,null);
//  }

  private String prevChildren ="";
  private String prevRunningCommands = "";

  @Override
  void _execute() {
    super._execute();
    String runningCommands = StreamSupport.stream(entryExtractor(commands).spliterator(),false)
        .filter(Command::isRunning).map(Command::getName).collect(Collectors.joining(", "));

    if(!prevRunningCommands.equals(runningCommands)) {
      System.out.println(getName() +"[Running]: "+runningCommands);
      prevRunningCommands = runningCommands;
    }

    String children = String.join(", ",childrenNames());
    if(!prevChildren.equals(children)){
      System.out.println(getName()+"[Children]:"+children);
      prevChildren = children;
    }
  }
}
