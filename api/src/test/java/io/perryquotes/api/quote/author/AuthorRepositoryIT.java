package io.perryquotes.api.quote.author;

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
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
    entityManager.persist(new Author("Author1"));
    entityManager.persist(new Author("Author2"));
    entityManager.flush();
  }

  @Test
  public void testFindByUuid() {
    getExistingAuthors().forEach(existing -> {
      var result = authorRepository.findByUuid(existing.getUuid());
      assertTrue(result.isPresent());
      assertEquals(existing, result.get());
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
      assertEquals(existing, result.get());
    });
  }

  @Test
  public void testFindByNameNotFound() {
    assertTrue(authorRepository.findByName("Unknown").isEmpty());
  }

  @Test
  public void testFindByNameIn() {
    var existingNames = getExistingAuthors().stream().map(Author::getName).collect(Collectors.toSet());
    var result = authorRepository.findByNameIn(existingNames);
    assertEquals(2, result.size());

    getExistingAuthors()
      .forEach(existing -> {
        assertEquals(1, authorRepository.findByNameIn(Set.of(existing.getName())).size());
      });
  }

  @Test
  public void testFindByNameInNotFound() {
    assertTrue(authorRepository.findByNameIn(Set.of("Unknown1")).isEmpty());
    assertTrue(authorRepository.findByNameIn(Set.of("Unknown1", "Unknown2")).isEmpty());
    assertTrue(authorRepository.findByNameIn(Set.of()).isEmpty());
  }

  @Test
  public void testFindByUuidIn() {
    var existingUuids = getExistingAuthors().stream().map(Author::getUuid).collect(Collectors.toSet());
    var result = authorRepository.findByUuidIn(existingUuids);
    assertEquals(2, result.size());

    getExistingAuthors()
      .forEach(existing -> {
        assertEquals(1, authorRepository.findByUuidIn(Set.of(existing.getUuid())).size());
      });
  }

  @Test
  public void testFindByUuidInNotFound() {
    assertTrue(authorRepository.findByUuidIn(Set.of(UUID.randomUUID())).isEmpty());
    assertTrue(authorRepository.findByUuidIn(Set.of(UUID.randomUUID(), UUID.randomUUID())).isEmpty());
    assertTrue(authorRepository.findByUuidIn(Set.of()).isEmpty());
  }

  private List<Author> getExistingAuthors() {
    return entityManager.getEntityManager().createQuery("SELECT a FROM Author a", Author.class).getResultList();
  }
}
