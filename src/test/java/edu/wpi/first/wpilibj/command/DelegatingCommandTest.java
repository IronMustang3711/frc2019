package edu.wpi.first.wpilibj.command;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class DelegatingCommandTest {

	private MockCommand mockCommand;




	@Before
	public void setUp() {
		mockCommand = new MockCommand("mock");
	}


	//@Ignore
	@Test
	public void testCommandLifeCycle() {
		Scheduler.getInstance().enable();
		mockCommand.setRunWhenDisabled(true); //important!
		CommandTestUtils.checkInitialState(mockCommand);


		//start the command and pump the scheduler
		mockCommand.start();
		Scheduler.getInstance().run();
		CommandTestUtils.checkMockCommandStarted(mockCommand);



		Scheduler.getInstance().run();
		CommandTestUtils.checkMockCommandExecution(mockCommand);



		mockCommand.cancel();
		CommandTestUtils.checkMockCommandPostCancel(mockCommand);




		Scheduler.getInstance().run();
		CommandTestUtils.checkMockCommandFinalState(mockCommand);

	}

	@Test
	public void testDelegateCommandLifecycle1() {

		DelegatingCommandFixture delegatingCommand = new DelegatingCommandFixture(mockCommand);
		Scheduler.getInstance().enable();

		CommandTestUtils.checkEq(delegatingCommand,mockCommand);


		delegatingCommand.setRunWhenDisabled(true);

		CommandTestUtils.checkEq(delegatingCommand,mockCommand);
		CommandTestUtils.checkInitialState(delegatingCommand);
		CommandTestUtils.checkInitialState(mockCommand);


		delegatingCommand.start();
		Scheduler.getInstance().run();

		CommandTestUtils.checkEq(delegatingCommand,mockCommand);
		CommandTestUtils.checkCommandStarted(delegatingCommand);
		CommandTestUtils.checkMockCommandStarted(mockCommand);

		Scheduler.getInstance().run();
		CommandTestUtils.checkEq(delegatingCommand,mockCommand);
		assertThat(delegatingCommand.execCalls, is(equalTo(mockCommand.execCalls)));
		CommandTestUtils.checkMockCommandExecution(mockCommand,1);


		Scheduler.getInstance().run();
		CommandTestUtils.checkEq(delegatingCommand,mockCommand);
		assertThat(delegatingCommand.execCalls, is(equalTo(mockCommand.execCalls)));
		CommandTestUtils.checkMockCommandExecution(mockCommand,2);


		delegatingCommand.cancel();
		CommandTestUtils.checkEq(delegatingCommand,mockCommand);
		assertTrue(delegatingCommand.isCanceled());
		CommandTestUtils.checkMockCommandPostCancel(mockCommand);


		Scheduler.getInstance().run();
		CommandTestUtils.checkEq(delegatingCommand,mockCommand);
		assertThat("command should not be running",
				delegatingCommand.isRunning(), is(false));
		assertThat("end has been called once",
				delegatingCommand.endCalls, is(1));
		assertThat("removed has been called once",
				delegatingCommand.removedCalls, is(1));






	}

	@Test
	public void testTimeout() throws InterruptedException {
		mockCommand = new MockCommand("mock"){
			@Override
			protected boolean isFinished() {
				return isTimedOut();
			}
		};
		mockCommand.setTimeout(0.0001);

		DelegatingCommandFixture delegatingCommand = new DelegatingCommandFixture(mockCommand);
		delegatingCommand.setRunWhenDisabled(true);

		Scheduler.getInstance().enable();

		CommandTestUtils.checkEq(delegatingCommand,mockCommand);

		delegatingCommand.start();
		CommandTestUtils.checkEq(delegatingCommand,mockCommand);

		Scheduler.getInstance().run();
		CommandTestUtils.checkEq(delegatingCommand,mockCommand);


		Scheduler.getInstance().run();
		Thread.sleep(200);
		Scheduler.getInstance().run();
		CommandTestUtils.checkEq(delegatingCommand,mockCommand);


		assertTrue(mockCommand.isFinished());
		assertTrue(delegatingCommand.isFinished());


	}

	static class DelegatingCommandFixture extends DelegatingCommand {
		int initCalls;
		int execCalls;
		int endCalls;
		int removedCalls;

		DelegatingCommandFixture(Command delegate) {
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
			return super.isFinished();
		}

		@Override
		public synchronized void removed() {
			super.removed();
			removedCalls++;
		}
	}
}
