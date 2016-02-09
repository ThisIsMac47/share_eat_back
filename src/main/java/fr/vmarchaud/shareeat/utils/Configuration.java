package fr.vmarchaud.shareeat.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.logging.log4j.jul.LogManager;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;

public class Configuration extends ResourceConfig {

	private Logger jerseyLogger = LogManager.getLogManager().getLogger(this.getClass().getName());
	
    public Configuration() {
    	// Add package to be scaned for routes
        packages("fr.vmarchaud.croixrouge.routes");

        // Enable LoggingFilter & output entity. 
        jerseyLogger.setLevel(Level.ALL);
        registerInstances(new LoggingFilter(jerseyLogger, true));
        register(GsonMessageBodyHandler.class);
    }
}