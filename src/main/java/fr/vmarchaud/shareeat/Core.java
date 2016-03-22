package fr.vmarchaud.shareeat;

import java.io.IOException;
import java.net.URI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import com.google.gson.JsonSyntaxException;

import fr.vmarchaud.shareeat.services.AuthService;
import fr.vmarchaud.shareeat.services.LocationService;
import fr.vmarchaud.shareeat.services.DataService;
import fr.vmarchaud.shareeat.services.MeetupService;
import fr.vmarchaud.shareeat.services.StripeService;
import fr.vmarchaud.shareeat.services.UserService;
import fr.vmarchaud.shareeat.utils.Configuration;
import fr.vmarchaud.shareeat.utils.CustomConfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.Getter;

public class Core {

	// Entry point for this program
	public static void main(String[] args) {
		try {
			new Core(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Class access
	@Getter static Core instance;
	@Getter static Logger logger = LogManager.getLogger();
	@Getter static HttpServer httpServer;
	
	// Service & db
	@Getter public DataService dataService;
	@Getter public UserService	userService;
	@Getter	public AuthService	authService;
	@Getter public MeetupService meetupService;
	@Getter public LocationService locationService;
	@Getter public StripeService 	stripeService;
	
	// Config 
	@Getter public CustomConfig config;

	public Core(String[] args) throws JsonSyntaxException, IOException {
		instance = this;
		long start = System.currentTimeMillis();
		
		// Load Config
		config = new CustomConfig();
		//gson.fromJson(new String(Files.readAllBytes(Paths.get("config.json")), StandardCharsets.UTF_8), CustomConfig.class);
		
		// Starting service
		dataService = new DataService();
		authService = new AuthService();
		userService = new UserService();
		meetupService = new MeetupService();
		locationService = new LocationService();
		stripeService = new StripeService();
		
		// Start web server
		httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(CustomConfig.BASE_URL), new Configuration());
		
		logger.info("Server ready in " + (System.currentTimeMillis() - start) + " ms");
		
		System.in.read();
		dataService.close();
		httpServer.shutdown();
	}
}
