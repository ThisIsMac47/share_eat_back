package fr.vmarchaud.croixrouge;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.vmarchaud.croixrouge.dbs.IDatabase;
import fr.vmarchaud.croixrouge.dbs.impls.DBRethink;
import fr.vmarchaud.croixrouge.objects.User;
import fr.vmarchaud.croixrouge.services.AuthService;
import fr.vmarchaud.croixrouge.services.UserService;
import fr.vmarchaud.croixrouge.utils.Configuration;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import lombok.Getter;

public class Core {

	// Entry point for this program
	public static void main(String[] args) {
		try {
			new Core(args);
		} catch (JsonSyntaxException | IOException e) {
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
	@Getter public IDatabase		database;
	@Getter public UserService	userService;
	@Getter	public AuthService	authService;
	
	// Config 
	public String GAPI_KEY = "google api key";
	public String PROJECT_ID = "google project id";

	public Core(String[] args) throws JsonSyntaxException, IOException {
		instance = this;
		
		// Load Config
		long start = System.currentTimeMillis();
		
		JsonObject config = gson.fromJson(new String(Files.readAllBytes(Paths.get("./config.json")), StandardCharsets.UTF_8), JsonObject.class);
		GAPI_KEY = config.get("GAPIKEY").getAsString();
		PROJECT_ID = config.get("PROJECT_ID").getAsString();
		
		
		database = new DBRethink("team-kh.eu", 28015);
		
		// Load Data
		
		/*JsonObject data = gson.fromJson(new String(Files.readAllBytes(Paths.get("./data.json")), StandardCharsets.UTF_8), JsonObject.class);
		TypeToken<List<User>> usersType = new TypeToken<List<User>>(){};
		List<User> users = gson.fromJson(data.get("users").getAsJsonArray(), usersType.getType());
		TypeToken<List<Degree>> degreeType = new TypeToken<List<Degree>>(){};
		List<Degree> degrees = gson.fromJson(data.get("degrees").getAsJsonArray(), degreeType.getType());
		
		for(Degree degree : degrees) {
			for(User user : users) {
				if (user.getId().compareTo(degree.getId()) == 0) {
					if (user.getDegrees() == null)
						user.setDegrees(new ArrayList<Degree>());
					user.getDegrees().add(degree);
					break ;
				}
			}
		}*/
		logger.info("Data loaded in " + (System.currentTimeMillis() - start) + " ms");
		
		// Starting service
		userService = new UserService();
		
		// Start web server
		
		httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(config.get("BASE_URL").getAsString()), new Configuration());
		
		System.in.read();
		httpServer.shutdown();
	}
		/*
		
		post("/available/update", (request, response) -> {
			if (request.queryMap().get("id").value() == null || request.queryMap().get("location").value() == null ||
					request.queryMap().get("state").value() == null)
				return gson.toJson(new ReturnMessage(false, "Fill all required field please"));
			for (User user : users) {
				if (user.getId().toString().equalsIgnoreCase(request.queryMap().get("id").value())) {
					String[] position = request.queryMap().get("location").value().split(",");
					user.setLast_geo(new Location(Double.valueOf(position[0]), Double.valueOf(position[1])));
					user.setAvailable(AvailableState.values()[Integer.parseInt(request.queryMap().get("state").value())]);
					return gson.toJson(new ReturnMessage(true, "Your status has been updated"));
				}
			}
			return gson.toJson(new ReturnMessage(false, "User not found"));
		});
		
		
		get("/available/request/all", (request, response) -> {
			for (User user : users) {
				if (user.getAvailable() != null && user.getAvailable().ordinal() != 0 && !user.getRegisterId().isEmpty()) {
					send_push(user, "request", "Etes-vous disponible pour être engagé sur une mission ?");
				}
			}
			return gson.toJson(new ReturnMessage(true, "Availability Requested"));
		});
		
		get("/available/request/:id", (request, response) -> {
			if (request.params(":id") == null)
				return gson.toJson(new ReturnMessage(false, "Fill all required field please"));
			for (User user : users) {
				if (user.getId().toString().equalsIgnoreCase(request.params(":id"))) {
					if (user.getAvailable().ordinal() == 0 && !user.getRegisterId().isEmpty()) {
						return gson.toJson(new ReturnMessage(false, "User is not available"));
					}
					send_push(user, "request", "Etes-vous disponible pour être engagé sur une mission ?");
					
					return gson.toJson(new ReturnMessage(true, "Availability Requested"));
				}
			}
			return gson.toJson(new ReturnMessage(false, "User not found"));
		});
		
		get("/engaged/show/all", (request, response) -> {
			JsonArray array = new JsonArray();
			for (User user : users) {
				if (user.getStatus() != StatusState.NONE) {
					JsonElement elem = gson.toJsonTree(user, User.class);
					array.add(elem);
				}
			}
			return array;
		});
		
		get("/engaged/show/:filtre", (request, response) -> {
			if (request.params(":filtre") == null || StatusState.valueOf(request.params(":filtre")) == null)
				return gson.toJson(new ReturnMessage(false, "Fill all required field please"));
			JsonArray array = new JsonArray();
			for (User user : users) {
				if (user.getStatus() == StatusState.valueOf(request.params(":filtre"))) {
					array.add(gson.toJson(user));
				}
			}
			return array;
		});
		
		post("/available/response", (request, response) -> {
			if (request.queryMap("id").value() == null || request.queryMap("status").value() == null)
				return gson.toJson(new ReturnMessage(false, "Fill all required field please"));
			for (User user : users) {
				if (user.getId().toString().equalsIgnoreCase(request.queryMap("id").value())) {
					if (request.queryMap("status").booleanValue()) {
						user.setAvailable(AvailableState.NO_AVAILABLE);
						if (request.queryMap().get("has_stuff").booleanValue())
							user.setStatus(StatusState.WAITING_ORDER_STUFF);
						else
							user.setStatus(StatusState.WAITING_ORDER);
						String[] position = request.queryMap().get("location").value().split(",");
						user.setLast_geo(new Location(Double.valueOf(position[0]), Double.valueOf(position[1])));
						return gson.toJson(new ReturnMessage(true, "Now, just WAITING for order."));
					} else {
						user.setAvailable(AvailableState.NO_AVAILABLE);
						return gson.toJson(new ReturnMessage(true, "You are not consired anymore as available."));
					}
					
				}
			}
			return gson.toJson(new ReturnMessage(false, "User not found"));
		});
		*/
	
	
	/* Send a push notification to an user */
	public void send_push(User user, String type, String message) {
		try {
			Sender sender = new Sender(GAPI_KEY);
			Result result = sender.send(new Message.Builder()
					.collapseKey("message")
					.timeToLive(120)
	                .delayWhileIdle(true)
					.addData("message", message)
					.addData("type", type)
					.build(), user.getRegisterId(), 3);
			System.out.println("Request avaibility on " + user.getName() + " >> messageId = " + result.getMessageId() + " && errorCode = " + result.getErrorCodeName());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
