package controller;

import gui.ShutdownGui;
import hochberger.utilities.eventbus.Event;
import hochberger.utilities.eventbus.EventBus;
import hochberger.utilities.eventbus.EventReceiver;
import hochberger.utilities.eventbus.SimpleEventBus;
import hochberger.utilities.logging.LogToConsole;
import hochberger.utilities.properties.LoadProperties;

import java.io.IOException;
import java.util.Properties;

import controller.event.CancelCountDownEvent;
import controller.event.CommandExecutionErrorEvent;
import controller.event.CountDownEvent;
import controller.event.CountDownFinishedEvent;
import controller.event.StartCountDownEvent;

public class Main {

    public static String APPLICATION_TITLE;
    public static final EventBus EVENT_BUS = new SimpleEventBus();

    public static void main(final String[] args) {
        Properties properties = null;
        try {
            properties = LoadProperties.from("application.properties");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        APPLICATION_TITLE = properties.getProperty("title");
        LogToConsole.info("Application \"" + APPLICATION_TITLE + "\" started.");
        ShutdownGui gui = new ShutdownGui(APPLICATION_TITLE);
        CountDownTaskManager taskManager = new CountDownTaskManager();
        ShutDownController shutDownController = new ShutDownController();
        registerEventReceivers(gui, taskManager, shutDownController);
        gui.show();
    }

    private static void registerEventReceivers(final ShutdownGui gui, final CountDownTaskManager taskManager, final ShutDownController shutDownController) {
        LogToConsole.info("Registering receivers to event bus");
        Main.EVENT_BUS.register(gui, CountDownEvent.class);
        Main.EVENT_BUS.register(gui, StartCountDownEvent.class);
        Main.EVENT_BUS.register(gui, CancelCountDownEvent.class);
        Main.EVENT_BUS.register(gui, CountDownFinishedEvent.class);
        Main.EVENT_BUS.register(gui, CommandExecutionErrorEvent.class);
        Main.EVENT_BUS.register(taskManager, StartCountDownEvent.class);
        Main.EVENT_BUS.register(taskManager, CancelCountDownEvent.class);
        Main.EVENT_BUS.register(taskManager, CountDownFinishedEvent.class);
        Main.EVENT_BUS.register(shutDownController, CountDownFinishedEvent.class);
        Main.EVENT_BUS.register(shutDownController, CancelCountDownEvent.class);
    }

    protected static class CountDownFinishedEventReceiver implements EventReceiver {

        public CountDownFinishedEventReceiver() {
            super();
        }

        @Override
        public <TYPE extends Event> void receive(final TYPE event) {
            System.out.println("CountDown finished");
        }

    }
}
