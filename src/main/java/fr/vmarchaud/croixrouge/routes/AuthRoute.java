package fr.vmarchaud.croixrouge.routes;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import fr.vmarchaud.croixrouge.Core;
import fr.vmarchaud.croixrouge.objects.User;
import fr.vmarchaud.croixrouge.request.UserLoginRequest;
import fr.vmarchaud.croixrouge.response.UserLoginResponse;
import fr.vmarchaud.croixrouge.services.UserService;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthRoute {

	private UserService	service = Core.getInstance().getUserService();
	private SecureRandom random = new SecureRandom();
	
	
	@Path("/login")
	@POST
	public Response userLogin(UserLoginRequest request) {
		if (!request.isValid())
			return Response.status(Status.BAD_REQUEST).build();
		User user = service.byName(request.getUsername());
		if (user != null) {
			if (user.getPassword().equals(request.getPassword())) {
				String accessToken = new BigInteger(130, random).toString(32);
				user.setAccessToken(accessToken);
				return Response.ok(new UserLoginResponse(accessToken, user.getStatus(), System.currentTimeMillis())).build();
			}
			else
				return Response.status(Status.FORBIDDEN).build();
		}
		else
			return Response.status(Status.NOT_FOUND).build();
	}
	
}
