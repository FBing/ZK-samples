package com.bytebeats.zookeeper.curator.discovery;

import com.bytebeats.zookeeper.curator.CuratorUtils;
import com.bytebeats.zookeeper.curator.discovery.client.ServiceDiscover;
import com.bytebeats.zookeeper.curator.discovery.domain.ServiceNode;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceInstance;

import java.io.IOException;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-12-08 20:13
 */
public class ClientApp {

    public static void main(String[] args) {

        CuratorFramework client = null;
        ServiceDiscover serviceDiscover = null;
        try{
            client = CuratorUtils.getCuratorClient();
            client.start();

            serviceDiscover = new ServiceDiscover(client,"services");
            serviceDiscover.start();

            ServiceInstance<ServiceNode> instance = serviceDiscover.getServiceInstance("service1");

            System.out.println("address:"+instance.getAddress()+", port:"+instance.getPort());
            System.out.println(instance.getPayload());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(serviceDiscover!=null){
                try {
                    serviceDiscover.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            client.close();
        }
    }
}
