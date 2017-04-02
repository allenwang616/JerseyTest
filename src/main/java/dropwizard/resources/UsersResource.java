package dropwizard.resources;

import dropwizard.core.User;
import dropwizard.db.UserDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by allen on 2016/10/4.
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UsersResource {
    private final UserDAO dao;

    public UsersResource(UserDAO dao) {
        this.dao = dao;
    }

    @GET
    public List<User> getUsers() {
        return dao.list();
    }

    @POST
    public Response create(User user) {
        user = dao.create(user);
        return Response.status(Response.Status.CREATED).entity(user).build();
    }
}
