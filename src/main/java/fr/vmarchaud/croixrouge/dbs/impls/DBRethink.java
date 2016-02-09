package fr.vmarchaud.croixrouge.dbs.impls;

import java.util.List;
import java.util.concurrent.TimeoutException;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;

import fr.vmarchaud.croixrouge.Core;
import fr.vmarchaud.croixrouge.dbs.IDatabase;
import fr.vmarchaud.croixrouge.entities.DegreeEntity;
import fr.vmarchaud.croixrouge.entities.UserEntity;

public class DBRethink implements IDatabase {
	
	public static final RethinkDB r = RethinkDB.r;
	private Connection				conn;
	
	public DBRethink(String host, int port) {
		try {
			conn = r.connection().db("croixrouge").hostname(host).port(port).connect();
		} catch (TimeoutException e) {
			Core.getLogger().error("RethinkDB unreachable", e);
		}
	}
	
	@Override
	public List<UserEntity> queryUsers() {
		Cursor<UserEntity> cursor = r.table("users").run(conn);
		return cursor.toList();
	}

	@Override
	public List<DegreeEntity> queryDegrees() {
		Cursor<DegreeEntity> cursor = r.table("degrees").run(conn);
		return cursor.toList();
	}

}
