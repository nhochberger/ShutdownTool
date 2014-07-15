package controller.task;

import java.util.TimerTask;

import controller.Main;
import controller.event.CountDownEvent;
import controller.event.CountDownFinishedEvent;

public class CountDownTask extends TimerTask {

	private long seconds;

	public CountDownTask(final long seconds) {
		super();
		this.seconds = seconds;
	}

	@Override
	public void run() {
		if (0 == this.seconds) {
			Main.EVENT_BUS.publish(new CountDownFinishedEvent());
		}
		Main.EVENT_BUS.publish(new CountDownEvent(this.seconds--));
	}
}
