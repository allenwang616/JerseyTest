import com.google.common.io.BaseEncoding;
import dropwizard.core.User;
import io.dropwizard.jersey.errors.ErrorMessage;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import dropwizard.pojo.UserPoJo;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by allen on 16/8/15.
 */
public class UserTest {
    private Client client;

    @Before
    public void setUp() throws Exception {
        final ClientConfig cc = new ClientConfig();
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder()
                .nonPreemptive()
//                .credentials("chief-wizard", "secret")
                .build();
        cc.register(feature);
        client = ClientBuilder.newClient(cc);

//        client = ClientBuilder.newClient();

    }

    @After
    public void tearDown() throws Exception {
        client.close();
    }

    @Test
    public void test1() throws Exception {
        UserPoJo u = new UserPoJo();
        u.setUserId("1");
        u.setUserName("wang");
        u.setAge("18");
        WebTarget target = client.target("http://127.0.0.1:8080/user/create");
        Response response = target.request().post(Entity.entity(u, MediaType.APPLICATION_JSON_TYPE));
        String output = response.readEntity(String.class);
        System.out.println(response.getStatus());
        System.out.println(output);
    }

    @Test
    public void test2() throws Exception {
        String u = "{\"userId\":\"005\",\"userName\":\"allen\",\"age\":\"18\"}";
        WebTarget target = client.target("http://127.0.0.1:8080/user/create");
        Response response = target.request().post(Entity.entity(u, MediaType.APPLICATION_JSON_TYPE));
        String output = response.readEntity(String.class);
        System.out.println(response.getStatus());
        System.out.println(output);
    }

    @Test
    public void test3() {
        //curl 命令测试
        // curl -H "Content-Type: application/json" -XPOST http://127.0.0.1:8080/user/create -d '{"userId":"005","userName":"allen","age":"18"}'
    }

    @Test
    public void test4() throws Exception {
        String u = "{\"userId\":\"005\",\"userName\":\"allen\",\"age\":\"18\"}";
        WebTarget target = client.target("http://127.0.0.1:8080/user/create");
        Response response = target.request().post(Entity.entity(u, MediaType.APPLICATION_JSON_TYPE));
        UserPoJo user = response.readEntity(UserPoJo.class);
        System.out.println(response.getStatus());
        System.out.println(user.getUserId() + "  " + user.getUserName() + " " + user.getAge());
    }

    @Test
    public void test5() throws Exception {
        List<UserPoJo> list = new ArrayList<UserPoJo>();
        UserPoJo u1 = new UserPoJo();
        u1.setUserId("1");
        u1.setUserName("wang");
        u1.setAge("18");
        UserPoJo u2 = new UserPoJo();
        u2.setUserId("2");
        u2.setUserName("zhang");
        u2.setAge("19");
        list.add(u1);
        list.add(u2);

        WebTarget target = client.target("http://127.0.0.1:8080/user/create_all");
        System.out.println(Entity.json(list).getEntity().toString());
        Response response = target.request().post(Entity.json(list));
        System.out.println(response.getStatus());
        String str = response.readEntity(String.class);
        System.out.println(str);
    }

    @Test
    public void test6() throws Exception {
        WebTarget target = client.target("http://127.0.0.1:8080/user/get");
        Response response = target.request()
//                .header(HttpHeaders.AUTHORIZATION, "Basic Y2hpZWYtd2l6YXJkOnNlY3JldA==")
                .get();
        UserPoJo user = response.readEntity(UserPoJo.class);
        //String decoded = new String(BaseEncoding.base64().decode("Y2hpZWYtd2l6YXJkOnNlY3JldA=="), StandardCharsets.UTF_8);
        //System.out.println(decoded);
        System.out.println(response.getStatus());
        System.out.println(user.getUserId() + "  " + user.getUserName() + " " + user.getAge());
    }

    @Test
    public void test7() throws Exception {
        WebTarget target = client.target("http://127.0.0.1:8080/user/get_2");
        Response response = target.request()
                .header(HttpHeaders.AUTHORIZATION, "Basic Y2hpZWYtd2l6YXJkOnNlY3JldA==")
                .get();
        System.out.println(response.getStatus());
        if (response.getStatus() != 200) {
            System.out.println(response.readEntity(ErrorMessage.class).toString());
        } else {
            UserPoJo user = response.readEntity(UserPoJo.class);
            System.out.println(user.getUserId() + "  " + user.getUserName() + " " + user.getAge());
        }
    }

    @Test
    public void test8() throws Exception {
        WebTarget target = client.target("http://127.0.0.1:8080/user/get_1");
        Response response = target.request()
                .header(HttpHeaders.AUTHORIZATION, "Basic Y2hpZWYtd2l6YXJkOnNlY3JldA==")
                .get();
        UserPoJo user = response.readEntity(UserPoJo.class);
        System.out.println(response.getStatus());
        System.out.println(user.getUserId() + "  " + user.getUserName() + " " + user.getAge());
    }

    @Test
    public void test9() throws Exception {
        WebTarget target = client.target("http://127.0.0.1:8080/users");
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName("wang");
        List<String> roles = new ArrayList<String>();
        roles.add("admin");
        roles.add("normal");
        user.setRoles(roles);
        Response response = target.request().post(Entity.json(user));
        System.out.println(response.readEntity(String.class));
    }

    @Test
    public void test10() throws Exception {
        WebTarget target = client.target("http://127.0.0.1:8080/users");
        Response response = target.request().get();
        System.out.println(response.readEntity(String.class));
    }

}
