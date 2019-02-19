package edu.wpi.first.wpilibj.command;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MyCommandGroupTest {

static class TestCommand extends Command {

  TestCommand(String name){ super(name);}
  int numIsFinishedCallsAllowed = 3;
  int isFinishedCalls = 0;
  @Override
  protected boolean isFinished() {
    return ++isFinishedCalls > numIsFinishedCallsAllowed;
  }
}


  @Test(expected = Test.None.class)
  public void testReflection() {
    var reflection = MyCommandGroup.Reflection.INSTANCE;

    var cg = new CommandGroup("testing");
    cg.addParallel(new TestCommand("1"));
    cg.addSequential(new TestCommand("2"));


    var childrenVec = reflection.getChildrenVector(cg);
    assertNotNull(childrenVec);

    var commandsVec = reflection.getCommandsVector(cg);
    assertNotNull(commandsVec);

    var commands = reflection.getCommands(cg);
    assertEquals(2,commands.size());

    var children = reflection.getChildren(cg);
    assertEquals(0,children.size());
  }

  @Test
  public void testMyCommandGroupDebugString() throws InterruptedException {
    List<CharSequence> debugMessages = new ArrayList<>();

    var cg = new MyCommandGroup("testing");
    cg.setRunWhenDisabled(true);
    cg.debugSink = debugMessages::add;

    TestCommand c1 = new TestCommand("1");
    cg.addParallel(c1);
    TestCommand c2 = new TestCommand("2");
    cg.addSequential(c2);

    cg.start();

    Scheduler.getInstance().enable();
    Scheduler.getInstance().run();

    assertEquals("[]",debugMessages.toString());

    Scheduler.getInstance().run();

    assertEquals("[testing[Init], testing[Running@0.0] .[1] ..[2]]",debugMessages.toString());
    Thread.sleep(1000);

    Scheduler.getInstance().run();
    Scheduler.getInstance().run();
    Scheduler.getInstance().run();

    assertEquals("[testing[Init], testing[Running@0.0] .[1] ..[2], testing[Running@1.0] .[] ..[], testing[End]]",debugMessages.toString());

  }
}
