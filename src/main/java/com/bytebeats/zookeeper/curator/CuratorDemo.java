package com.bytebeats.zookeeper.curator;

import com.bytebeats.zookeeper.BaseConfigDemo;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-12-07 23:26
 */
public class CuratorDemo extends BaseConfigDemo {

    private String path = "/pandora/app1/consumer";

    public static void main(String[] args) {

        try{
            new CuratorDemo().start();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private void start() throws Exception {
        init();
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(address, retryPolicy);
        try{
            client.start();

            client.create()
                    .creatingParentContainersIfNeeded()
                    .withMode(CreateMode.PERSISTENT)
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                    .forPath(path, "hello, zk".getBytes());

            byte[] buf = client.getData().forPath(path);
            System.out.println("get data path:"+path+", data:"+new String(buf));

            client.setData().forPath(path, "ricky".getBytes());

            //级联删除
            //client.delete().deletingChildrenIfNeeded().forPath("/pandora");

        }finally {
            if(client!=null)
                client.close();
        }
    }
}
