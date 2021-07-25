package io.perryquotes.api.processor;

import io.perryquotes.api.base.LoggableComponent;
import io.perryquotes.api.quote.Quote;
import io.perryquotes.api.quote.QuoteRecord;
import io.perryquotes.api.quote.QuoteService;
import io.perryquotes.api.quote.author.AuthorRecord;
import io.perryquotes.api.quote.booksource.BookSourceRecord;
import io.perryquotes.api.quote.parser.BotMessageParser;
import io.perryquotes.api.quote.parser.ParserException;
import io.perryquotes.api.telegram.BotMessage;
import io.perryquotes.api.telegram.BotMessageService;
import io.perryquotes.api.telegram.IncomingBotMessage;
import io.perryquotes.api.telegram.TelegramResponseClient;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Component
@Validated
public class MessageProcessor extends LoggableComponent {

  private final BotMessageService botMessageService;
  private final QuoteService quoteService;
  private final BotMessageParser botMessageParser;
  private final TelegramResponseClient telegramResponseClient;

  public MessageProcessor(final Logger log,
                          final BotMessageService botMessageService,
                          final QuoteService quoteService,
                          final BotMessageParser botMessageParser,
                          final TelegramResponseClient telegramResponseClient) {
    super(log);
    this.botMessageService = botMessageService;
    this.quoteService = quoteService;
    this.botMessageParser = botMessageParser;
    this.telegramResponseClient = telegramResponseClient;
  }

  @Transactional
  public BotMessage processMessage(@Valid final IncomingBotMessage msg) {
    var createdBotMessage = botMessageService.create(msg);
    log.debug("BotMessage created: {}, ", createdBotMessage);

    var createdQuote = createQuoteFromBotMessage(createdBotMessage);
    log.debug("Created quote from bot message;: {}", createdQuote);

    var result = createdQuote != null ?
      botMessageService.setSuccessState(createdBotMessage.getUuid(), createdQuote.getUuid()) :
      botMessageService.setFailureState(createdBotMessage.getUuid());
    log.debug("Set BotMessage state: {}", result.getProcessingState());

    var responseStatus = telegramResponseClient.sendTelegramResponse(createdQuote);
    log.info("Telegram response status: {}", responseStatus);
    return result;
  }

  private Quote createQuoteFromBotMessage(final BotMessage botMessage) {
    try {
      var parsed = botMessageParser.parse(botMessage.getText());
      var authors = parsed.authors().stream()
        .map(AuthorRecord::new)
        .collect(Collectors.toSet());
      var bookSource = new BookSourceRecord(parsed.bookSource());
      return quoteService.create(new QuoteRecord(parsed.text(), authors, bookSource));
    } catch (ParserException ex) {
      log.warn("Unable to parse BotMessage: {}", ex.getMessage());
      return null;
    } catch (Exception ex) {
      log.warn("Unable to create Quote from BotMessage: {}", ex.getMessage());
      return null;
    }
  }
}
