package fr.vmarchaud.shareeat.services;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import org.mindrot.jbcrypt.BCrypt;

import fr.vmarchaud.shareeat.Core;
import fr.vmarchaud.shareeat.objects.Relation;
import fr.vmarchaud.shareeat.objects.User;
import fr.vmarchaud.shareeat.response.ProfileShowResponse;
import fr.vmarchaud.shareeat.utils.Utils;

public class UserService extends MasterService {
	
	private AuthService		authSrv	= Core.getInstance().getAuthService();
	
	/**
	 * Try to find an user by his name
	 * (try to use id instead of name)
	 * @param String the name that you want to find
	 * 
	 * @return User object if found, else null;
	 */
	public User		byName(String name) {
		for(User user : users) {
			if (user.getName().equals(name)) {
				return user;
			}
		}
		return null;
	}
	
	/**
	 * Try to find an user by his mail
	 * (try to use id instead of mail)
	 * @param String the mail that you want to find
	 * 
	 * @return User object if found, else null;
	 */
	public User		byMail(String mail) {
		for(User user : users) {
			if (user.getMail().equals(mail)) {
				return user;
			}
		}
		return null;
	}
	
	/**
	 * Try to find an user by his ID
	 * @param id String value of the UUID
	 * 
	 * @return User object if found, else null.
	 */
	public User		byId(String id) {
		if (!Utils.isUUID(id)) return null;
		
		for(User user : users) {
			if (user.getId().toString().equals(id)) {
				return user;
			}
		}
		return null;
	}
	
	/**
	 * Update an user in cache and request update for database
	 * @param user User object that you want to be updated
	 * @param datas Map that contains updated key/value 
	 * 
	 * @return true if updated, false else.
	 */
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
				case "school" : {
					user.setSchool(entry.getValue());;
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
	
	/**
	 * Create an user / Encrypt password / Gen UUID and try to save it
	 * 
	 * @param mail The mail of the user
	 * @param password Unencrypted password of the user
	 * @return User object created
	 */
	public User createUser(String mail, String password) {
		User user = new User();
		user.setId(UUID.randomUUID());
		user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
		user.setMail(mail);
		String accessToken = new BigInteger(130, random).toString(32);
		authSrv.addLoggedUser(accessToken, user);
		users.add(user);
		try {
			usersDao.create(user);
		} catch (SQLException e) {
			logger.error("exception while trying to register an new user", e);
			return null;
		}
		logger.info(user.getId() + " has been successfuly created");
		return user;
	}
	
	
	public ProfileShowResponse buildProfile(User user, boolean isFriend) {
		Map<String, String>		datas = new HashMap<String, String>();
		
		if (user.getDesc() != null && user.getDesc().length() > 0)
			datas.put("description", user.getDesc());
		
		if (user.getAvatar() != null && user.getAvatar().length() > 0)
			datas.put("avatar", user.getAvatar());
		
		if (user.getJob() != null && user.getJob().length() > 0)
			datas.put("job", user.getJob());
		
		if (user.getSchool() != null && user.getSchool().length() > 0)
			datas.put("school", user.getSchool());
		
		if (user.getTags() != null && user.getTags().length() > 0)
			datas.put("tags", user.getTags());
		
		if (isFriend) {
			datas.put("age", String.valueOf(user.getAge()));
			datas.put("mail", user.getMail());

			if (user.getPhone() != null && user.getPhone().length() > 0)
				datas.put("phone", user.getPhone());
		}
		return new ProfileShowResponse(datas);
	}
	
	
	
	// alarach
	public List<User> all() {
		return users;
	}
	
	public List<Relation> relations() {
		return relations;
	}
	
}
