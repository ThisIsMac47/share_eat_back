package fr.vmarchaud.shareeat.services;

import fr.vmarchaud.shareeat.Core;
import fr.vmarchaud.shareeat.objects.User;

public class AuthService {
		
	private UserService	userService = Core.getInstance().getUserService();
	
	public boolean verify(User user, String token) {
		return false;
	}
}
