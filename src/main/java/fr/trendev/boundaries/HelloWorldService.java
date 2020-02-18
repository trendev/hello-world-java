package fr.trendev.boundaries;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author 
 */
@Path("hello-world")
public class HelloWorldService {
    
    @GET
    public Response ping(){
        return Response
                .ok("ping")
                .build();
    }
}
