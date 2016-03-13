package fr.vmarchaud.shareeat.services;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.vmarchaud.shareeat.Core;
import fr.vmarchaud.shareeat.objects.User;
import fr.vmarchaud.shareeat.utils.Utils;

public class AuthService {
		
	private UserService			userSrv = Core.getInstance().getUserService();
	private DataService			dataSrv = Core.getInstance().getDataService();
	private Logger 				logger = LogManager.getLogger();
	
	private Map<String, User>	loggedUsers;
	
	public AuthService() {
		loggedUsers = new HashMap<String, User>();
		for(User user : dataSrv.getUsers()) {
			if (user.accessToken != null && !user.getAccessToken().isEmpty()) {
				loggedUsers.put(user.getAccessToken(), user);
			}
		}
	}
	
	/**
	 * Verify that this token is valid and linked to this id
	 * 
	 * @param id UUID of the user
	 * @param token AccessToken of the user
	 * @return User if found and valid, else null.
	 */
	public User verify(String id, String token) {
		if (!Utils.isUUID(id)) return null;
		if (!loggedUsers.containsKey(token)) return null;
		
		User user = loggedUsers.get(token);
		if (id.equalsIgnoreCase(user.getId().toString()))
			return user;
		else
			return null;
	}
	
	/**
	 * Add this user as logged with this token 
	 * 
	 * @param token accessToken of the user
	 * @param user User object
	 */
	public void		addLoggedUser(String token, User user) {
		loggedUsers.put(token, user);
		user.setAccessToken(token);
	}
}
