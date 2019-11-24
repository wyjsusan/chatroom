package com.wyjsusan.spring.boot.chatroom.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChatControllerTest {

  @Autowired
  private MockMvc _mvc;

  @Test
  void login() throws Exception {
    _mvc.perform(MockMvcRequestBuilders.get("/"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
        .andExpect(view().name("/login"));
  }

  @Test
  void index() throws Exception {
    _mvc.perform(MockMvcRequestBuilders.get("/index/user1"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
        .andExpect(view().name("/chat"))
        .andExpect(model().attribute("username", "user1"))
        .andExpect(model().attribute("webSocketUrl", "ws://localhost:8080/chat/user1"));
  }
}