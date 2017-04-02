package dropwizard.auth;

import dropwizard.core.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by allen on 16/9/24.
 */
public interface Check {
    User check(HttpServletRequest request);
}
