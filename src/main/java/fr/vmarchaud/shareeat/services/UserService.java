package fr.vmarchaud.shareeat.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fr.vmarchaud.shareeat.objects.User;
import fr.vmarchaud.shareeat.utils.Utils;
import lombok.Getter;

public class UserService {
		
	@Getter private	List<User>		users = new ArrayList<User>();
	
	public UserService(List<User> users) {
		this.users.addAll(users);
	}
	
	public User		byName(String name) {
		for(User user : users) {
			if (user.name != null && user.getName().equals(name))
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
