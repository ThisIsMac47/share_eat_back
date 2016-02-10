package fr.vmarchaud.shareeat.services;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.j256.ormlite.db.DatabaseTypeUtils;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import fr.vmarchaud.shareeat.Core;

public class IService {
	

	// DB constants
	public static final String		USR_TBL 	= 	"users";
	public static final String		METP_TBL 	= 	"meetups";
	public static final String		LOC_TBL		= 	"locations";
	public static final String		DB 			= 	Core.getInstance().getConfig().ENV.toString().toLowerCase();
	public static final Gson		gson 		= 	Core.getInstance().gson;
	
	// Internal
	protected ConnectionSource conn = null;
	protected Logger logger = LogManager.getLogger();
	
	public IService() { 
		if (conn == null) {
			
			// Loading driver
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			
			// Starting the connection
			try {
				conn = new JdbcPooledConnectionSource(Core.getInstance().getConfig().HOSTNAME, Core.getInstance().getConfig().USERNAME, Core.getInstance().getConfig().PASSWORD
						, DatabaseTypeUtils.createDatabaseType(Core.getInstance().getConfig().HOSTNAME));
			} catch (SQLException e) {
				logger.error("cannot create jdbc connection", e);
			}
		}
	}
}
