package com.bytebeats.zookeeper.curator.discovery.server;

import com.bytebeats.zookeeper.curator.discovery.domain.ServiceNode;
import com.bytebeats.zookeeper.curator.discovery.serializer.ServiceNodeSerializer;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-12-08 19:16
 */
public class ServiceRegistry {

    private ServiceDiscovery<ServiceNode> serviceDiscovery;

    private final CuratorFramework client;

    public ServiceRegistry(CuratorFramework client, String basePath){
        this.client = client;
        serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceNode.class)
                .client(client)
                .serializer(new JsonInstanceSerializer<>(ServiceNode.class))
                .basePath(basePath)
                .build();
    }

    public void updateService(ServiceInstance<ServiceNode> instance) throws Exception {
        serviceDiscovery.updateService(instance);
    }

    public void registerService(ServiceInstance<ServiceNode> instance) throws Exception {
        serviceDiscovery.registerService(instance);
    }

    public void unregisterService(ServiceInstance<ServiceNode> instance) throws Exception {
        serviceDiscovery.unregisterService(instance);
    }


    public void start() throws Exception {
        serviceDiscovery.start();
    }

    public void close() throws Exception {
        serviceDiscovery.close();
    }
}
