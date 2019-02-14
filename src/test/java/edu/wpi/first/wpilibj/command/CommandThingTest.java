package edu.wpi.first.wpilibj.command;

import org.junit.Test;

public class CommandThingTest {

  @Test
  public void testDoesItWork() {
    MockCommand child = new MockCommand("child");
    MockCommand next = new MockCommand("next");


    CommandThing nextThing = new CommandThing(null, next);
    CommandThing firstThing = new CommandThing(nextThing, child);

    firstThing.start();

    Scheduler.getInstance().run();

    CommandTestUtils.checkEq(firstThing, child);

    Scheduler.getInstance().run();

    child.finished = true;

    Scheduler.getInstance().run();

    CommandTestUtils.checkEq(firstThing, child);


    System.out.println(firstThing);



  }
}
