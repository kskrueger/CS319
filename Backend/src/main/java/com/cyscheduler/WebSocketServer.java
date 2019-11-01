package com.cyscheduler;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/websocket/{username}")
@Component
public class WebSocketServer {
    private static Map<Session, String> sessionUsernameMap = new HashMap<>();
    private static Map<String, Session> usernameSessionMap = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException {
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);
        System.out.println(username + " connected");
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        String username = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);
        System.out.println(username + " disconnected");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // ERROR
        System.out.println("Error: session: "+session.toString()+", throw: "+throwable.toString());
    }
}
