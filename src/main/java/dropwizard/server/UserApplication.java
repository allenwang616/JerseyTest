package dropwizard.server;

import dropwizard.annotation.UserParam;
import dropwizard.auth.Check;
import dropwizard.auth.CheckImpl1;
import dropwizard.auth.MyAuthenticator;
import dropwizard.auth.MyAuthorizer;
import dropwizard.configuration.UserConfiguration;
import dropwizard.core.User;
import dropwizard.db.UserDAO;
import dropwizard.db.pg.PgUserDAO;
import dropwizard.factory.UserParamValueFactoryProvider;
import dropwizard.factory.UserParamValueFactoryProvider2;
import dropwizard.resources.UsersResource;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import dropwizard.resources.UserResource;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.jersey.server.spi.internal.ValueFactoryProvider;
import org.skife.jdbi.v2.DBI;

import javax.inject.Singleton;

/**
 * Created by allen on 16/8/15.
 */
public class UserApplication extends Application<UserConfiguration> {

    public static void main(String[] args) throws Exception {
        new UserApplication().run(args);
    }

    @Override
    public String getName() {
        return "myapp";//在项目启动之后，负责URL跳转
    }

    @Override
    public void initialize(Bootstrap<UserConfiguration> bootstrap) {

    }

    @Override
    public void run(UserConfiguration configuration,
                    Environment environment) {
        final UserResource userResource = new UserResource(configuration.getName(), configuration.getAge());
        environment.jersey().register(userResource);

        environment.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
                .setAuthenticator(new MyAuthenticator())
                .setAuthorizer(new MyAuthorizer())
                .setRealm("SUPER SECRET STUFF")
                .buildAuthFilter()));
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new InjectionBinder());
//        environment.jersey().register(new InjectionBinder2(new CheckImpl1()));
        DBI jdbi = (new DBIFactory()).build(environment, configuration.getDataSourceFactory(), "postgresql");
        UserDAO userDAO = new PgUserDAO(jdbi);
        final UsersResource usersResource = new UsersResource(userDAO);
        environment.jersey().register(usersResource);


    }

    public static class InjectionBinder extends AbstractBinder {

        @Override
        protected void configure() {
            bind(UserParamValueFactoryProvider.class).to(ValueFactoryProvider.class).in(Singleton.class);
            bind(UserParamValueFactoryProvider.InjectionResolver.class).to(new TypeLiteral<InjectionResolver<UserParam>>() {
            }).in(Singleton.class);

        }
    }

    public static class InjectionBinder2 extends AbstractBinder {

        private final Check check;

        InjectionBinder2(Check check) {
            this.check = check;
        }

        @Override
        protected void configure() {
            this.bind(this.check).to(new TypeLiteral<Check>() {
            });
            this.bind(UserParamValueFactoryProvider2.class).to(ValueFactoryProvider.class).in(Singleton.class);
            this.bind(UserParamValueFactoryProvider2.InjectionResolver.class).to(new TypeLiteral<InjectionResolver<UserParam>>() {
            }).in(Singleton.class);

        }
    }
}
