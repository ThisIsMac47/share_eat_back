package fr.vmarchaud.shareeat.services;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import fr.vmarchaud.shareeat.Core;
import fr.vmarchaud.shareeat.enums.EnumInvitation;
import fr.vmarchaud.shareeat.enums.EnumState;
import fr.vmarchaud.shareeat.objects.Invitation;
import fr.vmarchaud.shareeat.objects.Location;
import fr.vmarchaud.shareeat.objects.Meetup;
import fr.vmarchaud.shareeat.objects.User;

public class MeetupService {
	
	private UserService		userSrv = Core.getInstance().getUserService();
	private DataService		dataSrv = Core.getInstance().getDataService();
	
	/**
	 * Find a Meetup by his name
	 * 
	 * @param name
	 * 
	 * @return Meetup or null if not found
	 */
	public Meetup		byName(String name) {
		return dataSrv.getMeetups().stream().filter(meetup -> meetup.getName().equals(name)).findFirst().orElse(null);
	}
	
	/**
	 * Find a Meetup by his id
	 * 
	 * @param String
	 * 
	 * @return Meetup or null if not found
	 */
	public Meetup		byId(String id) {
		return dataSrv.getMeetups().stream().filter(meetup -> meetup.getId().compareTo(UUID.fromString(id)) == 0).findFirst().orElse(null);
	}
	
	/**
	 * Find a Meetup by his id
	 * 
	 * @param UUID
	 * 
	 * @return Meetup or null if not found
	 */
	public Meetup		byId(UUID id) {
		return dataSrv.getMeetups().stream().filter(meetup -> meetup.getId().compareTo(id) == 0).findFirst().orElse(null);
	}
	
	/**
	 * Get if an user is part of this meetup
	 * 
	 * @param meetup : the meetup you want to check the user
	 * @param user : the user
	 *
	 * @return true if user is part of the meetup, else false.
	 */
	public boolean		containsUser(Meetup meetup, User user) {
		Invitation invitation = meetup.getUsers().stream().filter(invit -> invit.getReceiver().getId().compareTo(user.getId()) == 0).findFirst().orElse(null);
		if (invitation != null || user.getId().compareTo(meetup.getMaster().getId()) == 0)
			return true;
		return false;
	}
	
	/**
	 * Create a meetup from parameters
	 * 
	 * @param String : name of the meetup
	 * @param User : the person that created the meetup
	 * @param Location : where this meetup will be
	 * @param String[] : list of tags for this meetup
	 * @param List<UUID> : the list of all user that has been invited
	 * @param String : the date (with time) of this meetup
	 * @param Integer : the price of this meetup (for the food)
	 * 
	 * @return Meetup if created and null if there was an error
	 */
	public Meetup	createMeetup(String name, User user, Location loc, String[] tags, List<UUID> invitedIds, String date) {
		Meetup meetup = new Meetup();
		
		// Set default information
		meetup.setId(UUID.randomUUID());
		meetup.setMaster(user);
		meetup.setLocation(loc);
		meetup.setTags(Arrays.stream(tags).collect(Collectors.joining(",")));
		meetup.setDate(date);
		meetup.setState(EnumState.WAITING);
		meetup.setName(name);
		
		// For each id, try to get user from and create invitation for each of them
		for(UUID userId : invitedIds) {
			User tmp = userSrv.byId(userId);
			if (tmp == null)
				return null;
			tmp.getInvitations().add(new Invitation(user, tmp, EnumInvitation.MEETUP, meetup, EnumState.WAITING));
		}
		// And finally create the meetup on the database and register it for the master
		user.getMeetups().add(meetup);
		// If meetup has been succesfuly created
		return meetup;
	}
}
