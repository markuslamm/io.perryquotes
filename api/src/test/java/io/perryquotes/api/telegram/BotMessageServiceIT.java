package io.perryquotes.api.telegram;

import io.perryquotes.api.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

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
  public void testStoreMessageUpdateIdIsNull() {
    assertThrows(ConstraintViolationException.class,
      () -> service.storeMessage(
        new IncomingBotMessage(null,
          new IncomingBotMessage.Message(1L, Instant.now().toEpochMilli(), "Quote Text@@Author@@SB1"))));
  }

  @Test
  public void testStoreMessageIsNull() {
    assertThrows(ConstraintViolationException.class,
      () -> service.storeMessage(
        new IncomingBotMessage(1L, null)));
  }

  @Test
  public void testStoreMessageIdIsNull() {
    assertThrows(ConstraintViolationException.class,
      () -> service.storeMessage(
        new IncomingBotMessage(1L,
          new IncomingBotMessage.Message(null, Instant.now().toEpochMilli(), "Quote Text@@Author@@SB1"))));
  }

  @Test
  public void testStoreMessageDateIsNull() {
    assertThrows(ConstraintViolationException.class,
      () -> service.storeMessage(
        new IncomingBotMessage(1L,
          new IncomingBotMessage.Message(1L, null, "Quote Text@@Author@@SB1"))));
  }

  @Test
  public void testStoreMessageTextNull() {
    assertThrows(ConstraintViolationException.class,
      () -> service.storeMessage(
        new IncomingBotMessage(1L,
          new IncomingBotMessage.Message(1L, Instant.now().toEpochMilli(), null))));
  }

  @Test
  public void testStoreMessageMissingMessageTextEmpty() {
    assertThrows(ConstraintViolationException.class,
      () -> service.storeMessage(
        new IncomingBotMessage(1L,
          new IncomingBotMessage.Message(1L, Instant.now().toEpochMilli(), ""))));
  }

  @Test
  public void testSetFailureState() {

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
