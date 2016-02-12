package fr.vmarchaud.shareeat.services;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.db.DatabaseTypeUtils;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import fr.vmarchaud.shareeat.Core;
import fr.vmarchaud.shareeat.objects.Relation;
import fr.vmarchaud.shareeat.objects.User;


public class MasterService {

	// DB constants
	public static final Gson gson = Core.getInstance().gson;

	// Internal
	protected ConnectionSource 	conn = null;
	protected Logger 			logger = LogManager.getLogger();
	protected SecureRandom 		random = new SecureRandom();
	
	protected Dao<User, String> 	usersDao;
	protected Dao<Relation, String> relationsDao;
	protected List<User> 			users;
	protected List<Relation> 		relations;

	public MasterService() {
		// If we are starting, load stuff
		if (conn == null) {
			long start = System.currentTimeMillis();
			// Loading driver
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e1) {
				logger.info("Cannot load database driver");
				System.exit(0);
			}
	
			// Starting the connection
			try {
				conn = new JdbcPooledConnectionSource(Core.getInstance().getConfig().HOSTNAME,
						Core.getInstance().getConfig().USERNAME, Core.getInstance().getConfig().PASSWORD,
						DatabaseTypeUtils.createDatabaseType(Core.getInstance().getConfig().HOSTNAME));
				logger.info("Connection with database etablished");
			} catch (SQLException e) {
				logger.error("cannot create jdbc connection", e);
				System.exit(0);
			}
			
			// Register dao
			try {
				usersDao = DaoManager.createDao(conn, User.class);
				relationsDao = DaoManager.createDao(conn, Relation.class);
			} catch (SQLException e) {
				logger.error("cannot create DAO", e);
				System.exit(0);
			}
			
			// Query all data
			try {
				users = usersDao.queryForAll();
				relations = relationsDao.queryForAll();
			} catch (SQLException e) {
				logger.error("cannot query all users from DAO", e);
				System.exit(0);
			}
			
			// Link user with their data
			for (Relation relation : relations) {
				for (User user : users) {
					if (user.friends == null) {
						user.friends = new ArrayList<UUID>();
					}
					if (relation.getFriend().compareTo(user.getId()) == 0) {
						user.friends.add(relation.getUser());
					}
					if (relation.getUser().compareTo(user.getId()) == 0) { 
						user.friends.add(relation.getFriend());
					}
				}
			}
			
			logger.info("Data loaded in " + (System.currentTimeMillis() - start) + " ms");
		}
	}
	
	
	public void	close() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
