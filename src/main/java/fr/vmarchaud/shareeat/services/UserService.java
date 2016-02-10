package fr.vmarchaud.shareeat.services;

import java.util.ArrayList;

import fr.vmarchaud.shareeat.objects.User;
import fr.vmarchaud.shareeat.utils.Utils;

public class UserService extends IService {
	
	public UserService() {
		super();
	}
	
	public User		byName(String name) {
		return null;
	}
	
	public User		byId(String id) {
		if (!Utils.isUUID(id)) return null;
		return null;
	}
	
	public ArrayList<Object> all() {
		return null;
	}
	
}
