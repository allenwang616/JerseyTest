package dropwizard.core;

import java.security.Principal;
import java.util.List;
import java.util.Set;

/**
 * Created by allen on 16/8/17.
 */
public class User implements Principal {
    private String name;

    private String id;

    private List<String> roles;

    public User() {

    }

    public User(String name) {
        this(name, null, null);
    }

    public User(String name, List<String> roles) {
        this(name, null, roles);
    }

    public User(String name, String id, List<String> roles) {
        this.name = name;
        this.id = id;
        this.roles = roles;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return this.id;
    }

    public List<String> getRoles() {
        return roles;
    }
}