package controller.event;

import hochberger.utilities.eventbus.Event;

public class CountDownFinishedEvent implements Event {

	@Override
	public void performEvent() {
		// does nothing intentionally
	}
}
