package fr.vmarchaud.shareeat.services;

import fr.vmarchaud.shareeat.Core;
import fr.vmarchaud.shareeat.objects.User;

public class AuthService {
		
	private UserService	userService = Core.getInstance().getUserService();
	
	public User verify(String id, String token) {
		User user = userService.byId(id);
		if (user != null && user.getAccessToken().equals(token))
			return user;
		else
			return null;
	}
}
