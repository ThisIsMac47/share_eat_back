package fr.vmarchaud.shareeat.utils;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;

public class Configuration extends ResourceConfig {

	java.util.logging.Logger jerseyLogger = org.apache.logging.log4j.jul.LogManager.getLogManager().getLogger(this.getClass().getName());
	
    public Configuration() {
    	// Add package to be scaned for routes
        packages("fr.vmarchaud.shareeat.routes");

        // Enable LoggingFilter & output entity. 
        //jerseyLogger.setLevel(Level.ALL);
        registerInstances(new LoggingFilter(jerseyLogger, false));
        register(GsonMessageBodyHandler.class);
        register(AuthenticationFilter.class);
    }
}