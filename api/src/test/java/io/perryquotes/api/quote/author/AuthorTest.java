package io.perryquotes.api.quote.author;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AuthorTest {

  @Test
  public void testEquals() {
    var author1 = new Author("Author1");
    var author2 = new Author("Author1");
    assertNotEquals(author1, author2);

    var uuid = UUID.randomUUID();
    author1.setUuid(uuid);
    author2.setUuid(uuid);
    assertEquals(author1.hashCode(), author2.hashCode());

    author1.setUuid(UUID.randomUUID());
    assertNotEquals(author1, author2);

    author1.setUuid(uuid);
    assertEquals(author1, author2);

    author1.setName("NewName");
    assertNotEquals(author1, author2);
  }

  @Test
  public void testHashCode() {
    var author1 = new Author("Author1");
    var author2 = new Author("Author1");
    assertNotEquals(author1.hashCode(), author2.hashCode());

    var uuid = UUID.randomUUID();
    author1.setUuid(uuid);
    author2.setUuid(uuid);
    assertEquals(author1.hashCode(), author2.hashCode());

    author1.setUuid(UUID.randomUUID());
    assertNotEquals(author1.hashCode(), author2.hashCode());

    author1.setUuid(uuid);
    assertEquals(author1.hashCode(), author2.hashCode());

    author1.setName("NewName");
    assertNotEquals(author1.hashCode(), author2.hashCode());
  }
}
