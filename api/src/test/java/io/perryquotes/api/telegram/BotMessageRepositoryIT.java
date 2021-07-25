package io.perryquotes.api.telegram;

import io.perryquotes.api.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BotMessageRepositoryIT extends AbstractIntegrationTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private BotMessageRepository repository;

  @BeforeEach
  public void setUp() {
    entityManager.persist(new BotMessage(1L, 2L, Instant.now().getEpochSecond(),
      "Message text 1").setQuoteUuid(UUID.randomUUID()));
    entityManager.persist(new BotMessage(2L, 4L, Instant.now().getEpochSecond(),
      "Message text 2").setQuoteUuid(UUID.randomUUID()));
    entityManager.flush();
  }

  @Test
  public void testFindByUuid() {
    getExistingBotMessages().forEach(existing -> {
      var result = repository.findByUuid(existing.getUuid());
      assertTrue(result.isPresent());
      assertEquals(existing, result.get());
    });
  }

  @Test
  public void testFindByUuidNotFound() {
    assertTrue(repository.findByUuid(UUID.randomUUID()).isEmpty());
  }

  @Test
  public void testFindByProcessingStatus() {
    assertEquals(2, repository.findByProcessingStatus(ProcessingStatus.UNPROCESSED).size()); //Default state
  }

  @Test
  public void testFindByProcessingStatusNotFound() {
    assertTrue(repository.findByProcessingStatus(ProcessingStatus.SUCCESS).isEmpty());
  }

  @Test
  public void testFindByQuoteUuid() {
    getExistingBotMessages().forEach(existing -> {
      var result = repository.findByQuoteUuid(existing.getQuoteUuid());
      assertTrue(result.isPresent());
      assertEquals(existing, result.get());
    });
  }

  @Test
  public void testFindByQuoteUuidNotFound() {
    assertTrue(repository.findByQuoteUuid(UUID.randomUUID()).isEmpty());
  }

  private List<BotMessage> getExistingBotMessages() {
    return entityManager.getEntityManager().createQuery("SELECT bm FROM BotMessage bm", BotMessage.class).getResultList();
  }
}
