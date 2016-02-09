package fr.vmarchaud.croixrouge.services;

import fr.vmarchaud.croixrouge.Core;
import fr.vmarchaud.croixrouge.objects.User;

public class AuthService {
		
	private UserService	userService = Core.getInstance().getUserService();
	
	public boolean login(User user, String password, String lastToken) {
		return false;
	}
}
