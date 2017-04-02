import hk2.impl.MyServiceImpl;
import hk2.interfaces.MyService;
import org.glassfish.hk2.api.*;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Singleton;


/**
 * Created by allen on 16/8/16.
 */
public class MyserviceTest {
    private ServiceLocator locator;
    private ServiceLocatorFactory factory;

    @Before
    public void doBefore() throws Exception {
        factory = ServiceLocatorFactory.getInstance();
        locator = factory.create("CustomResolverTest");
        DynamicConfigurationService dcs = locator.getService(DynamicConfigurationService.class);
        DynamicConfiguration config = dcs.createDynamicConfiguration();
        config.bind(createMyserviceDescriptor());
        config.commit();

    }

    public Descriptor createMyserviceDescriptor() {
        return BuilderHelper.link(MyServiceImpl.class).
                to(MyService.class).
                in(Singleton.class.getName()).
                build();
    }

    @Test
    public void test1() {
        MyService myService = locator.getService(MyService.class);
        myService.helloHK2();
    }

}
