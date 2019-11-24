package com.wyjsusan.spring.boot.chatroom.util;

import com.wyjsusan.spring.boot.chatroom.domain.Message;
import javax.websocket.DecodeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class MessageDecoderTest {

  private MessageDecoder _decoder = new MessageDecoder();

  @Test
  void decode() throws DecodeException {
    Message message =
        _decoder.decode("{\"type\":\"SPEAK\",\"username\":\"user1\",\"onlineCount\":10,\"msg\":\"hello\"}");
    assertEquals("hello", message.getMsg());
    assertEquals("SPEAK", message.getType());
    assertEquals("user1", message.getUsername());
    assertEquals(10, message.getOnlineCount());
  }
}