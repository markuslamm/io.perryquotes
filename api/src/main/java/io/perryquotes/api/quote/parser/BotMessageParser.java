package io.perryquotes.api.quote.parser;

import io.perryquotes.api.error.InvalidDataException;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BotMessageParser {

  private final Logger log;

  //@[a-zA-z0-9-\s]{2,}@[a-zA-z0-9.,;()'?!\s]+|#[A-Z0-9]{3,5}? works on freeformater.com
  //WTF TODO
  //private final static Pattern MESSAGE_PATTERN = Pattern.compile("@[a-zA-z0-9-\\s]{2,}@[a-zA-z0-9.,;()'?!\\s]+|#[A-Z0-9]{3,5}?");

  public BotMessageParser(Logger log) {
    this.log = log;
  }


  public ParserResult parse(final String input) {
    var authors = parseAuthors(input);
    if(authors.isEmpty()) {
      throw new InvalidDataException("Invalid data: No book source provided");
    }
    log.debug("Parsed authors: {}", authors);

    var bookSource= parseBookSource(input).orElseThrow(() -> new InvalidDataException("Invalid data: No book source provided"));
    log.debug("Parsed book source: {}", bookSource);

    var quoteText = parseQuoteText(input);
    log.debug("Parsed quote text: {}", quoteText);
    return new ParserResult(quoteText, authors, bookSource);
  }

  private Set<String> parseAuthors(final String input) {
    return input.lines()
      .filter(s ->s.startsWith("@"))
      .map(s -> s.trim().substring(1, s.lastIndexOf("@")))
      .collect(Collectors.toSet());
  }

  private Optional<String> parseBookSource(final String input) {
    return input.lines()
      .filter(s ->s.startsWith("#"))
      .map(s -> s.trim().substring(1))
      .filter(s -> !s.isEmpty()).findFirst();
  }

  private String parseQuoteText(final String input) {

    return input.lines()
      .filter(s ->s.startsWith("@"))
      .map(s -> s.trim().substring(1))
      .map(s -> s.replaceAll("@", ":"))
      .collect(Collectors.joining("\n"));
  }
}
