package controller.event;

import hochberger.utilities.eventbus.Event;

public class StartCountDownEvent implements Event {

    private final long seconds;

    public StartCountDownEvent(final long seconds) {
        super();
        this.seconds = seconds;
    }

    @Override
    public void performEvent() {
        // TODO Auto-generated method stub
    }

    public long getSeconds() {
        return this.seconds;
    }
}
