package com.wyjsusan.spring.boot.chatroom.controller;

import java.net.UnknownHostException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class ChatController {
  /**
   * Login Page
   */
  @GetMapping("/")
  public ModelAndView login() {
    return new ModelAndView("login");
  }

  /**
   * Chatroom Page
   */
  @GetMapping("/index/{username}")
  public ModelAndView index(@PathVariable("username") String username, Model model) throws UnknownHostException {
    //TODO: add code for login to chatroom.
    model.addAttribute("username", username);
    model.addAttribute("webSocketUrl", "ws://localhost:8080/chat/" + username);
    return new ModelAndView("chat", "model", model);
  }
}
