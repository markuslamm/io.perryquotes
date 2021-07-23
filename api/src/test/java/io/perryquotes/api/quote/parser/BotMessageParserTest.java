package io.perryquotes.api.quote.parser;

import io.perryquotes.api.error.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BotMessageParserTest {

  private BotMessageParser parser;

  @BeforeEach
  public void setUp() {
    parser = new BotMessageParser(LoggerFactory.getLogger(BotMessageParser.class));
  }

  @Test
  public void testValidInputSingleAuthor() {
    var msg = """
      @Perry@ Hello Thora.
      #SB1
      """;

    var result = parser.parse(msg);
    assertEquals(1, result.authors().size());
    assertEquals("SB1", result.bookSource());
  }

  @Test
  public void testValidInputMultipleAuthors() {
    var msg = """
      @Perry@ Hello Thora.
      @Thora@Hello Perry.
      @Gucky@ Hello Thora.
      @Atlan@ Hello Thora.
      @Perry@Hello Atlan.
      #SB1
      """;

    var result = parser.parse(msg);
    assertEquals( 4, result.authors().size());
    assertEquals("SB1", result.bookSource());
  }

  @Test
  public void testMissingAuthors() {
    var msg = """
      Perry@ Hello Thora.
      Thora@Hello Perry.
      Gucky@ Hello Thora.
      Atlan@ Hello Thora.
      Perry@Hello Atlan.
      #SB1
      """;

    assertThrows(InvalidDataException.class,
      () -> parser.parse(msg));
  }

  @Test
  public void testMissingBookSource() {
    var msg = """
      @Perry@ Hello Thora.
      @Thora@Hello Perry.
      @Gucky@ Hello Thora.
      @Atlan@ Hello Thora.
      @Perry@Hello Atlan.
      """;

    assertThrows(InvalidDataException.class,
      () -> parser.parse(msg));
  }

  @Test
  public void testEmptyBookSource() {
    var msg = """
      @Perry@ Hello Thora.
      @Thora@Hello Perry.
      @Gucky@ Hello Thora.
      @Atlan@ Hello Thora.
      @Perry@Hello Atlan.
      #
      """;

    assertThrows(InvalidDataException.class,
      () -> parser.parse(msg));
  }
}
