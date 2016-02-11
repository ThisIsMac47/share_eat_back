package fr.vmarchaud.shareeat.utils;
 
import java.lang.reflect.Method;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
 
import org.glassfish.jersey.internal.util.Base64;

import fr.vmarchaud.shareeat.Core;
import fr.vmarchaud.shareeat.enums.EnumRole;
import fr.vmarchaud.shareeat.objects.User;

@Provider
public class AuthenticationFilter implements ContainerRequestFilter
{
     
    @Context
    private ResourceInfo resourceInfo;
     
    private static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED).build();
      
    @Override
    public void filter(ContainerRequestContext requestContext)
    {
    	// Wrapper around the class and method
        Method 		method = resourceInfo.getResourceMethod();
        Class<?> 	cls = resourceInfo.getResourceClass();
        
        if(!method.isAnnotationPresent(PermitAll.class) && !cls.isAnnotationPresent(PermitAll.class)) {
            // Get data from header
            final List<String> authorization = requestContext.getHeaders().get("Authorization");
            if(authorization == null || authorization.isEmpty())  {
                requestContext.abortWith(ACCESS_DENIED);
                return;
            }
            String decodedString = new String(Base64.decode(authorization.get(0).getBytes()));;
  
            //Verify if an user can access to a method
            if(method.isAnnotationPresent(RolesAllowed.class) || cls.isAnnotationPresent(PermitAll.class))
            {
            	// Get which role are allowed
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                if (rolesAnnotation == null)
                	rolesAnnotation = cls.getAnnotation(RolesAllowed.class);
                EnumRole roleNeeded = EnumRole.fromName(rolesAnnotation.value()[0]);
                
                StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
                String id = tokenizer.nextToken();
                String accessToken = tokenizer.nextToken();
                if (!Utils.isUUID(id) || accessToken.length() > 0) {
                	requestContext.abortWith(ACCESS_DENIED);
                    return;
                }
                
                User user = Core.getInstance().getAuthService().verify(id, accessToken);
                if (user == null) {
                	if (roleNeeded.compareTo(user.getRole()) > 0) {
                		requestContext.abortWith(ACCESS_DENIED);
                        return;
                	}
                	return ;
                }
                else {
                	requestContext.abortWith(ACCESS_DENIED);
                    return;
                }
            }
        }
    }
    
}