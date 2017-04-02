package dropwizard.factory;

import dropwizard.annotation.UserParam;
import dropwizard.core.User;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.internal.inject.AbstractContainerRequestValueFactory;
import org.glassfish.jersey.server.internal.inject.AbstractValueFactoryProvider;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractorProvider;
import org.glassfish.jersey.server.internal.inject.ParamInjectionResolver;
import org.glassfish.jersey.server.model.Parameter;
import org.glassfish.jersey.server.spi.internal.ValueFactoryProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

/**
 * Created by allen on 16/9/12.
 */
@Singleton
public class UserParamValueFactoryProvider extends AbstractValueFactoryProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserParamValueFactoryProvider.class);


    @Inject
    public UserParamValueFactoryProvider(MultivaluedParameterExtractorProvider mpep, ServiceLocator injector) {
        super(mpep, injector, Parameter.Source.UNKNOWN);
    }

    @Override
    public Factory<?> createValueFactory(Parameter parameter) {
        Class<?> classType = parameter.getRawType();

        if (classType == null || (!classType.equals(User.class))) {
            LOGGER.warn("IdentityParam annotation was not placed on correct object type; Injection might not work correctly!");
            return null;
        }

        return new UserParamValueFactory();
    }

    public static final class UserParamValueFactory extends AbstractContainerRequestValueFactory<User> {

        @Context
        private ResourceContext context;

        public User provide() {
            final HttpServletRequest request = context.getResource(HttpServletRequest.class);
            final User user = new User(request.getHeader(HttpHeaders.AUTHORIZATION));
            return user;
        }
    }

    @Singleton
    public static final class InjectionResolver extends ParamInjectionResolver<UserParam> {

        public InjectionResolver() {
            super(UserParamValueFactoryProvider.class);
        }
    }

}
