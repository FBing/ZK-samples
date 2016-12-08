package com.bytebeats.zookeeper.curator.discovery;

import com.bytebeats.zookeeper.curator.CuratorUtils;
import com.bytebeats.zookeeper.curator.discovery.domain.ServiceNode;
import com.bytebeats.zookeeper.curator.discovery.server.ServiceRegistry;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.UriSpec;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-12-08 19:24
 */
public class ServerApp {

    public static void main(String[] args) {

        CuratorFramework client = null;
        ServiceRegistry serviceRegistry = null;
        try{
            client = CuratorUtils.getCuratorClient();
            client.start();

            serviceRegistry = new ServiceRegistry(client,"services");
            serviceRegistry.start();

            ServiceInstance<ServiceNode> host1 = ServiceInstance.<ServiceNode>builder()
                    .name("service1")
                    .port(21999)
                    .address("192.168.1.100")   //address不写的话，会取本地ip
                    .payload(new ServiceNode("192.168.1.100", 21999, "host1", 5))
                    .uriSpec(new UriSpec("{scheme}://{address}:{port}"))
                    .build();

            serviceRegistry.registerService(host1);

            System.out.println("register service success...");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(serviceRegistry!=null){
                try {
                    serviceRegistry.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            client.close();
        }

    }
}
