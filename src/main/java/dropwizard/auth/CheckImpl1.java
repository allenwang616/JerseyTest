package dropwizard.auth;

import dropwizard.core.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;

/**
 * Created by allen on 16/9/24.
 */
public class CheckImpl1 implements Check {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckImpl1.class);

    @Override
    public User check(HttpServletRequest request) {
        LOGGER.info(request.getHeader(HttpHeaders.AUTHORIZATION));
        return new User(request.getHeader(HttpHeaders.AUTHORIZATION));
    }
}
