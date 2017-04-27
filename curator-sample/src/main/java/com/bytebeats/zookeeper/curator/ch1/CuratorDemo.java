package com.bytebeats.zookeeper.curator.ch1;

import com.bytebeats.zookeeper.curator.CuratorUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.Charset;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-12-07 23:26
 */
public class CuratorDemo {

    private String path = "/pandora/app2/consumer";

    public static final Charset CHARSET = Charset.forName("UTF-8");

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

            Stat stat = client.checkExists().forPath(path);
            if(stat==null){
                System.out.println("exec create path:"+path);
                client.create()
                        .creatingParentContainersIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                        .forPath(path, "hello, zk".getBytes(CHARSET));
            }else {
                System.out.println("path: "+path+" exists");
            }

            byte[] buf = client.getData().forPath(path);
            System.out.println("get data path:"+path+", data:"+new String(buf, CHARSET));

            client.setData().inBackground().forPath(path, "ricky".getBytes(CHARSET));


            //级联删除
            //client.delete().deletingChildrenIfNeeded().forPath("/pandora");

        }finally {
            if(client!=null)
                client.close();
        }
    }
}
