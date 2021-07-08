package io.perryquotes.api.telegram;

import io.perryquotes.api.config.TelegramProperties;
import io.perryquotes.api.quote.Quote;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@Component
public class TelegramResponseClient {

  private final TelegramProperties telegramProperties;

  public TelegramResponseClient(final TelegramProperties telegramProperties) {
    this.telegramProperties = telegramProperties;
  }

  public boolean sendTelegramResponse(final UUID botMessageUuid, final Optional<Quote> quote) {
    var restTemplate = new RestTemplate();
    var urlTemplate = telegramProperties.getResponseUrl() + "%s";
    var fullUrl = String.format(urlTemplate, createResponseText(botMessageUuid, quote));
    var response = restTemplate.getForEntity(fullUrl, String.class);
    return response.getStatusCode().is2xxSuccessful();
  }

  private static String createResponseText(final UUID messageUuid, final Optional<Quote> quote) {
    if(quote.isPresent()) {
      var successText = """
        Processed BotMessage: %s
        Created quote:
        
        %s
        """;
      return String.format(successText, messageUuid, quote.get());
    } else {
      var errorText = """
        Processing of MotMessage %s FAILED
        """;
      return String.format(errorText, messageUuid);
    }
  }
}
