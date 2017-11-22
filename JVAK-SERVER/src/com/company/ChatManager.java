package com.company;

import com.company.model.User;

import java.net.InetAddress;
import java.util.HashMap;

public class ChatManager {
    /* STEPS OF THE PROCESS:
    1. SERVER CONNECTION
    2. LOGIN
    3. INITIALIZE CONVERSATION
    4. MESSAGING
    5. END CONVERSATION
    6. LOGOUT
    7. CLOSE CONNECTION
    8. REPEAT (GOTO 1)
     */
    //TRANSLATION OF ADDRESSES
    //SERVER CONNECTION -> LOGIN -> INIT CONVERSATION -> MESSAGING ->  -> LOGOUT -> CLOSE CONNECTION -> REPEAT

    /*
    We have two Users (1) and (2), and a Server (S).
    When we are online as User (1), and User (2) is online as well, we init a conversation.
    When User(1) is in a conversation with User(2), they are a pair. When paired:
    User(1) sends a message to the Server (S), which User(2) from the same pair receives the message from the Server(S)
    as well.
     */

    private static UserManager userManager = UserManager.getInstance();
    private HashMap<String, String> conversations = new HashMap<>();

    public boolean login(String username, String password, InetAddress ipAddress) {
        User user = userManager.getUser(username);

        if (user == null) {
            return false;
        } else {
            if (user.getPassword().equals(password)) {
                user.setStatus(User.STATUS_ONLINE);
                user.setIpAddress(ipAddress);
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean logout(String username) {
        User user = userManager.getUser(username);

        if (user == null) {
            return false;
        } else {
            if (user.getStatus() != User.STATUS_OFFLINE) {
                user.setStatus(User.STATUS_OFFLINE);
                return true;
            } else {
                return false;
            }
        }
    }

    private boolean isUserOnline(String username) {
        User user = userManager.getUser(username);

        if (user == null) {
            return false;
        } else {
            if (user.getStatus() != User.STATUS_OFFLINE) {
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean initConversation(String requester, String receiver) {
        if (isUserOnline(receiver)) {
            if (conversations.get(receiver) != null) {
                return false;
            } else {
                conversations.put(requester, receiver);
                conversations.put(receiver, requester);
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean endConversation(String requester) {
        String receiver = conversations.get(requester);
        if (receiver != null) {
            conversations.remove(receiver);
            conversations.remove(requester);
            return true;
        } else {
            return false;
        }
    }

    public InetAddress getIPForUser(String otherUsername) {
        return userManager.getUser(otherUsername).getIpAddress();
    }

    public String getOtherSide(String otherUsername) {
        return conversations.get(otherUsername);
    }
}
