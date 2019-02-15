package edu.wpi.first.wpilibj.command;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

class CommandTestUtils {

    static void checkEq(Command a, Command b){
        assertEquals("finished",a.isFinished(),b.isFinished());
        assertEquals("canceled",a.isCanceled(),b.isCanceled());
        assertEquals("completed",a.isCompleted(),b.isCompleted());
        assertEquals("running",a.isRunning(),b.isRunning());
        assertEquals("timeout",a.isTimedOut(),b.isTimedOut());
        assertEquals("interruptable",a.isInterruptible(),b.isInterruptible());
        assertEquals("willRunWhenDisabled",a.willRunWhenDisabled(),b.willRunWhenDisabled());
    }

    static void checkInitialState(Command c){
        assertThat("command should not be canceled",
                c.isCanceled(), is(false));

        assertThat("command should not be finished",
                c.isFinished(), is(false));

        assertThat("command should not be completed",
                c.isCompleted(), is(false));

        assertThat("command should not be running",
                c.isRunning(), is(false));
    }

    static void checkCommandStarted(Command c){
        assertThat("command should not be canceled",
                c.isCanceled(), is(false));

        assertThat("command should not be finished",
                c.isFinished(), is(false));

        assertThat("command should not be completed",
                c.isCompleted(), is(false));

        assertThat("command should be running",
                c.isRunning(), is(true));

    }

    static void checkMockCommandStarted(MockCommand c){
        checkCommandStarted(c);
        assertThat("init has not been called yet(?)",
                c.initCalls, is(0));
    }

    static void checkMockCommandExecution(MockCommand c){
        //normal execution
        assertThat("init has been called once",
                c.initCalls, is(1));

        assertThat("exec has been called at least once",
                c.execCalls, is(greaterThanOrEqualTo(1)));
    }

    static void checkMockCommandExecution(MockCommand c, int expectedExecutions){
        //normal execution
        assertThat("init has been called once",
                c.initCalls, is(1));

        assertThat("exec has been called once",
                c.execCalls, is(expectedExecutions));
    }

    static void checkMockCommandPostCancel(MockCommand c){
        assertThat("command should be canceled",
                c.isCanceled(), is(true));

        assertThat("end has not ben called yet",
                c.endCalls, is(0));

        assertThat("removed has not been called yet",
                c.removedCalls, is(0));
    }

    static void checkMockCommandFinalState(MockCommand c){
        assertThat("end has been called once",
                c.endCalls, is(1));

        assertThat("removed has been called once",
                c.removedCalls, is(1));

        assertThat("command should not be running",
                c.isRunning(), is(false));
    }
}
