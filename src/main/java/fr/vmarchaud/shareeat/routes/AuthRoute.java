package fr.vmarchaud.shareeat.routes;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import fr.vmarchaud.shareeat.Core;
import fr.vmarchaud.shareeat.objects.User;
import fr.vmarchaud.shareeat.request.UserLoginRequest;
import fr.vmarchaud.shareeat.response.UserLoginResponse;
import fr.vmarchaud.shareeat.services.AuthService;
import fr.vmarchaud.shareeat.services.UserService;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthRoute {

	private UserService	service = Core.getInstance().getUserService();
	private AuthService	auth	= Core.getInstance().getAuthService();
	
	@Path("/login")
	@POST
	public Response userLogin(UserLoginRequest request) {
		if (!request.isValid())
			return Response.status(Status.BAD_REQUEST).build();
		return Response.status(Status.NOT_FOUND).build();
	}
	
}
