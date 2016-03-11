package fr.vmarchaud.shareeat.routes;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import fr.vmarchaud.shareeat.Core;
import fr.vmarchaud.shareeat.objects.User;
import fr.vmarchaud.shareeat.services.MeetupService;
import fr.vmarchaud.shareeat.services.UserService;
import fr.vmarchaud.shareeat.utils.Utils;

@Path("/meetup")
@RolesAllowed("USER")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MeetupRoute {

	private UserService		userSrv = Core.getInstance().getUserService();
	private MeetupService	meetupSrv = Core.getInstance().getMeetupService();
	
	@Path("create")
	@GET
	public Response show(@PathParam("id") String id, @Context ContainerRequestContext context) {
		if (!Utils.isUUID(id))
			return Response.status(Status.BAD_REQUEST).build();
		
		User user = (User)context.getProperty("user");
		User profile = userSrv.byId(id);
		if (profile == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(userSrv.buildProfile(profile, user.getFriends().contains(profile.getId()))).build();
	}
}
