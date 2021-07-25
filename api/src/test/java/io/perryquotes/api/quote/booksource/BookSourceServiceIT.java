package io.perryquotes.api.quote.booksource;

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
public class BookSourceServiceIT extends AbstractIntegrationTest {

  @Autowired
  private BookSourceService bookSourceService;

  @Autowired
  private BookSourceRepository repository;

  @BeforeEach
  public void setUp() {
    repository.saveAll(
      List.of(new BookSource("Source1", "AB1"), new BookSource("Source2", "AB2")));
  }

  @Test
  public void testValidCreate() {
    var result = bookSourceService.create(
      new BookSourceRecord("BookSource 3", "BS3"));
    assertNotNull(result);
    assertNotNull(result.getUuid());
    assertNotNull(result.getCreatedAt());
    assertNotNull(result.getLastModifiedAt());
    assertTrue(repository.findByUuid(result.getUuid()).isPresent());
  }

  @Test
  public void testCreateExistingName() {
    assertThrows(InvalidDataException.class,
      () -> bookSourceService.create(new BookSourceRecord("Source1", "BS3")));
  }

  @Test
  public void testCreateShortcutIsNull() {
    assertThrows(ConstraintViolationException.class,
      () -> bookSourceService.create(new BookSourceRecord("Source1", null)));
  }

  @Test
  public void testCreateShortcutIsEmpty() {
    assertThrows(ConstraintViolationException.class,
      () -> bookSourceService.create(new BookSourceRecord("Source1", "")));
  }

  @Test
  public void testCreateShortcutIsWrongPattern() {
    assertThrows(ConstraintViolationException.class,
      () -> bookSourceService.create(new BookSourceRecord("Source1", "ABVCFDFSFS")));
  }

  @Test
  public void testCreateExistingShortcut() {
    assertThrows(InvalidDataException.class,
      () -> bookSourceService.create(new BookSourceRecord("Source1", "AB1")));
  }

  @Test
  public void testValidUpdate() {
    var existing = repository.findByName("Source1").get();
    var updated = bookSourceService.update(existing.getUuid(), new BookSourceRecord("SourceNEW", "NEW1"));
    assertEquals(existing.getUuid(), updated.getUuid());
    assertEquals("SourceNEW", updated.getName());
    assertEquals("NEW1", updated.getShortcut());
  }

  @Test
  public void testUpdateBookSourceNotFound() {
    assertThrows(EntityNotFoundException.class,
      () -> bookSourceService.update(UUID.randomUUID(), new BookSourceRecord("SourceNEW", "NEW1")));
  }

  @Test
  public void testUpdateShortcutIsNull() {
    assertThrows(ConstraintViolationException.class,
      () -> bookSourceService.update(UUID.randomUUID(), new BookSourceRecord("SourceNEW", null)));
  }

  @Test
  public void testUpdateShortcutIsEmpty() {
    assertThrows(ConstraintViolationException.class,
      () -> bookSourceService.update(UUID.randomUUID(), new BookSourceRecord("SourceNEW", "")));
  }

  @Test
  public void testUpdateShortcutIsWrongPattern() {
    assertThrows(ConstraintViolationException.class,
      () -> bookSourceService.update(UUID.randomUUID(), new BookSourceRecord("SourceNEW", "ABVCFDFSFS")));
  }
}
