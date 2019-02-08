package org.usfirst.frc3711.FRC2019.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class Commands {


	public static class ThenableCommand extends Command {

		private ThenableCommand next;
		private Command delegate;

		private ThenableCommand getLast(){
			ThenableCommand it = this;
			while(it.next != null) { it = it.next; }
			return it;
		}

		public ThenableCommand then(ThenableCommand next){
			getLast().next = next;
			return this;
		}


		@Override
		protected boolean isFinished() {
			return isTimedOut() || isCanceled() || isCompleted();
		}
	}
}
