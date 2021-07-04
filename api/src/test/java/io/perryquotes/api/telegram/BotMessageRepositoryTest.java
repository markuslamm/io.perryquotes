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

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BotMessageRepositoryTest extends AbstractIntegrationTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private BotMessageRepository repository;

  @BeforeEach
  public void setUp() {
    var telegramMessage1 = new BotMessage(
      1L, 2L, Instant.now().getEpochSecond(), "Message1");
    telegramMessage1.setProcessingStatus(ProcessingStatus.PROCESSED);
    entityManager.persist(telegramMessage1);

    var telegramMessage2 = entityManager.persist(
      new BotMessage(2L, 4L, Instant.now().getEpochSecond(), "Message2"));

    entityManager.flush();
  }

  @Test
  public void testFindByProcessingStatus() {
    assertEquals(1, repository.findByProcessingStatus(ProcessingStatus.PROCESSED).size());
    assertEquals(1, repository.findByProcessingStatus(ProcessingStatus.UNPROCESSED).size());
  }


}
