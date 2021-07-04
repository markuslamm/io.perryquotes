package io.perryquotes.api.telegram;

import io.perryquotes.api.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BotWebhookControllerIT extends AbstractIntegrationTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void testWebhook() {
    var update = new IncomingBotMessage(
      10L, new IncomingBotMessage.Message(
        100L, Instant.now().toEpochMilli(), "Message Text@@Author@@SB1"));

    var response = restTemplate.exchange(
      "http://localhost:" + port + "/webhook",
      HttpMethod.POST, new HttpEntity<>(update) ,
      Void.class);
    assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    //TODO assertEquals(1, entityManager.createQuery("SELECT q FROM Quote q").getResultList().size());
  }
}
