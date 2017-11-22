package com.company.model;

import java.net.InetAddress;

public class User {
    public static final int STATUS_OFFLINE = 0;
    public static final int STATUS_ONLINE = 1;
    public static final int STATUS_AFK = 2;

    private String username;
    private String password;
    private String email;
    private int status = STATUS_OFFLINE;

    private InetAddress ipAddress;
    private int port = 3000;

    public String getUsername() {
        return username;
    }

    public void setUsername(String nickname) {
        this.username = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }
    //TODO
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
