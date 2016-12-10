package com.bytebeats.zookeeper.curator.discovery.domain;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-12-08 19:18
 */
public class ServerPayload {
    private String cluster;
    private int payload;

    public ServerPayload(){

    }

    public ServerPayload(String cluster, int payload) {
        this.cluster = cluster;
        this.payload = payload;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public int getPayload() {
        return payload;
    }

    public void setPayload(int payload) {
        this.payload = payload;
    }
}
