package io.perryquotes.api.quote.author;

import io.perryquotes.api.AbstractIntegrationTest;
import io.perryquotes.api.error.EntityNotFoundException;
import io.perryquotes.api.error.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional //necessary for automatic rollback after each test
public class AuthorServiceIT extends AbstractIntegrationTest {

  @Autowired
  private AuthorService authorService;

  @Autowired
  private AuthorRepository repository;

  @BeforeEach
  public void setUp() {
    repository.saveAll(List.of(new Author("Author1"), new Author("Author2")));
  }

  @Test
  public void testValidCreate() {
    var result = authorService.create(new AuthorRecord("Author3"));
    assertNotNull(result);
    assertNotNull(result.getUuid());
    assertNotNull(result.getCreatedAt());
    assertNotNull(result.getLastModifiedAt());
    assertEquals(3, repository.findAll().size());
  }

  @Test
  public void testCreateNameIsNull() {
    assertThrows(ConstraintViolationException.class, () -> authorService.create(new AuthorRecord(null)));
  }

  @Test
  public void testCreateNameIsEmpty() {
    assertThrows(ConstraintViolationException.class, () -> authorService.create(new AuthorRecord("")));
  }

  @Test
  public void testCreateExistingName() {
    assertThrows(InvalidDataException.class, () -> authorService.create(new AuthorRecord("Author1")));
  }

  @Test
  public void testValidUpdate() {
    var existing = repository.findByName("Author1").get();
    var updated = authorService.update(existing.getUuid(), new AuthorRecord("Author0815"));
    assertEquals(existing.getUuid(), updated.getUuid());
    assertEquals("Author0815", updated.getName());
  }

  @Test
  public void testUpdateNameIsNull() {
    var existing = repository.findByName("Author1").get();
    assertThrows(ConstraintViolationException.class,
      () -> authorService.update(existing.getUuid(), new AuthorRecord(null)));
  }

  @Test
  public void testUpdateNameIsEmpty() {
    var existing = repository.findByName("Author1").get();
    assertThrows(ConstraintViolationException.class,
      () -> authorService.update(existing.getUuid(), new AuthorRecord("")));
  }

  @Test
  public void testUpdateAuthorNotFound() {
    assertThrows(EntityNotFoundException.class,
      () -> authorService.update(UUID.randomUUID(), new AuthorRecord("Author0815")));
  }
}
