package fr.vmarchaud.shareeat.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import fr.vmarchaud.shareeat.Core;
import fr.vmarchaud.shareeat.enums.EnumInvitation;
import fr.vmarchaud.shareeat.objects.Invitation;
import fr.vmarchaud.shareeat.objects.Location;
import fr.vmarchaud.shareeat.objects.Meetup;
import fr.vmarchaud.shareeat.objects.User;
import fr.vmarchaud.shareeat.request.MeetupCreateRequest;

public class MeetupService extends MasterService {
	
	private UserService		userSrv = Core.getInstance().getUserService();
	private LocationService	locationSrv = Core.getInstance().getLocationService();
	
	public boolean	createMeetup(User user, Location loc, MeetupCreateRequest request) {
		Meetup meetup = new Meetup();
		meetup.setId(UUID.randomUUID());
		meetup.setMaster(user);
		meetup.setLocation(loc);
		meetup.setTags(Arrays.stream(request.getTags()).collect(Collectors.joining(",")));
		meetup.setDone(false);
		
		List<Invitation> invitations = new ArrayList<Invitation>();
		for(UUID userId : request.getInvited()) {
			User tmp = userSrv.byId(userId);
			if (tmp == null)
				return false;
			invitations.add(new Invitation(user, tmp, EnumInvitation.MEETUP, meetup, false));
		}
		return true;
	}
}
