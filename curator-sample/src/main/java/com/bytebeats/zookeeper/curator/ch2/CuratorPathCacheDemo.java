package com.bytebeats.zookeeper.curator.ch2;

import com.bytebeats.zookeeper.curator.CuratorUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-12-10 15:37
 */
public class CuratorPathCacheDemo {

    private String path = "/pandora";

    public static final Charset CHARSET = Charset.forName("UTF-8");

    public static void main(String[] args) {

        try{
            new CuratorPathCacheDemo().start();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private void start() throws Exception {
        CuratorFramework client = CuratorUtils.getCuratorClient();
        try{
            client.start();

            final PathChildrenCache pathChildrenCache = new PathChildrenCache(client, path, true);

            pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
                @Override
                public void childEvent(CuratorFramework curatorFramework,
                                       PathChildrenCacheEvent event) throws Exception {

                    System.out.println("======== catch children change =======");
                    System.out.println("update event type:" + event.getType() +
                            ",path:" + event.getData().getPath() + ",data:" + new String(event.getData().getData(), CHARSET));

                    List<ChildData> childDataList = pathChildrenCache.getCurrentData();
                    if (childDataList != null && childDataList.size() > 0) {
                        System.out.println("path all children list:");
                        for (ChildData childData : childDataList) {
                            System.out.println("path:" + childData.getPath() + "," + new String(childData.getData(), CHARSET));
                        }
                    }
                }
            });

            pathChildrenCache.start();  //must call start();

            TimeUnit.MINUTES.sleep(5);

            pathChildrenCache.close();

        }finally {
            if(client!=null)
                client.close();
        }
    }
}
