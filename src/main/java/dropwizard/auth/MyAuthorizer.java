package dropwizard.auth;

import dropwizard.core.User;
import io.dropwizard.auth.Authorizer;

//授权
public class MyAuthorizer implements Authorizer<User> {

    @Override
    public boolean authorize(User user, String role) {
        return user.getRoles() != null && user.getRoles().contains(role);
    }
}
