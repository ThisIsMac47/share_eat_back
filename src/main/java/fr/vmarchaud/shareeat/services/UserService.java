package fr.vmarchaud.shareeat.services;

import java.util.List;
import fr.vmarchaud.shareeat.objects.User;

public class UserService extends MasterService {
	
	public User		byName(String name) {
		for(User user : users) {
			if (user.getName().equals(name)) {
				return user;
			}
		}
		return null;
	}
	
	public User		byId(String id) {
		
		for(User user : users) {
			if (user.getId().toString().equals(id)) {
				return user;
			}
		}
		return null;
	}
	
	public List<User> all() {
		return users;
	}
	
}
