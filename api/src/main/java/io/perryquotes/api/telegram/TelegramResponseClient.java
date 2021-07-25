package io.perryquotes.api.telegram;

import io.perryquotes.api.base.LoggableComponent;
import io.perryquotes.api.config.TelegramProperties;
import io.perryquotes.api.quote.Quote;
import io.perryquotes.api.quote.author.Author;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TelegramResponseClient extends LoggableComponent {

  private final TelegramProperties telegramProperties;

  public TelegramResponseClient(final Logger log, final TelegramProperties telegramProperties) {
    super(log);
    this.telegramProperties = telegramProperties;
  }

  public boolean sendTelegramResponse(final Quote quote) {
    var restTemplate = new RestTemplate();
    var urlTemplate = telegramProperties.getResponseUrl() + "%s";
    var fullUrl = String.format(
      urlTemplate,
      createResponseText(quote));
    log.debug("Sending telegram response: {}", fullUrl);
    var response = restTemplate.getForEntity(fullUrl, String.class);
    return response.getStatusCode().is2xxSuccessful();
  }

  private String createResponseText(final Quote quote) {
    var quoteResult = Optional.ofNullable(quote);
    if (quoteResult.isPresent()) {
      var successText = """
        Created quote:
        %s
        Author(s):%s
        BookSource:%s(%s)
        """;
      return String.format(successText,
        quote.getText(),
        StringUtils.join(quote.getAuthors().stream().map(Author::getName).collect(Collectors.toSet()), ","),
        quote.getBookSource().getName(),
        quote.getBookSource().getShortcut());

    } else {
      return "Processing of BotMessage FAILED.";
    }
  }
}
