package fr.vmarchaud.croixrouge.services;

import java.util.List;
import java.util.UUID;

import fr.vmarchaud.croixrouge.objects.User;
import fr.vmarchaud.croixrouge.utils.Utils;
import lombok.Getter;

public class UserService {
		
	@Getter private	List<User>		users;
	
	public UserService(List<User> users) {
		this.users = users;
	}
	
	
	public User		byName(String name) {
		for(User user : users) {
			if (user.getName().equals(name))
				return user;
		}
		return null;
	}
	
	public User		byId(String id) {
		if (!Utils.isUUID(id)) return null;
		
		for(User user : users) {
			if (user.getId().compareTo(UUID.fromString(id)) == 0)
				return user;
		}
		return null;
	}
	
}
