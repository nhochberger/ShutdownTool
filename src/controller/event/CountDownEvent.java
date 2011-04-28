package controller.event;

import hochberger.utilities.eventbus.Event;

public class CountDownEvent implements Event {

    private final long seconds;

    public CountDownEvent(final long seconds) {
        super();
        this.seconds = seconds;
    }

    @Override
    public void performEvent() {
        // do nothing intentionally
    }

    public long getRemainingSeconds() {
        return this.seconds;
    }
}
