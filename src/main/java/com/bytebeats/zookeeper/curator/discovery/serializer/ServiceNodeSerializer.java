package com.bytebeats.zookeeper.curator.discovery.serializer;

import com.bytebeats.zookeeper.curator.discovery.domain.ServiceNode;
import com.bytebeats.zookeeper.util.JsonUtils;
import com.google.gson.reflect.TypeToken;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.InstanceSerializer;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-12-08 19:21
 */
public class ServiceNodeSerializer implements InstanceSerializer<ServiceNode> {

    private Charset charset = Charset.forName("UTF-8");

    @Override
    public byte[] serialize(ServiceInstance<ServiceNode> instance) throws Exception {
        String json = JsonUtils.toJson(instance);
        return json.getBytes(charset);
    }

    @Override
    public ServiceInstance<ServiceNode> deserialize(byte[] bytes) throws Exception {

        String json = new String(bytes, charset);

        Type jsonType = new TypeToken<ServiceInstance<ServiceNode>>() {}.getType();
        return JsonUtils.fromJson(json, jsonType);
    }
}
