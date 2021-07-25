//package io.perryquotes.api.telegram; TODO
//
//import io.perryquotes.api.AbstractIntegrationTest;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//
//import javax.persistence.EntityManager;
//import java.time.Instant;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class BotWebhookControllerIT extends AbstractIntegrationTest {
//
//  @LocalServerPort
//  private int port;
//
//  @Autowired
//  private TestRestTemplate restTemplate;
//
//  @Autowired
//  private EntityManager entityManager;
//
//  @Test
//  public void testWebhook() {
//    var msg = """
//      @Perry@ Hallo
//      #SB1
//      """;
//    var update = new IncomingBotMessage(
//      10L, new IncomingBotMessage.Message(
//        100L, Instant.now().toEpochMilli(), msg));
//
//    var response = restTemplate.exchange(
//      "http://localhost:" + port + "/webhook",
//      HttpMethod.POST, new HttpEntity<>(update), Void.class);
//    assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
//    assertEquals(1, entityManager.createQuery("SELECT q FROM Quote q").getResultList().size());
//    assertEquals(1, entityManager.createQuery("SELECT a FROM Author a").getResultList().size());
//  }
//
//  @Test
//  public void testWebhookUpdateIdIsNull() throws Exception {
//    //TODO
//  }
//
//
//  @Test
//  public void testWebhookUpdateIdIsMissing() throws Exception {
//    //TODO
//  }
//
//  @Test
//  public void testWebhookMessageIsNull() throws Exception {
//    //TODO
//  }
//
//  @Test
//  public void testWebhookMessageIsMissing() throws Exception {
//    //TODO
//  }
//
//  @Test
//  public void testWebhookMessageIdIsNull() throws Exception {
//    //TODO
//  }
//
//  @Test
//  public void testWebhookMessageIdIsMissing() throws Exception {
//    //TODO
//  }
//
//  @Test
//  public void testWebhookMessageDateIsNull() throws Exception {
//    //TODO
//  }
//
//  @Test
//  public void testWebhookMessageDateIsMissing() throws Exception {
//    //TODO
//  }
//
//  @Test
//  public void testWebhookTextIsNull() throws Exception {
//    //TODO
//  }
//
//  @Test
//  public void testWebhookTextIsMissing() throws Exception {
//    //TODO
//  }
//}
