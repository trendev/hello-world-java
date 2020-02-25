package fr.trendev.boundaries;

import fr.trendev.controllers.RecordsManager;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
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
@Stateless
public class HelloWorldService {

    private static final Logger LOG = Logger.getLogger(HelloWorldService.class.getName());

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

    @Inject
    private RecordsManager recordsManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response message() {

        if (recordsManager.isNull()) {
            LOG.log(Level.SEVERE, "Records cannot be null");
            return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity(Json.createObjectBuilder()
                            .add("status", "error")
                            .add("message", "Records cannot be null")
                            .add("pod_name", podName)
                            .add("namespace", namespace)
                            .add("pod_IP", podIP)
                            .add("timestamp", new Date().getTime())
                            .build()
                    )
                    .build();
        }

        List<String> records = this.recordsManager.add(podName);

        JsonObject jo = Json.createObjectBuilder()
                .add("message", message)
                .add("records", Json.createArrayBuilder(records).build())
                .add("records_length", records.size())
                .add("pod_name", podName)
                .add("namespace", namespace)
                .add("pod_IP", podIP)
                .add("timestamp", new Date().getTime())
                .build();

        LOG.info(jo.toString());

        return Response.ok(jo).build();
    }
}
