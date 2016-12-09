package com.bytebeats.zookeeper.curator.discovery.client;

import com.bytebeats.zookeeper.curator.discovery.domain.ServiceNode;
import com.google.common.collect.Maps;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceProvider;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.apache.curator.x.discovery.strategies.RandomStrategy;

import java.io.IOException;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-12-08 20:04
 */
public class ServiceDiscover {
    private ServiceDiscovery<ServiceNode> serviceDiscovery;
    private Map<String, ServiceProvider<ServiceNode>> providers = Maps.newHashMap();
    private final Object lock = new Object();

    public ServiceDiscover(CuratorFramework client , String basePath){
        serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceNode.class)
                .client(client)
                .basePath(basePath)
                .serializer(new JsonInstanceSerializer<>(ServiceNode.class))
                .build();
    }

    /**
     * Note: When using Curator 2.x (Zookeeper 3.4.x) it's essential that service provider objects are cached by your application and reused.
     * Since the internal NamespaceWatcher objects added by the service provider cannot be removed in Zookeeper 3.4.x,
     * creating a fresh service provider for each call to the same service will eventually exhaust the memory of the JVM.
     */
    public ServiceInstance<ServiceNode> getServiceInstance(String serviceName) throws Exception {
        ServiceProvider<ServiceNode> provider = providers.get(serviceName);
        if (provider == null) {
            synchronized (lock) {
                provider = providers.get(serviceName);
                if (provider == null) {
                    provider = serviceDiscovery.serviceProviderBuilder().
                            serviceName(serviceName).
                            providerStrategy(new RandomStrategy<ServiceNode>())
                            .build();
                    provider.start();
                    providers.put(serviceName, provider);
                }
            }
        }

        return provider.getInstance();
    }

    public void start() throws Exception {
        serviceDiscovery.start();
    }

    public void close() throws IOException {

        for (Map.Entry<String, ServiceProvider<ServiceNode>> me : providers.entrySet()){
            try{
                me.getValue().close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        serviceDiscovery.close();
    }
}
