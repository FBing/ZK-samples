package com.bytebeats.zookeeper.apache;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ApacheZKDemo extends BaseConfigDemo implements Watcher {

    private String path = "/zuka/127.0.0.1/com.bytebeats.api.IUserService/provider";

    private ZooKeeper zk;

    public static void main(String[] args) {

        try{
            new ApacheZKDemo().start();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    protected void start() throws Exception {
        init();
        String data = "hello";
        try{
            zk = new ZooKeeper(address, timeout, this);

            String result = ZKUtils.createNode(zk, path, data.getBytes(), CreateMode.PERSISTENT);
            logger.info("create zk node path:{}, data:{} success!", path, result);

            byte[] bytes = ZKUtils.getNodeValue(zk, path, true, null);
            result = new String(bytes);
            logger.info("read zk path:{}, result:{}", path, result);

        }finally {
            ZKUtils.closeQuietly(zk);
        }

    }

    @Override
    public void process(WatchedEvent event) {
        logger.info("Watcher process event type:{}, state:{}, path:{}", event.getType(), event.getState(), event.getPath());
    }
}
