package fr.trendev.boundaries;

import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author
 */
@Path("/")
@RequestScoped
public class HelloWorldService {

    @Inject
    @ConfigProperty(name = "TEXT_MESSAGE", defaultValue = "NO_MESSAGE_SET")
    private String message;

    @Inject
    @ConfigProperty(name = "MY_POD_NAME", defaultValue = "NO_POD_NAME")
    private String podName;

    @Inject
    @ConfigProperty(name = "MY_POD_NAMESPACE", defaultValue = "NO_POD_NAMESPACE")
    private String namespace;

    @Inject
    @ConfigProperty(name = "MY_POD_IP", defaultValue = "NO_POD_IP")
    private String podIP;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response ping() {
        return Response
                .ok(Json.createObjectBuilder()
                        .add("message", message)
                        .add("pod_name",podName)
                        .add("namespace",namespace)
                        .add("pod_IP",podIP)
                        .add("timestamp", new Date().getTime())
                        .build()
                )
                .build();
    }
}
