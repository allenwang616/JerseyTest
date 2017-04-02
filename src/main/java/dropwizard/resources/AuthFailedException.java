package dropwizard.resources;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by allen on 16/9/28.
 */
public class AuthFailedException extends WebApplicationException {
    public AuthFailedException(Response.Status status, Object entity) {
        super(Response.status(status).entity(entity).type(MediaType.APPLICATION_JSON).build());
    }
}
