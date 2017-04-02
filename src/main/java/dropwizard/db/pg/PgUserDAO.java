package dropwizard.db.pg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import dropwizard.core.User;
import dropwizard.db.UserDAO;
import org.skife.jdbi.v2.DBI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by allen on 2016/10/4.
 */
public class PgUserDAO extends UserDAO {
    private final UserAccess access;

    public PgUserDAO(DBI dbi) {
        this.access = (UserAccess) dbi.onDemand(UserAccess.class);
    }

    @Override
    public User create(User user) {
        try {
            access.create(user.getId(), user.getName(), new ObjectMapper().writeValueAsString(user.getRoles()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User delete(String var1) {
        return null;
    }

    @Override
    public List<User> list() {
        Iterator it = access.list();
        ArrayList r = Lists.newArrayList();
        Iterators.addAll(r, it);
        return r;
    }

    @Override
    public User update(User user) {
        return null;
    }
}
