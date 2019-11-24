package com.wyjsusan.spring.boot.chatroom.util;

import com.wyjsusan.spring.boot.chatroom.domain.Message;
import com.wyjsusan.spring.boot.chatroom.domain.MessageType;
import javax.websocket.EncodeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class MessageEncoderTest {

  private MessageEncoder _encoder = new MessageEncoder();

  @Test
  void encode() throws EncodeException {
    Message message = new Message();
    message.setOnlineCount(10);
    message.setType(MessageType.SPEAK.name());
    message.setMsg("hello");
    message.setUsername("user1");
    assertEquals("{\"type\":\"SPEAK\",\"username\":\"user1\",\"onlineCount\":10,\"msg\":\"hello\"}",
        _encoder.encode(message));
  }
}