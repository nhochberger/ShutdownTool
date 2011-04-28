package controller;

import hochberger.utilities.eventbus.Event;
import hochberger.utilities.eventbus.EventReceiver;
import hochberger.utilities.logging.LogToConsole;

import java.io.IOException;

import controller.event.CancelCountDownEvent;
import controller.event.CommandExecutionErrorEvent;
import controller.event.CountDownFinishedEvent;

public class ShutDownController implements EventReceiver {

    public ShutDownController() {
        super();
    }

    @Override
    public <TYPE extends Event> void receive(final TYPE event) {
        if (event instanceof CountDownFinishedEvent) {
            SystemCommands.SHUTDOWN.execute();
        }
        if (event instanceof CancelCountDownEvent) {
            SystemCommands.ABORT.execute();
        }
    }

    protected enum SystemCommands {

        // SHUTDOWN("shutdown -s -t 1"), ABORT("shutdown -a");
        // for debugging reasons
        SHUTDOWN(""), ABORT("");

        private final String cmdString;

        private SystemCommands(final String cmd) {
            this.cmdString = cmd;
        }

        public void execute() {
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec(this.cmdString);
            } catch (IOException e) {
                LogToConsole.error("Error while executing system command", e);
                Main.EVENT_BUS.publish(new CommandExecutionErrorEvent());
            }
        }
    }
}
