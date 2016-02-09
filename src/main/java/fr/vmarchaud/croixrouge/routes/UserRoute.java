package fr.vmarchaud.croixrouge.routes;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import fr.vmarchaud.croixrouge.Core;
import fr.vmarchaud.croixrouge.objects.User;
import fr.vmarchaud.croixrouge.request.UserLoginRequest;
import fr.vmarchaud.croixrouge.services.UserService;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserRoute {
	
	private UserService	service = Core.getInstance().getUserService();
	
	@Path("/login")
	@POST
	public Response userLogin(UserLoginRequest request) {
		if (!request.isValid())
			return Response.status(Status.BAD_REQUEST).build();
		User user = service.byName(request.getUsername());
		if (user != null) {
			if (user.getPassword().equals(request.getPassword())) 
				return Response.ok().build();
			else
				return Response.status(Status.FORBIDDEN).build();
		}
		else
			return Response.status(Status.NOT_FOUND).build();
	}
	
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
	
/*
	
	get("/users/:name/degrees", (request, response) -> {
		for (User user : users) {
			if (user.getName().equals(request.params(":name")))
				return gson.toJson(user.getDegrees());
		}
		return gson.toJson(new ReturnMessage(false, "User not found"));
	});*/
}
