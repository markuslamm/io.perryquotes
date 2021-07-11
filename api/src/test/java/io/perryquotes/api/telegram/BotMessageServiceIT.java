package io.perryquotes.api.telegram;

import io.perryquotes.api.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class BotMessageServiceIT extends AbstractIntegrationTest {

  @Autowired
  private BotMessageService service;

  @Autowired
  private EntityManager entityManager;

  @Test
  public void testStoreValidMessage() {
    var message = new IncomingBotMessage(1L,
      new IncomingBotMessage.Message(1L, Instant.now().toEpochMilli(), "Quote Text@@Author@@SB1"));
    var result = service.storeMessage(message);
    assertNotNull(result);
    assertNotNull(result.getQuoteUuid());
    assertEquals(1, entityManager.createQuery("SELECT bm FROM BotMessage bm").getResultList().size());
    assertEquals(1, entityManager.createQuery("SELECT q FROM Quote q").getResultList().size());
    assertEquals(1, entityManager.createQuery("SELECT a FROM Author a").getResultList().size());
  }

  @Test
  public void testStoreMessageMissingUpdateId() {
    //TODO
  }

  @Test
  public void testStoreMessageMissingMessage() {
    //TODO
  }

  @Test
  public void testStoreMessageMissingMessageId() {
    //TODO
  }

  @Test
  public void testStoreMessageMissingMessageDate() {
    //TODO
  }

  @Test
  public void testStoreMessageMissingMessageTextNull() {
    //TODO
  }

  @Test
  public void testStoreMessageMissingMessageTextEmpty() {
    //TODO
  }

  @Test
  public void testSetFailureState() {
    //TODO
  }

  @Test
  public void testSetFailureStateNotFound() {
    //TODO
  }

  @Test
  public void testSetSuccessState() {
    //TODO
  }

  @Test
  public void testSetSuccessStateNotFound() {
    //TODO
  }
}
