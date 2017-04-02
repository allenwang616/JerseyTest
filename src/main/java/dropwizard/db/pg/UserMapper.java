package dropwizard.db.pg;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dropwizard.core.User;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by allen on 2016/10/4.
 */
public class UserMapper implements ResultSetMapper<User> {
    private ObjectMapper mapper = new ObjectMapper();

    public UserMapper() {
    }

    public User map(int i, ResultSet r, StatementContext ctx) throws SQLException {
        User u = new User();
        u.setId(r.getString("id"));
        u.setName(r.getString("name"));
        try {
            u.setRoles((List<String>) mapper.readValue(r.getString("roles"), new TypeReference<List<String>>() {
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return u;
    }
}
