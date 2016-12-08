package com.bytebeats.zookeeper.curator.ch1;

import com.bytebeats.zookeeper.curator.CuratorUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-12-07 23:26
 */
public class CuratorDemo {

    private String path = "/pandora/app2/consumer";

    public static void main(String[] args) {

        try{
            new CuratorDemo().start();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private void start() throws Exception {

        CuratorFramework client = CuratorUtils.getCuratorClient();
        try{
            client.start();

            if(client.checkExists().forPath(path)!=null){
                System.out.println("path: "+path+" exists");
            }else {
                System.out.println("path: "+path+" not exists");
            }

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
