package fr.vmarchaud.shareeat.services;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import fr.vmarchaud.shareeat.request.MeetupCreateRequest;

public class MeetupService {
	
	private UserService		userSrv = Core.getInstance().getUserService();
	private LocationService	locationSrv = Core.getInstance().getLocationService();
	private DataService		dataSrv = Core.getInstance().getDataService();
	
	/**
	 * Create a meetup from parameters
	 * @param User : the person that created the meetup
	 * @param Location : where this meetup will be
	 * @param String[] : list of tags for this meetup
	 * @param List<UUID> : the list of all user that has been invited
	 * @param String : the date (with time) of this meetup
	 * @param Integer : the price of this meetup (for the food)
	 * @return true if created and false if there was an error
	 */
	public boolean	createMeetup(User user, Location loc, String[] tags, List<UUID> invitedIds, Date date, int price) {
		Meetup meetup = new Meetup();
		
		// Set default information
		meetup.setId(UUID.randomUUID());
		meetup.setMaster(user);
		meetup.setLocation(loc);
		meetup.setTags(Arrays.stream(tags).collect(Collectors.joining(",")));
		meetup.setPrice(price);
		meetup.setDate(date);
		meetup.setDone(false);
		
		// For each id, try to get user from and create invitation for each of them
		List<Invitation> invitations = new ArrayList<Invitation>();
		for(UUID userId : invitedIds) {
			User tmp = userSrv.byId(userId);
			if (tmp == null)
				return false;
			Invitation invit =  new Invitation(user, tmp, EnumInvitation.MEETUP, meetup, EnumState.NONE);
			invitations.add(invit);
			try {
				dataSrv.invitationsDao.create(invit);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			invit.getReceiver().getInvitations().add(invit);
		}
		// If we had created all invitation successfuly, register them in the meetup
		meetup.setUsers(invitations);
		
		// And finally create the meetup on the database and register it for the master
		try {
			dataSrv.meetupsDao.create(meetup);
			user.getMeetups().add(meetup);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		// If meetup has been succesfuly created
		return true;
	}
}
