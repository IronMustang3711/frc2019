package edu.wpi.first.wpilibj.command;

class MockCommand extends Command {
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
        return isTimedOut();
    }

    @Override
    synchronized void removed() {
        super.removed();
        removedCalls++;
    }
}
