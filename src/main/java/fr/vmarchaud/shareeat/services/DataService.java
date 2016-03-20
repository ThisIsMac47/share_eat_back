package fr.vmarchaud.shareeat.services;

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
import fr.vmarchaud.shareeat.objects.Invitation;
import fr.vmarchaud.shareeat.objects.Location;
import fr.vmarchaud.shareeat.objects.Mealplan;
import fr.vmarchaud.shareeat.objects.Meetup;
import fr.vmarchaud.shareeat.objects.Payement;
import fr.vmarchaud.shareeat.objects.Relation;
import fr.vmarchaud.shareeat.objects.Tag;
import fr.vmarchaud.shareeat.objects.User;
import fr.vmarchaud.shareeat.utils.CustomConfig;
import lombok.Getter;


public class DataService {

	// DB constants
	public static final Gson gson = Core.getInstance().gson;

	// Internal
	protected ConnectionSource 	conn = null;
	protected Logger 			logger = LogManager.getLogger();
	
	protected Dao<User, String> 	usersDao;
	protected Dao<Relation, String> relationsDao;
	protected Dao<Location, String> locationsDao;
	protected Dao<Mealplan, String> mealplansDao;
	protected Dao<Meetup, String> 	meetupsDao;
	protected Dao<Invitation, String> invitationsDao;
	protected Dao<Payement, String> payementsDao;
	protected Dao<Tag, String>	tagsDao;
	
	// All the internal data
	@Getter protected List<User> 			users;
	@Getter protected List<Location>		locations;
	@Getter protected List<Meetup>			meetups;
	@Getter protected List<Invitation>		invitations;
	@Getter protected List<Payement>		payements;
	@Getter protected List<String>			tags = new ArrayList<String>();

	private boolean started = false;
	
	public DataService() {
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
				Core.getInstance().getConfig();
				Core.getInstance().getConfig();
				Core.getInstance().getConfig();
				Core.getInstance().getConfig();
				conn = new JdbcPooledConnectionSource(CustomConfig.HOSTNAME, CustomConfig.USERNAME, CustomConfig.PASSWORD,
														DatabaseTypeUtils.createDatabaseType(CustomConfig.HOSTNAME));
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
				meetupsDao = DaoManager.createDao(conn, Meetup.class);
				invitationsDao = DaoManager.createDao(conn, Invitation.class);
				payementsDao = DaoManager.createDao(conn, Payement.class);
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
				meetups = meetupsDao.queryForAll();
				invitations = invitationsDao.queryForAll();
				payements = payementsDao.queryForAll();
				
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
