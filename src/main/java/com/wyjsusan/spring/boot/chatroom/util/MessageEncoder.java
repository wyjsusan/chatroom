package com.wyjsusan.spring.boot.chatroom.util;

import com.google.gson.Gson;
import com.wyjsusan.spring.boot.chatroom.domain.Message;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;


public class MessageEncoder implements Encoder.Text<Message> {

  private static Gson gson = new Gson();

  @Override
  public String encode(Message message) throws EncodeException {
    return gson.toJson(message);
  }

  @Override
  public void init(EndpointConfig endpointConfig) {
    // Custom initialization logic
  }

  @Override
  public void destroy() {
    // Close resources
  }
}
