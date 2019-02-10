package edu.wpi.first.wpilibj.command;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

import java.util.Enumeration;

public class DelegatingCommand extends Command {

	@SuppressWarnings("WeakerAccess")
	protected Command delegate;

	public DelegatingCommand(Command delegate) {
		super("->" + delegate.getName());
		this.delegate = delegate;
	}

	public DelegatingCommand(Command delegate, double timeout) {
		super("->" + delegate.getName(), timeout);
		this.delegate = delegate;
	}

	/**
	 * This method specifies that the given {@link Subsystem} is used by this command. This method is
	 * crucial to the functioning of the Command System in general.
	 *
	 * <p>Note that the recommended way to call this method is in the constructor.
	 *
	 * @param subsystem the {@link Subsystem} required
	 * @throws IllegalArgumentException     if subsystem is null
	 * @throws IllegalUseOfCommandException if this command has started before or if it has been given
	 *                                      to a {@link CommandGroup}
	 * @see Subsystem
	 */
	@Override
	public void requires(Subsystem subsystem) {
		super.requires(subsystem);
		//delegate.requires(subsystem);
	}

	/**
	 * Called when the command has been removed. This will call {@link Command#interrupted()
	 * interrupted()} or {@link Command#end() end()}.
	 */
	@Override
	public void removed() {
		super.removed();
		//delegate.removed();
	}

	/**
	 * The run method is used internally to actually run the commands.
	 *
	 * @return whether or not the command should stay within the {@link Scheduler}.
	 */
	@Override
	public boolean run() {
		//return delegate.run();
		return super.run() && !delegate.isFinished();
	}

	/**
	 * The initialize method is called the first time this Command is run after being started.
	 */
	@Override
	public void initialize() {
		super.initialize();
		//delegate.initialize();
	}

	/**
	 * A shadow method called before {@link Command#initialize() initialize()}.
	 */
	@Override
	public void _initialize() {
		super._initialize();
		//delegate._initialize();
	}

	/**
	 * The execute method is called repeatedly until this Command either finishes or is canceled.
	 */
	@Override
	public void execute() {
		super.execute();
		//delegate.execute();
	}

	/**
	 * A shadow method called before {@link Command#execute() execute()}.
	 */
	@Override
	public void _execute() {
		super._execute();
		//delegate._execute();
	}

	/**
	 * Returns whether this command is finished. If it is, then the command will be removed and {@link
	 * Command#end() end()} will be called.
	 *
	 * <p>It may be useful for a team to reference the {@link Command#isTimedOut() isTimedOut()}
	 * method for time-sensitive commands.
	 *
	 * <p>Returning false will result in the command never ending automatically. It may still be
	 * cancelled manually or interrupted by another command. Returning true will result in the
	 * command executing once and finishing immediately. We recommend using {@link InstantCommand}
	 * for this.
	 *
	 * @return whether this command is finished.
	 * @see Command#isTimedOut() isTimedOut()
	 */
	@Override
	public boolean isFinished() {
		return delegate.isFinished() || isTimedOut();
	}

	/**
	 * Called when the command ended peacefully. This is where you may want to wrap up loose ends,
	 * like shutting off a motor that was being used in the command.
	 */
	@Override
	public void end() {
		super.end();
		//delegate.end();
	}

	/**
	 * A shadow method called after {@link Command#end() end()}.
	 */
	@Override
	public void _end() {
		super._end();//delegate._end();
	}

	/**
	 * Called when the command ends because somebody called {@link Command#cancel() cancel()} or
	 * another command shared the same requirements as this one, and booted it out.
	 *
	 * <p>This is where you may want to wrap up loose ends, like shutting off a motor that was being
	 * used in the command.
	 *
	 * <p>Generally, it is useful to simply call the {@link Command#end() end()} method within this
	 * method, as done here.
	 */
	@Override
	public void interrupted() {
		super.interrupted();
		//delegate.interrupted();
	}

	/**
	 * A shadow method called after {@link Command#interrupted() interrupted()}.
	 */
	@Override
	public void _interrupted() {
		super._interrupted();
		//delegate._interrupted();
	}

	/**
	 * Returns whether or not the {@link Command#timeSinceInitialized() timeSinceInitialized()} method
	 * returns a number which is greater than or equal to the timeout for the command. If there is no
	 * timeout, this will always return false.
	 *
	 * @return whether the time has expired
	 */
	@Override
	public boolean isTimedOut() {
		return super.isTimedOut() || delegate.isTimedOut();
	}

	/**
	 * Returns the requirements (as an {@link Enumeration Enumeration} of {@link Subsystem
	 * Subsystems}) of this command.
	 *
	 * @return the requirements (as an {@link Enumeration Enumeration} of {@link Subsystem
	 * Subsystems}) of this command
	 */
	@Override
	public Enumeration getRequirements() {
		return super.getRequirements();//delegate.getRequirements();
	}

	/**
	 * Prevents further changes from being made.
	 */
	@Override
	public void lockChanges() {
		super.lockChanges();
		delegate.lockChanges();
	}

//	/**
//	 * If changes are locked, then this will throw an {@link IllegalUseOfCommandException}.
//	 *
//	 * @param message the message to say (it is appended by a default message)
//	 */
//	@Override
//	public void validate(String message) {
//		super.validate(message);
//		//delegate.validate(message);
//	}

	/**
	 * Sets the parent of this command. No actual change is made to the group.
	 *
	 * @param parent the parent
	 * @throws IllegalUseOfCommandException if this {@link Command} already is already in a group
	 */
	@Override
	public void setParent(CommandGroup parent) {
		super.setParent(parent);
		//delegate.setParent(parent);
	}

	/**
	 * Returns whether the command has a parent.
	 *
	 * @return true if the command has a parent.
	 */
	@Override
	public boolean isParented() {
		return super.isParented();//delegate.isParented();
	}

	/**
	 * Clears list of subsystem requirements. This is only used by
	 * {@link ConditionalCommand} so cancelling the chosen command works properly
	 * in {@link CommandGroup}.
	 */
	@Override
	public void clearRequirements() {
		super.clearRequirements();
		//delegate.clearRequirements();
	}

	/**
	 * Starts up the command. Gets the command ready to start. <p> Note that the command will
	 * eventually start, however it will not necessarily do so immediately, and may in fact be
	 * canceled before initialize is even called. </p>
	 *
	 * @throws IllegalUseOfCommandException if the command is a part of a CommandGroup
	 */
	@Override
	public void start() {
		super.start();
		delegate.start();
		//delegate.start();
	}

	/**
	 * This is used internally to mark that the command has been started. The lifecycle of a command
	 * is:
	 *
	 * <p>startRunning() is called. run() is called (multiple times potentially) removed() is called.
	 *
	 * <p>It is very important that startRunning and removed be called in order or some assumptions of
	 * the code will be broken.
	 */
	@Override
	public void startRunning() {
		super.startRunning();
		delegate.startRunning();
	}

	/**
	 * Returns whether or not the command is running. This may return true even if the command has
	 * just been canceled, as it may not have yet called {@link Command#interrupted()}.
	 *
	 * @return whether or not the command is running
	 */
	@Override
	public boolean isRunning() {
		return super.isRunning();// && delegate.isRunning();
	}

	/**
	 * This will cancel the current command. <p> This will cancel the current command eventually. It
	 * can be called multiple times. And it can be called when the command is not running. If the
	 * command is running though, then the command will be marked as canceled and eventually removed.
	 * </p> <p> A command can not be canceled if it is a part of a command group, you must cancel the
	 * command group instead. </p>
	 *
	 * @throws IllegalUseOfCommandException if this command is a part of a command group
	 */
	@Override
	public void cancel() {
		super.cancel();
		delegate.cancel();
	}

	/**
	 * This works like cancel(), except that it doesn't throw an exception if it is a part of a
	 * command group. Should only be called by the parent command group.
	 */
	@Override
	public void _cancel() {
		super._cancel();
		delegate._cancel();
	}

	/**
	 * Returns whether or not this has been canceled.
	 *
	 * @return whether or not this has been canceled
	 */
	@Override
	public boolean isCanceled() {
		return super.isCanceled(); //delegate.isCanceled();// delegate.isCanceled();
	}

	/**
	 * Whether or not this command has completed running.
	 *
	 * @return whether or not this command has completed running.
	 */
	@Override
	public boolean isCompleted() {
		return super.isCompleted(); //delegate.isCompleted();
	}

	/**
	 * Returns whether or not this command can be interrupted.
	 *
	 * @return whether or not this command can be interrupted
	 */
	@Override
	public boolean isInterruptible() {
		assert super.isInterruptible() == delegate.isInterruptible();
		return super.isInterruptible();//delegate.isInterruptible();
	}

	/**
	 * Sets whether or not this command can be interrupted.
	 *
	 * @param interruptible whether or not this command can be interrupted
	 */
	@Override
	public void setInterruptible(boolean interruptible) {
		super.setInterruptible(interruptible);
		delegate.setInterruptible(interruptible);
	}

	/**
	 * Checks if the command requires the given {@link Subsystem}.
	 *
	 * @param system the system
	 * @return whether or not the subsystem is required, or false if given null
	 */
	@Override
	public boolean doesRequire(Subsystem system) {
		return super.doesRequire(system);//delegate.doesRequire(system);
	}

	/**
	 * Returns the {@link CommandGroup} that this command is a part of. Will return null if this
	 * {@link Command} is not in a group.
	 *
	 * @return the {@link CommandGroup} that this command is a part of (or null if not in group)
	 */
	@Override
	public CommandGroup getGroup() {
		return super.getGroup();//.getGroup();
	}

	/**
	 * Sets whether or not this {@link Command} should run when the robot is disabled.
	 *
	 * <p>By default a command will not run when the robot is disabled, and will in fact be canceled.
	 *
	 * @param run whether or not this command should run when the robot is disabled
	 */
	@Override
	public void setRunWhenDisabled(boolean run) {
		super.setRunWhenDisabled(run);
		delegate.setRunWhenDisabled(run);
	}

	/**
	 * Returns whether or not this {@link Command} will run when the robot is disabled, or if it will
	 * cancel itself.
	 *
	 * @return True if this command will run when the robot is disabled.
	 */
	@Override
	public boolean willRunWhenDisabled() {
		assert super.willRunWhenDisabled() == delegate.willRunWhenDisabled();
		return super.willRunWhenDisabled();//delegate.willRunWhenDisabled();
	}

	/**
	 * The string representation for a {@link Command} is by default its name.
	 *
	 * @return the string representation of this object
	 */
	@Override
	public String toString() {
		return "->" + delegate.toString();
	}

	@Override
	public void initSendable(SendableBuilder builder) {
		super.initSendable(builder);

		//delegate.initSendable(builder);
	}

//	@Override
//	@Deprecated
//	public void free() {
//		super.free();
//		delegate.free();
//	}

	@Override
	public void close() {
		super.close();
		//delegate.close();
	}


	/**
	 * Sets both the subsystem name and device name of this {@link Sendable} object.
	 *
	 * @param subsystem subsystem name
	 * @param name      device name
	 */
	@Override
	public void setName(String subsystem, String name) {
		super.setName(subsystem, name);
		//delegate.setName(subsystem, name);
	}


}
