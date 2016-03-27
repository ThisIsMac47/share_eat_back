package fr.vmarchaud.shareeat.routes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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

import com.google.gson.JsonObject;

import fr.vmarchaud.shareeat.Core;
import fr.vmarchaud.shareeat.enums.EnumInvitation;
import fr.vmarchaud.shareeat.enums.EnumState;
import fr.vmarchaud.shareeat.objects.Invitation;
import fr.vmarchaud.shareeat.objects.Meetup;
import fr.vmarchaud.shareeat.objects.User;
import fr.vmarchaud.shareeat.services.LocationService;
import fr.vmarchaud.shareeat.services.MeetupService;
import fr.vmarchaud.shareeat.services.UserService;

@Path("/me")
@RolesAllowed("USER")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MeRoute {
	
	private UserService	userSrv = Core.getInstance().getUserService();
	private MeetupService	meetupSrv = Core.getInstance().getMeetupService();
	private LocationService	locationSrv = Core.getInstance().getLocationService();
	
	@Path("update")
	@POST
	public Response updateProfile(Map<String, String> datas, @Context ContainerRequestContext context) {
		if (datas == null || datas.size() == 0)
			return Response.status(Status.BAD_REQUEST).build();
		
		User user = (User)context.getProperty("user");
		if (userSrv.updateUser(user, datas))
			return Response.ok("{}").build();
		else
			return Response.status(Status.NOT_MODIFIED).build();
	}
	
	@Path("profile")
	@GET
	public Response	getProfile(@Context ContainerRequestContext context) {
		User user = (User)context.getProperty("user");
		return Response.ok(userSrv.buildProfile(user, true)).build();
	}
	
	@Path("tags")
	@GET
	public Response	getTags(@Context ContainerRequestContext context) {
		return Response.ok(Core.getInstance().getDataService().getTags()).build();
	}
	
	@Path("invitations/all")
	@GET
	public Response	getInvitations(@Context ContainerRequestContext context) {
		return Response.ok(((User)context.getProperty("user")).getInvitations().stream().filter(invit -> invit.getState() != EnumState.NONE).collect(Collectors.toList())).build();
	}
	
	@Path("stats")
	@GET
	public Response	getStats(@Context ContainerRequestContext context) {
		JsonObject obj = new JsonObject();
		User user = ((User)context.getProperty("user"));
		obj.addProperty("received", user.getInvitations().stream().filter(invit -> invit.getState() == EnumState.WAITING).count());
		
		obj.addProperty("futur", user.getInvitations().stream().filter(invit -> invit.getState() == EnumState.ACCEPTED && invit.getMeetup().getState() == EnumState.WAITING).count() +
				user.getMeetups().stream().filter(meetup -> meetup.getState() == EnumState.WAITING).count());
		obj.addProperty("sent", user.getMeetups().stream().filter(meetup -> meetup.getState() == EnumState.WAITING).count());
		return Response.ok(obj).build();
	}
	

	@Path("meetups/{state}")
	@GET
	public Response	getMeetups(@PathParam("state") String state, @Context ContainerRequestContext context) {
		EnumState predicate = EnumState.valueOf(state);
		if (predicate == null || predicate == EnumState.NONE)
			return Response.status(Status.BAD_REQUEST).build();
		
		User user = (User)context.getProperty("user");
		List<UUID> meetups = new ArrayList<UUID>();
		
		user.getInvitations().stream().forEach(invit -> {
			if (invit.getState() == EnumState.ACCEPTED && invit.getType() == EnumInvitation.MEETUP) {
				Meetup meetup = meetupSrv.byId(invit.getMeetup().getId());
				if (meetup.getState() == predicate) {
					meetups.add(meetup.getId());
				}
			}
			
		});
		user.getMeetups().stream().forEach(meetup -> {
			if (meetup.getState() == predicate) {
				meetups.add(meetup.getId());
			}
		});
		return Response.ok(meetups).build();
	}
	
	@Path("invitations/{state}")
	@GET
	public Response	getInvitations(@PathParam("state") String state, @Context ContainerRequestContext context) {
		EnumState predicate = EnumState.valueOf(state);
		if (predicate == null || predicate == EnumState.NONE)
			return Response.status(Status.BAD_REQUEST).build();
		return Response.ok(((User)context.getProperty("user")).getInvitations().stream().filter(invit -> invit.getState() == predicate).collect(Collectors.toList())).build();
	}
	
	
	
	// TO-DO
	/*
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
		user.getFriends().add(new Relation(user, friend));
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
	}*/
}
