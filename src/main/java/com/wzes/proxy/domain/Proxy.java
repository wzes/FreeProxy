package com.wzes.proxy.domain;

import com.alibaba.fastjson.JSONObject;

public class Proxy {
    public Proxy(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    private String ip;
    private String port;

    public Proxy(String ip, String port, String location) {
        this.ip = ip;
        this.port = port;
        this.location = location;
    }

    public Proxy(String ip, String port, String location, String type) {
        this.ip = ip;
        this.port = port;
        this.location = location;
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private String location;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
