package fr.vmarchaud.shareeat.services;

import java.util.HashMap;
import java.util.Map;

import fr.vmarchaud.shareeat.Core;
import fr.vmarchaud.shareeat.objects.User;
import fr.vmarchaud.shareeat.utils.Utils;

public class AuthService extends MasterService{
		
	private UserService			userService = Core.getInstance().getUserService();
	private Map<String, User>	loggedUsers;
	
	public AuthService() {
		super();
		loggedUsers = new HashMap<String, User>();
		for(User user : users) {
			if (!user.getAccessToken().isEmpty()) {
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
		if (id.equals(user.getId()))
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
