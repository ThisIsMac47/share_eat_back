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
import fr.vmarchaud.shareeat.request.UserProfileUpdateRequest;
import fr.vmarchaud.shareeat.services.AuthService;
import fr.vmarchaud.shareeat.services.UserService;

@Path("/me")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MeRoute {
	
	private UserService	userSrv = Core.getInstance().getUserService();
	private AuthService authSrv = Core.getInstance().getAuthService();
	
	@Path("update")
	@POST
	public Response updateProfile(UserProfileUpdateRequest request) {
		if (!request.isValid())
			return Response.status(Status.BAD_REQUEST).build();
		if (authSrv.verify(request.getId(), request.getAccessToken()) != null) 
			return Response.status(Status.FORBIDDEN).build();
		
		User user = userSrv.byId(request.getId());
		if (userSrv.updateUser(user, request.datas))
			return Response.ok().build();
		else
			return Response.status(Status.NOT_MODIFIED).build();
	}

}
