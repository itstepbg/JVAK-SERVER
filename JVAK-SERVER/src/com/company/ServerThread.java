package com.company;

import com.company.model.API;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ServerThread extends Thread {

    private ChatManager chatManager = new ChatManager();

    private DatagramSocket serverSocket;
    private BufferedReader reader;

    private boolean running = true;

    public ServerThread(String name) {
        super(name);
        try {
            serverSocket = new DatagramSocket(3000);
            serverSocket.setSoTimeout(1000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();

        byte[] buffer = new byte[256];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        while (running) {
            try {
                serverSocket.receive(packet);
                String request = new String(packet.getData()).replaceAll("\\u0000", "");
                handleRequest(request, packet.getAddress());
            } catch (IOException e) {
                String message = e.getMessage();
                if (!message.contains("Receive timed out")) {
                    System.out.println(e.getMessage());
                }
            } catch (Exception e) {
                //TODO
            }
        }

        serverSocket.close();
    }

    private void handleRequest(String request, InetAddress ipAddress) {
        System.out.println();
        System.out.println("RECIEVE (" + ipAddress.getHostAddress() + "): " + request);

        String prefix = request.split(":")[0] + ":";
        String payload = request.split(":")[1];

        String userName;
        String otherUserName;
        InetAddress otherIPAddress;

        String status;

        switch (prefix) {
            case API.PREFIX_LOGIN:
                userName = payload.split(",")[0];
                String password = payload.split(",")[1];

                boolean isLoginSuccessfull = chatManager.login(userName, password, ipAddress);

                status = isLoginSuccessfull ? API.STATUS_OK : API.STATUS_NOT_OK;

                sendMessage(API.PREFIX_STATUS + status, ipAddress);
                if (isLoginSuccessfull) {
                    //TODO
                }

                break;
            case API.PREFIX_LOGOUT:
                userName = payload.split(",")[0];
                boolean isLogoutSuccessfull = chatManager.logout(userName);

                status = isLogoutSuccessfull ? API.STATUS_OK : API.STATUS_NOT_OK;

                sendMessage(API.PREFIX_STATUS + status, ipAddress);
                break;
            case API.PREFIX_INIT_CONVERSATION:
                userName = payload.split(",")[0];
                otherUserName = payload.split(",")[0];

                boolean isConversationInitialized = chatManager.initConversation(userName, otherUserName);

                status = isConversationInitialized ? API.STATUS_OK : API.STATUS_NOT_OK;

                sendMessage(API.PREFIX_STATUS + status, ipAddress);

                otherIPAddress = chatManager.getIPForUser(otherUserName);

                sendMessage(API.PREFIX_CONVERSATION_INITIALIZED + userName, otherIPAddress);

                break;
            case API.PREFIX_END_CONVERSATION:
                userName = payload;

                boolean hasConversationEnded = chatManager.endConversation(userName);

                status = hasConversationEnded ? API.STATUS_OK : API.STATUS_NOT_OK;

                sendMessage(API.PREFIX_STATUS + status, ipAddress);

                otherUserName = chatManager.getOtherSide(userName);
                otherIPAddress = chatManager.getIPForUser(otherUserName);

                sendMessage(API.PREFIX_CONVERSATION_INITIALIZED + userName, otherIPAddress);
                break;

            case API.PREFIX_CHAT_MESSAGE:
                userName = payload.split(",")[0];
                String message = payload.split(",")[1];

                otherUserName = chatManager.getOtherSide(userName);
                otherIPAddress = chatManager.getIPForUser(otherUserName);

                sendMessage(API.PREFIX_CHAT_MESSAGE + message, otherIPAddress);
                break;
        }
    }

    private void sendMessage(String message, InetAddress ipAddress) {
        byte[] buffer = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, ipAddress, 4000);

        try {
            serverSocket.send(packet);
            System.out.println();
            System.out.println("SEND (" + ipAddress.getHostAddress() + "): " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        running = false;
    }
}