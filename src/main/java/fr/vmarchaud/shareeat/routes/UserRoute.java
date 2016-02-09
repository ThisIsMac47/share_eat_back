package fr.vmarchaud.shareeat.routes;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import fr.vmarchaud.shareeat.Core;
import fr.vmarchaud.shareeat.objects.User;
import fr.vmarchaud.shareeat.services.UserService;


@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserRoute {
	
	private UserService	service = Core.getInstance().getUserService();
	
	@Path("find/all")
	@GET
	public Response showAll() {
		return Response.ok(service.getUsers()).build(); 
	}
	
	@Path("find/{id}")
	@GET
	public Response showUser(@PathParam("id") String id) {
		User user = service.byId(id);
		if(user != null) {
			return Response.ok(user).build();
		}
		user = service.byName(id);
		if (user != null)
			return Response.ok(user).build();
		return Response.status(Status.NOT_FOUND).build();
	}
	
}
