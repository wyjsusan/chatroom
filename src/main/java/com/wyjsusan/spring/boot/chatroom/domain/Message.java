package com.wyjsusan.spring.boot.chatroom.domain;

/**
 * WebSocket message model
 */
public class Message {
  private String type;
  private String username;
  private int onlineCount;
  private String msg;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getOnlineCount() {
    return onlineCount;
  }

  public void setOnlineCount(int onlineCount) {
    this.onlineCount = onlineCount;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public Message() {
  }

  @Override
  public String toString() {
    return "Message{" + "type='" + type + '\'' + ", username='" + username + '\'' + ", onlineCount=" + onlineCount
        + ", msg='" + msg + '\'' + '}';
  }
}
