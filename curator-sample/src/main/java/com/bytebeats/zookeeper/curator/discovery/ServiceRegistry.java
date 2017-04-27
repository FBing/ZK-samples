package com.bytebeats.zookeeper.curator.discovery;

import com.bytebeats.zookeeper.curator.discovery.domain.ServerPayload;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.util.Collection;

/**
 * 服务注册
 *
 * @author Ricky Fung
 * @create 2016-12-08 19:16
 */
public class ServiceRegistry {

    private ServiceDiscovery<ServerPayload> serviceDiscovery;

    private final CuratorFramework client;

    public ServiceRegistry(CuratorFramework client, String basePath){
        this.client = client;
        serviceDiscovery = ServiceDiscoveryBuilder.builder(ServerPayload.class)
                .client(client)
                .serializer(new JsonInstanceSerializer<>(ServerPayload.class))
                .basePath(basePath)
                .build();
    }

    public void updateService(ServiceInstance<ServerPayload> instance) throws Exception {
        serviceDiscovery.updateService(instance);
    }

    public void registerService(ServiceInstance<ServerPayload> instance) throws Exception {
        serviceDiscovery.registerService(instance);
    }

    public void unregisterService(ServiceInstance<ServerPayload> instance) throws Exception {
        serviceDiscovery.unregisterService(instance);
    }

    public Collection<ServiceInstance<ServerPayload>> queryForInstances(String name) throws Exception {
        return serviceDiscovery.queryForInstances(name);
    }

    public ServiceInstance<ServerPayload> queryForInstance(String name, String id) throws Exception {
        return serviceDiscovery.queryForInstance(name, id);
    }

    public void start() throws Exception {
        serviceDiscovery.start();
    }

    public void close() throws Exception {
        serviceDiscovery.close();
    }
}
