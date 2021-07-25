package io.perryquotes.api.quote.booksource;

import io.perryquotes.api.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional //necessary for automatic rollback after each test
public class BookSourceServiceIT extends AbstractIntegrationTest {

  @Autowired
  private BookSourceService bookSourceService;

  @Autowired
  private EntityManager entityManager;

  @BeforeEach
  public void setUp() {
    //TODO
  }

  @Test
  public void testValidCreate() {
    //TODO
  }

  @Test
  public void testCreateMissingName() {
    //TODO
  }

  @Test
  public void testCreateNameIsNull() {
    //TODO
  }

  @Test
  public void testCreateNameIsEmpty() {
    //TODO
  }

  @Test
  public void testCreateExistingName() {
    //TODO
  }

  @Test
  public void testCreateMissingShortcut() {
    //TODO
  }

  @Test
  public void testCreateShortcutIsNull() {
    //TODO
  }

  @Test
  public void testCreateShortcutIsEmpty() {
    //TODO
  }

  @Test
  public void testCreateShortcutIsWrongPattern() {
    //TODO
  }

  @Test
  public void testCreateExistingShortcut() {
    //TODO
  }





  @Test
  public void testValidUpdate() {
    //TODO
  }

  @Test
  public void testUpdateMissingName() {
    //TODO
  }

  @Test
  public void testUpdateNameIsNull() {
    //TODO
  }

  @Test
  public void testUpdateNameIsEmpty() {
    //TODO
  }

  @Test
  public void testUpdateBookSourceNotFound() {
    //TODO
  }

  @Test
  public void testUpdateMissingShortcut() {
    //TODO
  }

  @Test
  public void testUpdateShortcutIsNull() {
    //TODO
  }

  @Test
  public void testUpdateShortcutIsEmpty() {
    //TODO
  }

  @Test
  public void testUpdateShortcutIsWrongPattern() {
    //TODO
  }
}
