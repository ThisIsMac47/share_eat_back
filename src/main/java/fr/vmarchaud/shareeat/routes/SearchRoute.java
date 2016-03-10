package fr.vmarchaud.shareeat.routes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
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

import fr.vmarchaud.shareeat.Core;
import fr.vmarchaud.shareeat.objects.Location;
import fr.vmarchaud.shareeat.objects.User;
import fr.vmarchaud.shareeat.request.SearchUserByTagRequest;
import fr.vmarchaud.shareeat.services.MeetupService;
import fr.vmarchaud.shareeat.services.UserService;

@Path("/search")
@RolesAllowed("USER")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SearchRoute {

	private UserService		userSrv = Core.getInstance().getUserService();
	private MeetupService	meetupSrv = Core.getInstance().getMeetupService();
	
	@Path("user/tags")
	@POST
	public Response	searchUserByTag(SearchUserByTagRequest request, @Context ContainerRequestContext context) {
		if (request == null || !request.isValid())
			return Response.status(Status.BAD_REQUEST).build();
		
		List<User> users =  userSrv.all().stream().filter(user -> user.tags != null &&
				Collections.disjoint(Arrays.asList(user.getTags().split(",")), request.getTags()) == false).collect(Collectors.toCollection(ArrayList::new));
		
		List<UUID> response = new ArrayList<UUID>();
		for(User user : users) 
			response.add(user.getId());
		
		return Response.ok(response).build();
	}
	
	@Path("location/{type}")
	@GET
	public Response	searchLocationByType(@PathParam("type") String type, @Context ContainerRequestContext context) {
		if (type == null || type.length() == 0)
			return Response.status(Status.BAD_REQUEST).build();
		
		List<Location>	locations = meetupSrv.getLocations().stream().filter(location -> location.getType().toLowerCase().contains(type.toLowerCase())).collect(Collectors.toList());
		
		List<UUID> response = new ArrayList<UUID>();
		for(Location loc : locations) 
			response.add(loc.getId());
		
		return Response.ok(response).build();
	}
}
