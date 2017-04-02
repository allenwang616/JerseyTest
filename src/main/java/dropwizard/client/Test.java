package dropwizard.client;

import dropwizard.pojo.UserPoJo;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Created by allen on 16/8/15.
 */
public class Test {
    public static void main(String[] args) {
        //curl -H "Content-Type: application/json" -XPOST http://127.0.0.1:8080/user/create -d '{"userId":"005","userName":"allen","age":"18"}'
//        Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class);// 注册json 支持
        Client client = ClientBuilder.newClient();
//        String u = "{\"userId\":\"005\",\"userName\":\"allen\",\"age\":\"18\"}";
        UserPoJo u = new UserPoJo();
        u.setUserId("1");
        u.setUserName("wang");
        u.setAge("18");
        WebTarget target = client.target("http://127.0.0.1:8080/user/create");


        Response response = target.request().post(Entity.entity(u, MediaType.APPLICATION_JSON_TYPE));
        String output = response.readEntity(String.class);
        System.out.println(response.getStatus());
        System.out.println(output);


//        User user = target.request().post(Entity.entity(u, MediaType.APPLICATION_JSON_TYPE)).readEntity(User.class);
//        System.out.println(user.getUserId() + user.getUserName()  +  user.getAge());
    }
}
