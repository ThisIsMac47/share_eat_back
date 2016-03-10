package fr.vmarchaud.shareeat.services;

import java.util.UUID;

import fr.vmarchaud.shareeat.objects.Location;

public class LocationService extends MasterService {
	
	/**
	 * Find a location by his name
	 * @param name
	 * @return
	 */
	public Location		byName(String name) {
		return locations.stream().filter(location -> location.getName().equals(name)).findFirst().orElse(null);
	}
	
	/**
	 * Find a location by his id
	 * @param name
	 * @return
	 */
	public Location		byId(String id) {
		return locations.stream().filter(location -> location.getId().compareTo(UUID.fromString(id)) == 0).findFirst().orElse(null);
	}
	
}
