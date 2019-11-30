package com.wyjsusan.spring.boot.chatroom.controller;

import java.net.UnknownHostException;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
  @GetMapping("/index")
  public ModelAndView index(@PathParam("username") String username, Model model, HttpServletRequest request)
      throws UnknownHostException {
    //TODO: add code for login to chatroom.
//    System.out.println("request.getLocalPort()=========" + request.getLocalPort());
//    System.out.println("request.getRemotePort()=========" + request.getRemotePort());
//    System.out.println("request.getServerPort()=========" + request.getServerPort());
    model.addAttribute("username", username);
    model.addAttribute("webSocketUrl", String.format("ws://localhost:%d/chat/%s", request.getServerPort(), username));
    return new ModelAndView("chat", "model", model);
  }
}
