package fr.vmarchaud.shareeat.services;

import java.util.concurrent.TimeoutException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;

import fr.vmarchaud.shareeat.Core;
import fr.vmarchaud.shareeat.utils.CustomConfig;

public class IService {
	

	// DB constants
	public static final String		USR_TBL = "users";
	public static final String		METP_TBL = "meetups";
	public static final String		LOC_TBL = "locations";
	public static final String		DB = Core.getInstance().getEnv().toString().toLowerCase();
	public static final Gson		gson = Core.getInstance().gson;
	
	// Internal
	public static final RethinkDB r = RethinkDB.r;
	private Connection conn = null;
	private Logger logger = LogManager.getLogger();
	
	public IService() { 
		if (conn == null) {
			try {
				conn = r.connection().hostname(CustomConfig.HOSTNAME).port(CustomConfig.PORT).connect();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}
		}
	}
}
