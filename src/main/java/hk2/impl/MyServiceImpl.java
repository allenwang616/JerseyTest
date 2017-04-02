package hk2.impl;

import hk2.interfaces.MyService;
import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

/**
 * Created by allen on 16/8/16.
 */
@Service @Singleton
public class MyServiceImpl implements MyService {
    public static final Logger LOGGER = LoggerFactory.getLogger(MyServiceImpl.class);

    @Override
    public void helloHK2() {
        LOGGER.info("hello hk2");
    }
}
