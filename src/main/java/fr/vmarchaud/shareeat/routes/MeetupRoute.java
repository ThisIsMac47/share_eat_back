package fr.vmarchaud.shareeat.routes;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import fr.vmarchaud.shareeat.Core;
import fr.vmarchaud.shareeat.objects.Location;
import fr.vmarchaud.shareeat.objects.User;
import fr.vmarchaud.shareeat.request.MeetupCreateRequest;
import fr.vmarchaud.shareeat.services.LocationService;
import fr.vmarchaud.shareeat.services.MeetupService;
import fr.vmarchaud.shareeat.services.UserService;

@Path("/meetup")
@RolesAllowed("USER")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MeetupRoute {

	private UserService		userSrv = Core.getInstance().getUserService();
	private MeetupService	meetupSrv = Core.getInstance().getMeetupService();
	private LocationService	locationSrv = Core.getInstance().getLocationService();
	
	@Path("create")
	@POST
	public Response createMeetup(MeetupCreateRequest request, @Context ContainerRequestContext context) {
		if (request == null || !request.isValid())
			return Response.status(Status.BAD_REQUEST).build();
		
		User user = (User)context.getProperty("user");
		Location loc = locationSrv.byId(request.getLocation());
		if (loc == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(Status.CREATED).build();
	}
}
