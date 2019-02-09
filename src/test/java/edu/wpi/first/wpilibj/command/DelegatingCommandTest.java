package edu.wpi.first.wpilibj.command;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.*;

@SuppressWarnings("UnusedLabel")
public class DelegatingCommandTest {

	static class MockCommand extends Command {
		int initCalls;
		int execCalls;
		int endCalls;
		int removedCalls;

		@Override
		protected void initialize() {
			initCalls++;
			super.initialize();
		}

		@Override
		protected void execute() {
			execCalls++;
			super.execute();
		}

		@Override
		protected void end() {
			endCalls++;
			super.end();
		}

		@Override
		protected boolean isFinished() {
			return false;
		}

		@Override
		synchronized void removed() {
			super.removed();
			removedCalls++;
		}
	}

	MockCommand mockCommand;




	@Before
	public void setUp() throws Exception {
		mockCommand = new MockCommand();
	}

	private static void checkEq(Command a, Command b){
		assertEquals("finished",a.isFinished(),b.isFinished());
		assertEquals("canceled",a.isCanceled(),b.isCanceled());
		assertEquals("completed",a.isCompleted(),b.isCompleted());
		assertEquals("running",a.isRunning(),b.isRunning());
		assertEquals("timeout",a.isTimedOut(),b.isTimedOut());
		assertEquals("interruptable",a.isInterruptible(),b.isInterruptible());
		assertEquals("willRunWhenDisabled",a.willRunWhenDisabled(),b.willRunWhenDisabled());
	}

	private static void checkInitialState(Command c){
		assertThat("command should not be canceled",
				c.isCanceled(), is(false));

		assertThat("command should not be finished",
				c.isFinished(), is(false));

		assertThat("command should not be completed",
				c.isCompleted(), is(false));

		assertThat("command should not be running",
				c.isRunning(), is(false));
	}

	private static void checkCommandStarted(Command c){
		assertThat("command should not be canceled",
				c.isCanceled(), is(false));

		assertThat("command should not be finished",
				c.isFinished(), is(false));

		assertThat("command should not be completed",
				c.isCompleted(), is(false));

		assertThat("command should be running",
				c.isRunning(), is(true));

	}

	private static void checkMockCommandStarted(MockCommand c){
		checkCommandStarted(c);
		assertThat("init has not been called yet(?)",
				c.initCalls, is(0));
	}

	private static void checkMockCommandExecution(MockCommand c){
		//normal execution
		assertThat("init has been called once",
				c.initCalls, is(1));

		assertThat("exec has been called at least once",
				c.execCalls, is(greaterThanOrEqualTo(1)));
	}

	private static void checkMockCommandExecution(MockCommand c,int expectedExecutions){
		//normal execution
		assertThat("init has been called once",
				c.initCalls, is(1));

		assertThat("exec has been called once",
				c.execCalls, is(expectedExecutions));
	}

	private static void checkMockCommandPostCancel(MockCommand c){
		assertThat("command should be canceled",
				c.isCanceled(), is(true));

		assertThat("end has not ben called yet",
				c.endCalls, is(0));

		assertThat("removed has not been called yet",
				c.removedCalls, is(0));
	}

	private static void checkMockCommandFinalState(MockCommand c){
		assertThat("end has been called once",
				c.endCalls, is(1));

		assertThat("removed has been called once",
				c.removedCalls, is(1));

		assertThat("command should not be running",
				c.isRunning(), is(false));
	}



	//@Ignore
	@Test
	public void testCommandLifeCycle() {
		Scheduler.getInstance().enable();
		mockCommand.setRunWhenDisabled(true); //important!
		checkInitialState(mockCommand);


		//start the command and pump the scheduler
		mockCommand.start();
		Scheduler.getInstance().run();
		checkMockCommandStarted(mockCommand);



		Scheduler.getInstance().run();
		checkMockCommandExecution(mockCommand);



		mockCommand.cancel();
		checkMockCommandPostCancel(mockCommand);




		Scheduler.getInstance().run();
		checkMockCommandFinalState(mockCommand);

	}

	@Test
	public void testDelegateCommandLifecycle1() {

		DelegatingCommandFixture delegatingCommand = new DelegatingCommandFixture(mockCommand);
		Scheduler.getInstance().enable();

		checkEq(delegatingCommand,mockCommand);


		delegatingCommand.setRunWhenDisabled(true);

		checkEq(delegatingCommand,mockCommand);
		checkInitialState(delegatingCommand);
		checkInitialState(mockCommand);


		delegatingCommand.start();
		Scheduler.getInstance().run();

		checkEq(delegatingCommand,mockCommand);
		checkCommandStarted(delegatingCommand);
		checkMockCommandStarted(mockCommand);

		Scheduler.getInstance().run();
		checkEq(delegatingCommand,mockCommand);
		assertThat(delegatingCommand.execCalls, is(equalTo(mockCommand.execCalls)));
		checkMockCommandExecution(mockCommand,1);


		Scheduler.getInstance().run();
		checkEq(delegatingCommand,mockCommand);
		assertThat(delegatingCommand.execCalls, is(equalTo(mockCommand.execCalls)));
		checkMockCommandExecution(mockCommand,2);


		delegatingCommand.cancel();
		checkEq(delegatingCommand,mockCommand);
		assertTrue(delegatingCommand.isCanceled());
		checkMockCommandPostCancel(mockCommand);


		Scheduler.getInstance().run();
		checkEq(delegatingCommand,mockCommand);
		assertThat("command should not be running",
				delegatingCommand.isRunning(), is(false));
		assertThat("end has been called once",
				delegatingCommand.endCalls, is(1));
		assertThat("removed has been called once",
				delegatingCommand.removedCalls, is(1));






	}

	static class DelegatingCommandFixture extends DelegatingCommand {
		int initCalls;
		int execCalls;
		int endCalls;
		int removedCalls;

		public DelegatingCommandFixture(Command delegate) {
			super(delegate);
		}

		@Override
		public void initialize() {
			initCalls++;
			super.initialize();
		}

		@Override
		public void execute() {
			execCalls++;
			super.execute();
		}

		@Override
		public void end() {
			endCalls++;
			//super.end();
		}

		@Override
		public boolean isFinished() {
			return false;
		}

		@Override
		public synchronized void removed() {
			super.removed();
			removedCalls++;
		}
	}
}
