package io.perryquotes.api.quote;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class QuoteTest {

  @Test
  public void testEquals() {
    var quote1 = new Quote("Text1", null ,null);
    var quote2 = new Quote("Text1", null, null);

    assertNotEquals(quote1, quote2);

    var uuid = UUID.randomUUID();
    quote1.setUuid(uuid);
    quote2.setUuid(uuid);
    assertEquals(quote1, quote2);

    quote1.setUuid(UUID.randomUUID());
    assertNotEquals(quote1, quote2);

    quote1.setUuid(uuid);
    assertEquals(quote1, quote2);

    quote1.setText("new text");
    assertNotEquals(quote1, quote2);

    quote2.setText(quote1.getText());
    assertEquals(quote1, quote2);
  }

  @Test
  public void testHashCode() {


    var quote1 = new Quote("Text1", null ,null);
    var quote2 = new Quote("Text1", null, null);

    assertNotEquals(quote1.hashCode(), quote2.hashCode());

    var uuid = UUID.randomUUID();
    quote1.setUuid(uuid);
    quote2.setUuid(uuid);
    assertEquals(quote1.hashCode(), quote2.hashCode());

    quote1.setUuid(UUID.randomUUID());
    assertNotEquals(quote1.hashCode(), quote2.hashCode());

    quote1.setUuid(uuid);
    assertEquals(quote1.hashCode(), quote2.hashCode());

    quote1.setText("new text");
    assertNotEquals(quote1.hashCode(), quote2.hashCode());

    quote2.setText(quote1.getText());
    assertEquals(quote1.hashCode(), quote2.hashCode());
  }
}
