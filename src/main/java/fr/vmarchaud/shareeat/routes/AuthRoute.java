package fr.vmarchaud.shareeat.routes;


import java.math.BigInteger;
import java.security.SecureRandom;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.mindrot.jbcrypt.BCrypt;

import fr.vmarchaud.shareeat.Core;
import fr.vmarchaud.shareeat.objects.User;
import fr.vmarchaud.shareeat.request.UserLoginRequest;
import fr.vmarchaud.shareeat.response.UserLoginResponse;
import fr.vmarchaud.shareeat.services.AuthService;
import fr.vmarchaud.shareeat.services.UserService;

@Path("/auth")
@PermitAll
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthRoute {

	private UserService		service = Core.getInstance().getUserService();
	private AuthService		auth	= Core.getInstance().getAuthService();
	private SecureRandom 	random = new SecureRandom();
	
	
	@Path("/login")
	@POST
	public Response userLogin(UserLoginRequest request) {
		if (!request.isValid())
			return Response.status(Status.BAD_REQUEST).build();
		User user = service.byMail(request.getUsername());
		if (user == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		if (BCrypt.checkpw(request.getPassword(), user.getPassword())) {
			String accessToken = new BigInteger(130, random).toString(32);
			auth.addLoggedUser(accessToken, user);
			return Response.ok(new UserLoginResponse(accessToken, user.getId(), System.currentTimeMillis())).build();
		}
		return Response.status(Status.FORBIDDEN).build();
	}
	
	
	@Path("/register")
	@POST
	public Response userRegister(UserLoginRequest request) {
		if (!request.isValid())
			return Response.status(Status.BAD_REQUEST).build();
		User user = service.byMail(request.getUsername());
		if (user == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		if (BCrypt.checkpw(request.getPassword(), user.getPassword())) {
			String accessToken = new BigInteger(130, random).toString(32);
			auth.addLoggedUser(accessToken, user);
			return Response.ok(new UserLoginResponse(accessToken, user.getId(), System.currentTimeMillis())).build();
		}
		return Response.status(Status.FORBIDDEN).build();
	}
	
}
