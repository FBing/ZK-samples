package com.bytebeats.zookeeper.apache;

import com.bytebeats.zookeeper.util.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-12-07 23:28
 */
public class BaseConfigDemo {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected String address;
    protected int timeout;

    protected void init(){
        try {
            Properties props = PropertyUtils.load("zookeeper.properties");
            address = props.getProperty("address");
            timeout = Integer.parseInt(props.getProperty("timeout", "5000"));

            logger.info("address:{}, timeout:{}", address, timeout);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
