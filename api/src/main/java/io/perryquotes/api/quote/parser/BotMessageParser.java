package io.perryquotes.api.quote.parser;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class BotMessageParser {

  private final static Pattern MESSAGE_PATTERN =
    Pattern.compile("(^[a-zA-z0-9.,;()'?!\\s]+)([@]{2})([a-zA-z0-9\\s]{2,100})([@]{2})([A-Z0-9]{3,5})"); //TODO

  public ParserResult parse(final String input) {
    var matcher = MESSAGE_PATTERN.matcher(input);
    if (!matcher.matches()) {
      throw new ParserException("Invalid data format (TEXT@@AUTHORS@@SOURCE)");
    }

    var quoteText = matcher.group(1).trim();
    var author = matcher.group(3).trim();
    var bookSourceShortcut = matcher.group(5).trim();

    return new ParserResult(quoteText, author, bookSourceShortcut);
  }
}
