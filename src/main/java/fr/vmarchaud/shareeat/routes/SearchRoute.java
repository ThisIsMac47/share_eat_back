package fr.vmarchaud.shareeat.routes;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
import fr.vmarchaud.shareeat.objects.User;
import fr.vmarchaud.shareeat.request.SearchUserByTagRequest;
import fr.vmarchaud.shareeat.services.UserService;

@Path("/search")
@RolesAllowed("USER")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SearchRoute {

	private UserService		userSrv = Core.getInstance().getUserService();
	
	@Path("/user/tags")
	@POST
	public Response	searchUserByTag(SearchUserByTagRequest request, @Context ContainerRequestContext context) {
		if (!request.isValid())
			return Response.status(Status.BAD_REQUEST).build();
		List<User> users = userSrv.all();
		users.removeIf(user -> user.tags != null && Collections.disjoint(Arrays.asList(user.getTags().split(",")), request.getTags()));
		
		return Response.ok(users).build();
	}
}
