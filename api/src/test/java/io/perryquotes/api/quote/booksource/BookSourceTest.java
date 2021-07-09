package io.perryquotes.api.quote.booksource;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BookSourceTest {

  @Test
  public void testEquals() {
    var source1 = new BookSource("Source1", "S1");
    var source2 = new BookSource("Source1", "S1");
    assertNotEquals(source1, source2);

    var uuid = UUID.randomUUID();
    source1.setUuid(uuid);
    source2.setUuid(uuid);
    assertEquals(source1, source2);

    source1.setName("NewName");
    assertNotEquals(source1, source2);
    source2.setName("NewName");
    assertEquals(source1, source2);

    source1.setShortcut("SB3");
    assertNotEquals(source1, source2);
    source2.setShortcut("SB3");
    assertEquals(source1, source2);
  }

  @Test
  public void testHashCode() {
    var source1 = new BookSource("Source1", "S1");
    var source2 = new BookSource("Source1", "S1");
    assertNotEquals(source1.hashCode(), source2.hashCode());

    var uuid = UUID.randomUUID();
    source1.setUuid(uuid);
    source2.setUuid(uuid);
    assertEquals(source1.hashCode(), source2.hashCode());

    source1.setName("NewName");
    assertNotEquals(source1.hashCode(), source2.hashCode());
    source2.setName("NewName");
    assertEquals(source1.hashCode(), source2.hashCode());

    source1.setShortcut("SB3");
    assertNotEquals(source1.hashCode(), source2.hashCode());
    source2.setShortcut("SB3");
    assertEquals(source1.hashCode(), source2.hashCode());
  }
}
