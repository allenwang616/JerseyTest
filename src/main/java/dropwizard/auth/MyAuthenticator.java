package dropwizard.auth;

import dropwizard.core.User;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

//认证
public class MyAuthenticator implements Authenticator<BasicCredentials, User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyAuthenticator.class);
    /**
     * Valid users with mapping user -> roles
     */
    private static final ImmutableMap<String, ImmutableSet<?>> VALID_USERS = ImmutableMap.of(
            "guest", ImmutableSet.of(),
            "good-guy", ImmutableSet.of("BASIC_GUY"),
            "chief-wizard", ImmutableSet.of("ADMIN", "BASIC_GUY")
    );

    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        if (VALID_USERS.containsKey(credentials.getUsername()) && "secret".equals(credentials.getPassword())) {
            LOGGER.info(credentials.toString());
            return Optional.of(new User(credentials.getUsername(), (List<String>) VALID_USERS.get(credentials.getUsername())));
        }
        return Optional.empty();
    }
}
