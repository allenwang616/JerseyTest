package dropwizard.db;

import dropwizard.core.User;

import java.util.List;

/**
 * Created by allen on 2016/10/4.
 */
public abstract class UserDAO {
    public UserDAO() {
    }

    public abstract User create(User user);

    public abstract User delete(String var1);

    public abstract List<User> list();

    public abstract User update(User user);
}
