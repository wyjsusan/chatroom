package com.wyjsusan.spring.boot.chatroom.chatserver;

import com.wyjsusan.spring.boot.chatroom.domain.Message;
import com.wyjsusan.spring.boot.chatroom.domain.MessageType;
import com.wyjsusan.spring.boot.chatroom.util.MessageDecoder;
import com.wyjsusan.spring.boot.chatroom.util.MessageEncoder;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;


/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
@ServerEndpoint(value = "/chat/{username}", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class WebSocketChatServer {

  /**
   * All chat sessions.
   */
  private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();

  /**
   * Open connection, 1) add session, 2) add user.
   */
  @OnOpen
  public void onOpen(Session session) {
    //TODO: add on open connection.
    onlineSessions.put(session.getId(), session);
    String username = session.getPathParameters().get("username");

    Message message = new Message();
    message.setType(MessageType.JOIN_LEFT.name());
    message.setUsername(username);
    message.setMsg("has joined the room.");
    message.setOnlineCount(onlineSessions.size());

    sendMessageToAll(message);
  }

  /**
   * Send message, 1) get username and session, 2) send message to all.
   */
  @OnMessage
  public void onMessage(Session session, Message message) {
    //TODO: add send message.
    message.setType(MessageType.SPEAK.name());
    message.setOnlineCount(onlineSessions.size());
    sendMessageToAll(message);
  }

  /**
   * Close connection, 1) remove session, 2) update user.
   */
  @OnClose
  public void onClose(Session session) {
    //TODO: add close connection.
    String username = session.getPathParameters().get("username");
    onlineSessions.remove(session.getId());
    Message message = new Message();
    message.setType(MessageType.JOIN_LEFT.name());
    message.setUsername(username);
    message.setMsg("has left the room.");
    message.setOnlineCount(onlineSessions.size());
    sendMessageToAll(message);
  }

  /**
   * Print exception.
   */
  @OnError
  public void onError(Session session, Throwable error) {
    error.printStackTrace();
  }

  private static void sendMessageToAll(Message message) {

    onlineSessions.values().forEach(session -> {
      synchronized (session) {
        try {
          session.getBasicRemote().sendObject(message);
        } catch (IOException | EncodeException e) {
          e.printStackTrace();
        }
      }
    });
  }
}
