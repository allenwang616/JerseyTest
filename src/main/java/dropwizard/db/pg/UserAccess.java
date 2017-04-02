package dropwizard.db.pg;

import dropwizard.core.User;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Iterator;

/**
 * Created by allen on 2016/10/4.
 */
public interface UserAccess {

    @SqlUpdate("INSERT INTO users (id, name, roles) VALUES (CAST(:id AS UUID), :name, CAST(:roles AS JSONB))")
    void create(@Bind("id") String var1, @Bind("name") String var2, @Bind("roles") String var3);


    @SqlQuery("SELECT * FROM users")
    @RegisterMapper({UserMapper.class})
    Iterator<User> list();
}
