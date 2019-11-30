package com.wyjsusan.spring.boot.chatroom.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChatControllerSpringBootTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate _restTemplate;

  @Autowired
  private MockMvc _mvc;

  @Test
  void login() {
    ResponseEntity<String> response =
        _restTemplate.getForEntity(String.format("http://localhost:%d/", port), String.class);
    assertEquals(response.getStatusCode(), HttpStatus.OK);
  }

  @Test
  void index() throws Exception {
    _mvc.perform(MockMvcRequestBuilders.get(String.format("http://localhost:%d/index?username=user1", port)))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
        .andExpect(view().name("chat"))
        .andExpect(model().attribute("username", "user1"))
        .andExpect(model().attribute("webSocketUrl", String.format("ws://localhost:%d/chat/user1", port)));
  }
}