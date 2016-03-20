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
	private LocationService	locationSrv = Core.getInstance().getLocationService();
	private DataService		dataSrv = Core.getInstance().getDataService();
	
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
	 * @return true if created and false if there was an error
	 */
	public boolean	createMeetup(String name, User user, Location loc, String[] tags, List<UUID> invitedIds, String date, int price) {
		Meetup meetup = new Meetup();
		
		// Set default information
		meetup.setId(UUID.randomUUID());
		meetup.setMaster(user);
		meetup.setLocation(loc);
		meetup.setTags(Arrays.stream(tags).collect(Collectors.joining(",")));
		meetup.setPrice(price);
		meetup.setDate(date);
		meetup.setDone(false);
		meetup.setName(name);
		
		// For each id, try to get user from and create invitation for each of them
		for(UUID userId : invitedIds) {
			User tmp = userSrv.byId(userId);
			if (tmp == null)
				return false;
			tmp.getInvitations().add(new Invitation(user, tmp, EnumInvitation.MEETUP, meetup, EnumState.NONE));
		}
		// And finally create the meetup on the database and register it for the master
		user.getMeetups().add(meetup);
		// If meetup has been succesfuly created
		return true;
	}
}
