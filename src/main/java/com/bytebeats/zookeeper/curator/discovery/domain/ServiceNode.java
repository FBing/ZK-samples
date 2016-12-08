package com.bytebeats.zookeeper.curator.discovery.domain;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-12-08 19:18
 */
public class ServiceNode {
    private String host;
    private int port;
    private String interfaceName;
    private int weight; //权重

    public ServiceNode(){

    }
    public ServiceNode(String host, int port, String interfaceName, int weight) {
        this.host = host;
        this.port = port;
        this.interfaceName = interfaceName;
        this.weight = weight;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
