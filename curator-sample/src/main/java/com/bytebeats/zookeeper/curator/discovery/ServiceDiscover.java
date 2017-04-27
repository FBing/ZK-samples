package com.bytebeats.zookeeper.curator.discovery;

import com.bytebeats.zookeeper.curator.discovery.domain.ServerPayload;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceProvider;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.apache.curator.x.discovery.strategies.RandomStrategy;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务发现
 *
 * @author Ricky Fung
 * @create 2016-12-08 20:04
 */
public class ServiceDiscover {
    private ServiceDiscovery<ServerPayload> serviceDiscovery;
    private final ConcurrentHashMap<String, ServiceProvider<ServerPayload>> serviceProviderMap = new ConcurrentHashMap<>();

    public ServiceDiscover(CuratorFramework client , String basePath){
        serviceDiscovery = ServiceDiscoveryBuilder.builder(ServerPayload.class)
                .client(client)
                .basePath(basePath)
                .serializer(new JsonInstanceSerializer<>(ServerPayload.class))
                .build();
    }

    /**
     * Note: When using Curator 2.x (Zookeeper 3.4.x) it's essential that service provider objects are cached by your application and reused.
     * Since the internal NamespaceWatcher objects added by the service provider cannot be removed in Zookeeper 3.4.x,
     * creating a fresh service provider for each call to the same service will eventually exhaust the memory of the JVM.
     */
    public ServiceInstance<ServerPayload> getServiceProvider(String serviceName) throws Exception {
        ServiceProvider<ServerPayload> provider = serviceProviderMap.get(serviceName);
        if (provider == null) {
            provider = serviceDiscovery.serviceProviderBuilder().
                    serviceName(serviceName).
                    providerStrategy(new RandomStrategy<ServerPayload>())
                    .build();

            ServiceProvider<ServerPayload> oldProvider = serviceProviderMap.putIfAbsent(serviceName, provider);
            if (oldProvider != null) {
                provider = oldProvider;
            }else {
                provider.start();
            }
        }

        return provider.getInstance();
    }

    public void start() throws Exception {
        serviceDiscovery.start();
    }

    public void close() throws IOException {

        for (Map.Entry<String, ServiceProvider<ServerPayload>> me : serviceProviderMap.entrySet()){
            try{
                me.getValue().close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        serviceDiscovery.close();
    }
}
