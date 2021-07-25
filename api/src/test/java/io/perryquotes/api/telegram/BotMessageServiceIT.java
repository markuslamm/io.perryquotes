package io.perryquotes.api.telegram;

import io.perryquotes.api.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional //necessary for automatic rollback after each test
public class BotMessageServiceIT extends AbstractIntegrationTest {

  @Autowired
  private BotMessageService service;

  @Autowired
  private BotMessageRepository repository;

  private static final String VALID_MSG_TEXT = """
    @Perry@ Hallo hallo
    @Thora@ Hallo Perry.
    #SB1
    """;

  @Test
  public void testCreateValidMessage() {
    var message = new IncomingBotMessage(1L,
      new IncomingBotMessage.Message(1L, Instant.now().toEpochMilli(), VALID_MSG_TEXT));
    var result = service.create(message);
    assertNotNull(result);
    assertNotNull(result.getUuid());
    assertNotNull(result.getCreatedAt());
    assertNotNull(result.getLastModifiedAt());
    assertTrue(repository.findByUuid(result.getUuid()).isPresent());
  }

  @Test
  public void testCreateMessageUpdateIdIsNull() {
    assertThrows(ConstraintViolationException.class,
      () -> service.create(
        new IncomingBotMessage(null,
          new IncomingBotMessage.Message(1L, Instant.now().toEpochMilli(), VALID_MSG_TEXT))));
  }

  @Test
  public void testCreateMessageIsNull() {
    assertThrows(ConstraintViolationException.class,
      () -> service.create(
        new IncomingBotMessage(1L, null)));
  }

  @Test
  public void testCreateMessageIdIsNull() {
    assertThrows(ConstraintViolationException.class,
      () -> service.create(
        new IncomingBotMessage(1L,
          new IncomingBotMessage.Message(null, Instant.now().toEpochMilli(), VALID_MSG_TEXT))));
  }

  @Test
  public void testCreateMessageDateIsNull() {
    assertThrows(ConstraintViolationException.class,
      () -> service.create(
        new IncomingBotMessage(1L,
          new IncomingBotMessage.Message(1L, null, VALID_MSG_TEXT))));
  }

  @Test
  public void testCreateMessageTextNull() {
    assertThrows(ConstraintViolationException.class,
      () -> service.create(
        new IncomingBotMessage(1L,
          new IncomingBotMessage.Message(1L, Instant.now().toEpochMilli(), null))));
  }

  @Test
  public void testCreateMessageTextEmpty() {
    assertThrows(ConstraintViolationException.class,
      () -> service.create(
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
