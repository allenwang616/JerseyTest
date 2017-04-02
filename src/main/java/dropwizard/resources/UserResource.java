package dropwizard.resources;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import dropwizard.annotation.UserParam;
import dropwizard.core.User;
import io.dropwizard.auth.Auth;
import io.dropwizard.jersey.errors.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dropwizard.pojo.UserPoJo;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by allen on 16/8/15.
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
//@RolesAllowed({"BASIC_GUY", "ADMIN"})
public class UserResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);
    private String name;
    private String age;

    public UserResource(String name, String age) {
        this.name = name;
        this.age = age;
    }

    @GET
    @Path("/get")
    @RolesAllowed("ADMIN")
    public UserPoJo getUserJson(@Auth User user) {
        LOGGER.info(user.getName() + "    " + user.getRoles() + " " + user.getId());
        UserPoJo userPoJo = new UserPoJo();
        userPoJo.setUserId("005");
        userPoJo.setUserName(this.name);
        userPoJo.setAge(this.age);
        if (userPoJo.getUserId().equals("005")) {
            throw new AuthFailedException(Response.Status.UNAUTHORIZED, new ErrorMessage(Response.Status.UNAUTHORIZED.getStatusCode(), "Invalid credentials"));
//            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").type(MediaType.TEXT_PLAIN_TYPE).build();
        }
        return userPoJo;
    }

    @GET
    @Path("/get_1")
    @RolesAllowed("ADMIN")
    public Response getUserJson_1(@Auth User user) {
        LOGGER.info(user.getName() + "    " + user.getRoles() + " " + user.getId());
        UserPoJo userPoJo = new UserPoJo();
        userPoJo.setUserId("005");
        userPoJo.setUserName(this.name);
        userPoJo.setAge(this.age);
        return Response.ok(userPoJo).build();
    }

    @GET
    @Path("/get_2")
    public Response getUserJson_2(@UserParam User user) {
        LOGGER.info(user.getName() + "    " + user.getRoles() + " " + user.getId());
        UserPoJo userPoJo = new UserPoJo();
        userPoJo.setUserId("005");
        userPoJo.setUserName(this.name);
        userPoJo.setAge(this.age);
        if (userPoJo.getUserId().equals("005")) {
            throw new AuthFailedException(Response.Status.UNAUTHORIZED, new ErrorMessage(Response.Status.UNAUTHORIZED.getStatusCode(), "Invalid credentials"));
//            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").type(MediaType.TEXT_PLAIN_TYPE).build();
        }
        return Response.ok(userPoJo).build();
    }

    @POST
    @Path("/create")
    public UserPoJo createUserJson(UserPoJo userPoJo, @Context HttpServletRequest request) throws Exception {
        LOGGER.info(userPoJo.getUserId() + "  " + userPoJo.getUserName() + "    " + userPoJo.getAge());
        String s = CharStreams.toString(new InputStreamReader(request.getInputStream(), Charsets.UTF_8));
        LOGGER.info(s);

        return userPoJo;
    }

    @POST
    @Path("/create_all")
    public String createUserJson(List<UserPoJo> list) throws Exception {
        for (UserPoJo user : list) {
            LOGGER.info(user.getUserId() + "  " + user.getUserName() + "    " + user.getAge());
        }

        return "ok";
    }

    @GET
    @Path("/get_all")
    @RolesAllowed("ADMIN")
    public Response getUserJson() {
        List<UserPoJo> list = new ArrayList<UserPoJo>();
        UserPoJo userPoJo = new UserPoJo();
        userPoJo.setUserId("005");
        userPoJo.setUserName("wang");
        userPoJo.setAge("10");
        List<UserPoJo.Attr> attr = new ArrayList<UserPoJo.Attr>();
        attr.add(new UserPoJo.Attr("key1","value1"));
        attr.add(new UserPoJo.Attr("key2","value2"));
        userPoJo.setAttr(attr);
        list.add(userPoJo);
        userPoJo = new UserPoJo();
        userPoJo.setUserId("006");
        userPoJo.setUserName("zhang");
        userPoJo.setAge("11");
        list.add(userPoJo);
        return Response.ok(list).build();
    }

    @GET
    @Path("{param1}")
    public Response test1(@PathParam("param1") String param1, @QueryParam("start") String start, @QueryParam("size") String size) {
        System.out.println(param1);
        System.out.println(start);
        System.out.println(size);
        return Response.ok().build();
    }
}
