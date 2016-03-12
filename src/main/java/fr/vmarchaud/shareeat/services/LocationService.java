package fr.vmarchaud.shareeat.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
	 * @param String
	 * @return Location or null if not found
	 */
	public Location		byId(String id) {
		return locations.stream().filter(location -> location.getId().compareTo(UUID.fromString(id)) == 0).findFirst().orElse(null);
	}
	
	/**
	 * Find a location by his id
	 * @param UUID
	 * @return Location or null if not found
	 */
	public Location		byId(UUID id) {
		return locations.stream().filter(location -> location.getId().compareTo(id) == 0).findFirst().orElse(null);
	}
	
	/**
	 * Find one or more location if one of his mealplan cost equal or less than price
	 * @param price
	 * @return a List of Location or empty list if nothing found
	 */
	public List<Location>		byPrice(int price) {
		return locations.stream().filter(location -> location.plans != null && location.getPlans().stream().anyMatch(plans -> plans.getPrice() == price))
				.collect(Collectors.toCollection(ArrayList::new));
	}
}
