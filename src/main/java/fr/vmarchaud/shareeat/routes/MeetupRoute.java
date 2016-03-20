package fr.vmarchaud.shareeat.routes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.JsonObject;

import fr.vmarchaud.shareeat.Core;
import fr.vmarchaud.shareeat.enums.EnumState;
import fr.vmarchaud.shareeat.objects.Invitation;
import fr.vmarchaud.shareeat.objects.Location;
import fr.vmarchaud.shareeat.objects.Meetup;
import fr.vmarchaud.shareeat.objects.User;
import fr.vmarchaud.shareeat.request.MeetupCreateRequest;
import fr.vmarchaud.shareeat.services.LocationService;
import fr.vmarchaud.shareeat.services.MeetupService;
import fr.vmarchaud.shareeat.services.StripeService;
import fr.vmarchaud.shareeat.services.UserService;
import fr.vmarchaud.shareeat.utils.Utils;

@Path("/meetup")
@RolesAllowed("USER")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MeetupRoute {

	private UserService		userSrv = Core.getInstance().getUserService();
	private MeetupService	meetupSrv = Core.getInstance().getMeetupService();
	private LocationService	locationSrv = Core.getInstance().getLocationService();
	private StripeService	stripeSrv = Core.getInstance().getStripeService();
	
	
	@Path("create")
	@POST
	public Response createMeetup(MeetupCreateRequest request, @Context ContainerRequestContext context) {
		if (request == null || !request.isValid())
			return Response.status(Status.BAD_REQUEST).build();
		
		// Get user from the context
		User user = (User)context.getProperty("user");
		
		// Get if the location actually exist in our database
		Location loc = locationSrv.byId(request.getLocation());
		if (loc == null)
			return Response.status(Status.NOT_FOUND).build();
		
		// Try to parse the string to date to verify if its a valid date
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(request.getDate());
		} catch (ParseException e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		// Ask to create the service and from the result, return created or not.
		Meetup meetup = meetupSrv.createMeetup(request.getName(), user, loc, request.getTags(), request.getInvited(), request.getDate(), request.getMealplan());
		if (meetup != null)
			return Response.ok(meetup.getId()).build();
		else
			return Response.ok(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	@Path("payement")
	@POST
	public Response	pay(JsonObject request, @Context ContainerRequestContext context) {
		if (request.get("id") == null || request.get("token") == null || !Utils.isUUID(request.get("id").getAsString()))
			return Response.status(Status.BAD_REQUEST).build();
		
		// Get all data we need
		User user = (User)context.getProperty("user");
		Meetup meetup = meetupSrv.byId(request.get("id").getAsString());
		String token = request.get("token").getAsString();
		boolean state = stripeSrv.chargeUser(meetup, user, token);
		if (state) {
			// If the master had payed, we throw all invitations
			if (user.getId().compareTo(meetup.getMaster().getId()) == 0) {
				meetup.setState(EnumState.WAITING);
				meetup.getUsers().forEach(invit -> {
					invit.setState(EnumState.WAITING);
				});
			}
			// Else if its just an invited users
			else {
				Invitation invitation = meetup.getUsers().stream().filter(invit -> invit.getReceiver().getId().compareTo(user.getId()) == 0).findFirst().orElse(null);
				invitation.setState(EnumState.ACCEPTED);
			}
			return Response.status(Status.ACCEPTED).build();
		}
		else
			return Response.status(Status.NOT_ACCEPTABLE).build();
	}
}
