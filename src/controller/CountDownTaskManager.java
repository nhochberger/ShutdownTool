package controller;

import hochberger.utilities.eventbus.Event;
import hochberger.utilities.eventbus.EventReceiver;

import java.util.Timer;

import controller.event.CancelCountDownEvent;
import controller.event.CountDownFinishedEvent;
import controller.event.StartCountDownEvent;
import controller.task.CountDownTask;

public class CountDownTaskManager implements EventReceiver {

	private final Timer timer;
	private CountDownTask task;

	public CountDownTaskManager() {
		super();
		this.timer = new Timer();
	}

	@Override
	public <TYPE extends Event> void receive(final TYPE event) {
		if (event instanceof StartCountDownEvent) {
			long seconds = ((StartCountDownEvent) event).getSeconds();
			scheduleTask(seconds);
		}
		if (event instanceof CancelCountDownEvent) {
			cancelTask();
		}
		if (event instanceof CountDownFinishedEvent) {
			cancelTask();
		}
	}

	private void scheduleTask(final long seconds) {
		if (null != this.task) {
			return;
		}
		this.task = new CountDownTask(seconds);
		this.timer.scheduleAtFixedRate(this.task, 0, 1000);
	}

	private void cancelTask() {
		if (null == this.task) {
			return;
		}
		this.task.cancel();
		this.task = null;
	}
}
