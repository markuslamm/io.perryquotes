package io.perryquotes.api.quote;

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
public class AuthorRepositoryIT extends AbstractIntegrationTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private AuthorRepository authorRepository;

  @BeforeEach
  public void setUp() {
    var author1 = entityManager.persist(new Author("Author1"));
    var author2 = entityManager.persist(new Author("Author2"));

    entityManager.flush();
  }

  @Test
  public void testFindByUuid() {
    getExistingAuthors().forEach(existing -> {
      var result = authorRepository.findByUuid(existing.getUuid());
      assertTrue(result.isPresent());
      assertEquals(result.get().getUuid(), existing.getUuid());
    });
  }

  @Test
  public void testFindByUuidNotFound() {
    assertTrue(authorRepository.findByUuid(UUID.randomUUID()).isEmpty());
  }

  @Test
  public void testFindByName() {
    getExistingAuthors().forEach(existing -> {
      var result = authorRepository.findByName(existing.getName());
      assertTrue(result.isPresent());
      assertEquals(result.get().getName(), existing.getName());
    });
  }

  @Test
  public void testFindByNameNotFound() {
    assertTrue(authorRepository.findByName("Unknown").isEmpty());
  }

  @SuppressWarnings("unchecked")
  private List<Author> getExistingAuthors() {
    return (List<Author>) entityManager.getEntityManager()
      .createQuery("SELECT a FROM Author a")
      .getResultList();
  }
}
