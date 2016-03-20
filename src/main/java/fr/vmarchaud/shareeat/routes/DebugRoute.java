package fr.vmarchaud.shareeat.routes;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import fr.vmarchaud.shareeat.Core;
import fr.vmarchaud.shareeat.services.DataService;


@Path("/debug")
@PermitAll
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DebugRoute {
	
	private DataService	service = Core.getInstance().getDataService();
	
	@Path("users/all")
	@GET
	public Response showAll() {
		return Response.ok(service.getUsers()).build(); 
	}
	
	@Path("location/all")
	@GET
	public Response showAllLoc() {
		return Response.ok(service.getLocations()).build(); 
	}
	
	@Path("meetup/all")
	@GET
	public Response showAllMeetup() {
		return Response.ok(service.getMeetups()).build(); 
	}
	
	@Path("invitations/all")
	@GET
	public Response showAllInvit() {
		return Response.ok(service.getInvitations()).build(); 
	}
	
	@Path("payements/all")
	@GET
	public Response showPayements() {
		return Response.ok(service.getPayements()).build(); 
	}
}
