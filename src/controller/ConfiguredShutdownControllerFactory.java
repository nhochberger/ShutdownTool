package controller;

import hochberger.utilities.logging.LogToConsole;

import java.util.Properties;

public class ConfiguredShutdownControllerFactory {

	private final static String DEBUG_MODE = "debugmode";
	private final static String DEBUG = "1";

	private final Properties properties;

	public ConfiguredShutdownControllerFactory(Properties properties) {
		super();
		this.properties = properties;
	}

	public ShutDownController shutDownController() {
		if (startedInDebugMode()) {
			LogToConsole.debug("Using debug shutdown");
			return new ShutDownController.DebugShutdownController();
		}
		LogToConsole.debug("Using actual system shutdown");
		return new ShutDownController();
	}

	private boolean startedInDebugMode() {
		return DEBUG.equals(this.properties.getProperty(DEBUG_MODE, String.valueOf(0)));
	}

}
