package util;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.Provider;


import com.sun.jersey.core.util.Base64;

@Provider
public class PowerSourceSecurityFilter implements ContainerRequestFilter {
	private static final String AUTHORIZATION_HEADER_KEY="Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX="Basic ";
	private static final String SECURED_URL_PREFIX="ElectroGrid/PowerSource";
	ResponseBuilder builder = null;
	
	//if (requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX)) {
	@Override
	public ContainerRequest filter(ContainerRequest requestContext) {
		System.out.println("Helllo PowerSource filter");
		
			
			List<String> authHeader=requestContext.getRequestHeaders().get(AUTHORIZATION_HEADER_KEY);
			
			
			if (authHeader!=null && authHeader.size()>0) {
				
				String authToken=authHeader.get(0);
		
				
				authToken=authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
				String decodedString=Base64.base64Decode(authToken);
				
				StringTokenizer tokenizer= new StringTokenizer(decodedString,":");
				String username= tokenizer.nextToken();
				String pwd=tokenizer.nextToken();
				
				if ("user".equals(username) && "password".equals(pwd) ) {
					return requestContext;
				}
				
				
			}

			builder=Response.status(Response.Status.UNAUTHORIZED).entity("Access Denied ");
			
			throw new WebApplicationException(builder.build());

		
		
		
	}
}
