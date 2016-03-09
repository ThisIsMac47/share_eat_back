package fr.vmarchaud.shareeat.services;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.db.DatabaseTypeUtils;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import fr.vmarchaud.shareeat.Core;
import fr.vmarchaud.shareeat.objects.Location;
import fr.vmarchaud.shareeat.objects.Mealplan;
import fr.vmarchaud.shareeat.objects.Relation;
import fr.vmarchaud.shareeat.objects.Tag;
import fr.vmarchaud.shareeat.objects.User;
import lombok.Getter;


public class MasterService {

	// DB constants
	public static final Gson gson = Core.getInstance().gson;

	// Internal
	protected ConnectionSource 	conn = null;
	protected Logger 			logger = LogManager.getLogger();
	protected SecureRandom 		random = new SecureRandom();
	
	protected Dao<User, String> 	usersDao;
	protected Dao<Relation, String> relationsDao;
	protected Dao<Location, String> locationsDao;
	protected Dao<Mealplan, String> mealplansDao;
	protected Dao<Tag, String>	tagsDao;
	
	@Getter protected List<User> 			users;
	@Getter protected List<Location>		locations;
	@Getter protected List<String>			tags = new ArrayList<String>();

	private boolean started = false;
	
	public MasterService() {
		// If we are starting, load stuff
		if (started == false) {
			started = true;
			long start = System.currentTimeMillis();
			// Loading driver
			try {
				Class.forName("com.mysql.jdbc.Driver");
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
				locationsDao = DaoManager.createDao(conn, Location.class);
				mealplansDao = DaoManager.createDao(conn, Mealplan.class);
				tagsDao = DaoManager.createDao(conn, Tag.class);
			} catch (SQLException e) {
				logger.error("cannot create DAO", e);
				System.exit(0);
			}
			
			// Query all data
			try {
				users = usersDao.queryForAll();
				locations = locationsDao.queryForAll();
				relationsDao.queryForAll();
				mealplansDao.queryForAll();
				tagsDao.queryForAll().forEach(tag -> {
					tags.add(tag.getValue());
				});
			} catch (SQLException e) {
				logger.error("cannot query all data from DAO", e);
				System.exit(0);
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
