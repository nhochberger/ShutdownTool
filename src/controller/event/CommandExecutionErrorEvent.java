package controller.event;

import hochberger.utilities.eventbus.Event;

public class CommandExecutionErrorEvent implements Event {

    public CommandExecutionErrorEvent() {
        super();
    }

    @Override
    public void performEvent() {
        // does nothing intentionally
    }
}
