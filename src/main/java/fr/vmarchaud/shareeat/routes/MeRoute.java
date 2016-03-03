package fr.vmarchaud.shareeat.routes;

import java.util.UUID;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
import fr.vmarchaud.shareeat.request.UserProfileUpdateRequest;
import fr.vmarchaud.shareeat.services.UserService;
import fr.vmarchaud.shareeat.utils.Utils;

@Path("/me")
@RolesAllowed("USER")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MeRoute {
	
	private UserService	userSrv = Core.getInstance().getUserService();
	
	@Path("update")
	@POST
	public Response updateProfile(UserProfileUpdateRequest request, @Context ContainerRequestContext context) {
		if (!request.isValid())
			return Response.status(Status.BAD_REQUEST).build();
		
		User user = (User)context.getProperty("user");
		if (userSrv.updateUser(user, request.getDatas()))
			return Response.ok().build();
		else
			return Response.status(Status.NOT_MODIFIED).build();
	}
	
	@Path("profile")
	@GET
	public Response	getProfile(@Context ContainerRequestContext context) {
		User user = (User)context.getProperty("user");
		return Response.ok(userSrv.buildProfile(user, true)).build();
	}
	
	@Path("friends")
	@GET
	public Response	getFriends(@Context ContainerRequestContext context) {
		User user = (User)context.getProperty("user");
		return Response.ok(user.getFriends()).build();
	}
	
	@Path("friends/{id}")
	@PUT
	public Response	putFriend(@PathParam("id") String id, @Context ContainerRequestContext context) {
		if (!Utils.isUUID(id))
			return Response.status(Status.BAD_REQUEST).build();
		
		User user = (User)context.getProperty("user");
		User friend = userSrv.byId(id);
		if (friend == null)
			return Response.status(Status.NOT_FOUND).build();
		user.getFriends().add(friend.getId());
		return Response.ok().build();
	}
	
	@Path("friends/{id}")
	@DELETE
	public Response	deleteFriend(@PathParam("id") String id, @Context ContainerRequestContext context) {
		if (!Utils.isUUID(id))
			return Response.status(Status.BAD_REQUEST).build();
		
		User user = (User)context.getProperty("user");
		UUID friend = UUID.fromString(id);
		if (friend == null)
			return Response.status(Status.NOT_FOUND).build();
		user.getFriends().remove(friend);
		return Response.ok().build();
	}
}
