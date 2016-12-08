package com.bytebeats.zookeeper.apache;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-12-08 13:32
 */
public class ZKUtils {

    public static Stat setData(ZooKeeper zk, String path, byte[] data, int version) throws KeeperException, InterruptedException {
        return zk.setData(path, data, version);
    }

    public static byte[] getNodeValue(ZooKeeper zk, String path, boolean watch, Stat stat) throws KeeperException, InterruptedException {
        if(zk.exists(path, false)!=null){
            return zk.getData(path, watch, stat);
        }else{
            System.out.println("zk node:"+path+" not exists");
        }
        return null;
    }

    public static String createNode(ZooKeeper zk, String path, byte[] data, CreateMode mode) throws Exception {

        List<String> pathList = new ArrayList<>();
        String parent = path;
        while(parent!=null && parent.length()>0){
            pathList.add(parent);
            System.out.println(parent);
            parent = parent.substring(0, parent.lastIndexOf("/"));
        }

        String result = null;
        for(int i=pathList.size()-1;i>=0;i--){
            String p = pathList.get(i);
            byte[] buf = (i==0) ? data : null;
            if(zk.exists(p, false)==null) {  //逐级创建
                result = zk.create(p, buf, ZooDefs.Ids.OPEN_ACL_UNSAFE, mode);
                System.out.println("zk recursively create node:"+p+" success...");
            }
        }
        return result;
    }

    public static void deleteNode(ZooKeeper zk, String path) throws KeeperException, InterruptedException {

        if(zk.exists(path, false)!=null){
            zk.delete(path, -1);
        }
    }

    public static void closeQuietly(ZooKeeper zk){
        if(zk!=null){
            try {
                zk.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
