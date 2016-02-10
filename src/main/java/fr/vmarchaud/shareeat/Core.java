package fr.vmarchaud.shareeat;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import fr.vmarchaud.shareeat.enums.EnumEnv;
import fr.vmarchaud.shareeat.services.AuthService;
import fr.vmarchaud.shareeat.services.UserService;
import fr.vmarchaud.shareeat.utils.Configuration;
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

	// Gson instance
	public Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
	
	// Service & db
	@Getter public UserService	userService;
	@Getter	public AuthService	authService;
	
	// Config 
	@Getter public EnumEnv env = EnumEnv.DEV;

	public Core(String[] args) throws JsonSyntaxException, IOException {
		long start = System.currentTimeMillis();
		instance = this;
		
		// Load Config
		JsonObject config = gson.fromJson(new String(Files.readAllBytes(Paths.get("config.json")), StandardCharsets.UTF_8), JsonObject.class);
		
		// Starting service
		try {
			dataService = new DataService("shareeat.vmarchaud.fr", 28015);
		} catch (TimeoutException e) {
			logger.error("Cant connect to database", e);
			return ;
		}
		userService = new UserService();
		authService =  new AuthService();
		
		
		// Start web server
		httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(config.get("BASE_URL").getAsString()), new Configuration());
		
		logger.info("Server ready (in " + (System.currentTimeMillis() - start) + " ms)");
		
		System.in.read();
		httpServer.shutdown();
	}
	
}
