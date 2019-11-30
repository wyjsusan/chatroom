package com.wyjsusan.spring.boot.chatroom.chatserver;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebDriverTest {

  private static final Logger logger = LoggerFactory.getLogger(WebDriverTest.class);

  @LocalServerPort
  private int serverPort;

  private static WebDriver _webDriver;
  private static String TEST_USER = "user1";
  private static String SEND_BUTTON_ID = "sendMsgToServer";
  private static String MESSAGE_CONTAINER_CLASS = "message-container";
  private static String MESSAGE_CONTENT_CLASS = "message-content";
  private static String EXIT_BUTTON_ID = "exitChatRoom";
  private static String LOGIN_BUTTON_ID = "loginButton";
  private static String USERNAME_INPUT_ID = "username";
  private static String ONLINE_USER_SPAN_ID = "onlineCount";

  @BeforeAll
  static void setup() {
    WebDriverManager.chromedriver().setup();
    _webDriver = new ChromeDriver();
  }

  @AfterAll
  static void destroy() {
    _webDriver.close();
  }

  @Test
  void testLoginAndJoinChatRoom() {
    String LOGIN_URL = String.format("http://localhost:%d/", serverPort);
    logger.debug("LOGIN_URL: " + LOGIN_URL);
    String INDEX_URL = String.format("http://localhost:%d/index?username=%s", serverPort, TEST_USER);
    _webDriver.get(LOGIN_URL);
    WebElement inputUsername = _webDriver.findElement(By.id(USERNAME_INPUT_ID));
    assertTrue(inputUsername.isDisplayed());
    inputUsername.sendKeys(TEST_USER);

    WebElement loginButton = _webDriver.findElement(By.id(LOGIN_BUTTON_ID));
    assertTrue(loginButton.isDisplayed());
    loginButton.click();

    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      logger.error("Unexpected error during test: ", e);
    }

    String currentUrl = _webDriver.getCurrentUrl();
    logger.info("currentUrl=======" + currentUrl);
    assertEquals(INDEX_URL, currentUrl);

    WebElement onlineCount = _webDriver.findElement(By.id(ONLINE_USER_SPAN_ID));
    assertEquals("Online Users: 1", onlineCount.getText());
  }

  @Test
  void testSendMessage() {
    String INDEX_URL = String.format("http://localhost:%d/index?username=%s", serverPort, TEST_USER);
    String testMsg = "Test Message";
    _webDriver.get(INDEX_URL);
    WebElement msgContainer = _webDriver.findElement(By.className(MESSAGE_CONTAINER_CLASS));
    int size = msgContainer.findElements(By.className("mdui-card")).size();
    assertEquals(1, size);

    WebElement msgInputField = _webDriver.findElement(By.className("mdui-textfield-input"));
    msgInputField.sendKeys(testMsg);
    _webDriver.findElement(By.id(SEND_BUTTON_ID)).click();

    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      logger.error("Unexpected error during test: ", e);
    }

    String expectedMsg = String.format("%s：%s", TEST_USER, testMsg);
    List<WebElement> msgContents = msgContainer.findElements(By.className(MESSAGE_CONTENT_CLASS));
    assertEquals(2, msgContents.size());
    assertEquals(expectedMsg, msgContents.get(msgContents.size() - 1).getText());
  }

  @Test
  void testExitChatRoom() {
    String INDEX_URL = String.format("http://localhost:%d/index?username=%s", serverPort, TEST_USER);
    _webDriver.get(INDEX_URL);
    WebElement exitButton = _webDriver.findElement(By.id(EXIT_BUTTON_ID));
    exitButton.click();

    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      logger.error("Unexpected error during test: ", e);
    }

    String currentUrl = _webDriver.getCurrentUrl();
    String LOGIN_URL = String.format("http://localhost:%d/", serverPort);
    assertEquals(LOGIN_URL, currentUrl);
  }
}
