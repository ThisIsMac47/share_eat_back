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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
 
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
            
            System.out.println(Core.getInstance().gson.toJson(requestContext.getHeaders()));
            
            if(authorization == null || authorization.isEmpty())  {
            	System.out.println("yolo");
                requestContext.abortWith(ACCESS_DENIED);
                return;
            }
            String decodedString = new String(authorization.get(0));
  
            // If annotation has been set
            if(method.isAnnotationPresent(RolesAllowed.class) || cls.isAnnotationPresent(RolesAllowed.class))
            {
            	// Get which role are allowed
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                if (rolesAnnotation == null)
                	rolesAnnotation = cls.getAnnotation(RolesAllowed.class);
                EnumRole roleNeeded = EnumRole.fromName(rolesAnnotation.value()[0]);
                
                // Get all token
                StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
                if (tokenizer.countTokens() != 2) {
                	System.out.println("lol3");
                	System.out.println(decodedString);
                	requestContext.abortWith(ACCESS_DENIED);
                    return;
                }
                
                String id = tokenizer.nextToken();
                String accessToken = tokenizer.nextToken();
                
                // Invalid auth token
                if (!Utils.isUUID(id) || accessToken.isEmpty()) {
                	System.out.println("lol2");
                	requestContext.abortWith(ACCESS_DENIED);
                    return;
                }
                
                // Get user from his token and id
                User user = Core.getInstance().getAuthService().verify(id, accessToken);
                if (user != null) {
                	// Check if the user have enought right to go here
                	if (EnumRole.compareWeight(roleNeeded, user.getRole()) > 0) {
                    	System.out.println("lol1");
                		requestContext.abortWith(ACCESS_DENIED);
                        return;
                	}
                	// User is allowed here so put it in context
                	requestContext.setProperty("user", user);
                	return ;
                }
                // User not found, get out
                else {
                	System.out.println("yolo4");
                	requestContext.abortWith(ACCESS_DENIED);
                    return;
                }
            }
            // If nothing has been set, better block it for security reason
            else {
            	requestContext.abortWith(Response.status(Status.NOT_IMPLEMENTED).build());
            }
        }
    }
    
}