package io.perryquotes.api.quote.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BotMessageParserTest {

  private BotMessageParser parser;

  @BeforeEach
  public void setUp() {
    parser = new BotMessageParser();
  }

  @Test
  public void testParseValidInput() {
    var validText = "Quote text here";
    var validAuthor = "Author";
    var validBookSource = "SB1";
    var validInput = String.format("%s@@%s@@%s", validText, validAuthor, validBookSource);

    var result = parser.parse(validInput);
    assertEquals(result.text(), validText);
    assertEquals(result.author(), validAuthor);
    assertEquals(result.bookSource(), validBookSource);
  }

  @Test
  public void testParseAuthorTooLong() {
    var validText = "Quote text her ,;?!";
    var validAuthor = "AuthorAuthorAuthorAuthorAuthorAuthorAuthorAuthorAuthorAuthorAuthorAuthorAuthorAuthorAuthorAuthorAuthor";
    var validBookSource = "SB1";
    var invalidInput = String.format("%s@@%s@@%s", validText, validAuthor, validBookSource);

    assertThatExceptionOfType(ParserException.class).isThrownBy(()-> parser.parse(invalidInput));
  }

  @Test
  public void testParseAuthorTooShort() {
    var validText = "Quote text her ,;?!";
    var validAuthor = "A";
    var validBookSource = "SB1";
    var invalidInput = String.format("%s@@%s@@%s", validText, validAuthor, validBookSource);

    assertThatExceptionOfType(ParserException.class).isThrownBy(()-> parser.parse(invalidInput));
  }

  @Test
  public void testParseBoolSourceMissing() {
    var validText = "Quote text her ,;?!";
    var validAuthor = "Author";
    var invalidBookSource = "";
    var invalidInput = String.format("%s@@%s@@%s", validText, validAuthor, invalidBookSource);

    assertThatExceptionOfType(ParserException.class).isThrownBy(()-> parser.parse(invalidInput));
  }

  @Test
  public void testParseBoolSourceTooShort() {
    var validText = "Quote text her ,;?!";
    var validAuthor = "Author";
    var invalidBookSource = "SB";
    var invalidInput = String.format("%s@@%s@@%s", validText, validAuthor, invalidBookSource);

    assertThatExceptionOfType(ParserException.class).isThrownBy(()-> parser.parse(invalidInput));
  }

  @Test
  public void testParseBoolSourceTooLong() {
    var validText = "Quote text her ,;?!";
    var validAuthor = "Author";
    var invalidBookSource = "SB2332432";
    var invalidInput = String.format("%s@@%s@@%s", validText, validAuthor, invalidBookSource);

    assertThatExceptionOfType(ParserException.class).isThrownBy(()-> parser.parse(invalidInput));
  }
}
