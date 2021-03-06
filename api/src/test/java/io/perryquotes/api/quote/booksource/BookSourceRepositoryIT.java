package io.perryquotes.api.quote.booksource;

import io.perryquotes.api.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookSourceRepositoryIT extends AbstractIntegrationTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private BookSourceRepository bookSourceRepository;

  @BeforeEach
  public void setUp() {
    entityManager.persist(new BookSource("Source1", "AB1"));
    entityManager.persist(new BookSource("Source2", "AB2"));
    entityManager.flush();
  }

  @Test
  public void testFindByUuid() {
    getExistingSources().forEach(existing -> {
      var result = bookSourceRepository.findByUuid(existing.getUuid());
      assertTrue(result.isPresent());
      assertEquals(existing, result.get());
    });
  }

  @Test
  public void testFindByUuidNotFound() {
   assertTrue(bookSourceRepository.findByUuid(UUID.randomUUID()).isEmpty());
  }

  @Test
  public void testFindByShortcut() {
    getExistingSources().forEach(existing -> {
      var result = bookSourceRepository.findByShortcut(existing.getShortcut());
      assertTrue(result.isPresent());
      assertEquals(existing, result.get());
    });
  }

  @Test
  public void testFindByShortcutNotFound() {
    assertTrue(bookSourceRepository.findByShortcut("XYZ1").isEmpty());
  }


  @Test
  public void testFindByName() {
    getExistingSources().forEach(existing -> {
      var result = bookSourceRepository.findByName(existing.getName());
      assertTrue(result.isPresent());
      assertEquals(existing, result.get());
    });
  }

  @Test
  public void testFindByNameNotFound() {
    assertTrue(bookSourceRepository.findByName("Unknown name").isEmpty());
  }

  private List<BookSource> getExistingSources() {
    return entityManager.getEntityManager().createQuery("SELECT bs FROM BookSource bs", BookSource.class).getResultList();
  }
}
