package fr.vmarchaud.croixrouge.dbs;

import java.util.List;

import fr.vmarchaud.croixrouge.entities.DegreeEntity;
import fr.vmarchaud.croixrouge.entities.UserEntity;

public interface IDatabase {
	
	/**
	 * Extract all users from the database.
	 * 
	 * @return a List of all the users;
	 */
	public List<UserEntity> queryUsers();
	
	/**
	 * Extract all degrees from the database.
	 * 
	 * @return a List of all the degrees;
	 */
	public List<DegreeEntity> queryDegrees();
}
