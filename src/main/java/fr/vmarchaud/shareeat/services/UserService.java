package fr.vmarchaud.shareeat.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import fr.vmarchaud.shareeat.objects.User;
import fr.vmarchaud.shareeat.utils.Utils;

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
		if (!Utils.isUUID(id)) return null;
		
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
	
	public boolean updateUser(User user, Map<String, String> datas) {
		for(Entry<String, String> entry : datas.entrySet()) {
			switch (entry.getValue()) {
				case "mail" : {
					user.setMail(entry.getValue());
					break ;
				}
				case "phone" : {
					user.setPhone(entry.getValue());
					break ;
				}
				case "age" : {
					user.setAge(Short.valueOf(entry.getValue()));
					break ;
				}
				case "tags" : {
					user.setTags(entry.getValue());
					break ;
				}
				case "description" : {
					user.setDesc(entry.getValue());
					break ;
				}
				case "background" : {
					user.setBackground(entry.getValue());
					break ;
				}
				case "avatar" : {
					user.setAvatar(entry.getValue());
					break ;
				}
				case "job" : {
					user.setJob(entry.getValue());
					break ;
				}
				default : {
					return false;
				}
			}
		}
		try {
			usersDao.update(user);
			logger.info(user.getId() + " has been successfuly updated");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
}
