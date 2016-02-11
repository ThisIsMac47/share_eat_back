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
import fr.vmarchaud.shareeat.services.AuthService;
import fr.vmarchaud.shareeat.services.UserService;

@Path("/profile")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfileRoute {
	
	private UserService	userSrv = Core.getInstance().getUserService();
	private AuthService authSrv = Core.getInstance().getAuthService();
	
	@Path("show/{id}")
	@GET
	public Response show(@PathParam("id") String id) {
		User user = userSrv.byId(id);
		if (user != null) {
			//if (user.getFriends())
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
}
