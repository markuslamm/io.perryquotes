package io.perryquotes.api.quote;

import io.perryquotes.api.base.BaseEntityService;
import io.perryquotes.api.events.BotMessageCreatedEvent;
import io.perryquotes.api.events.BotMessageProcessedEvent;
import io.perryquotes.api.quote.parser.BotMessageParser;
import io.perryquotes.api.quote.parser.ParserException;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class QuoteService extends BaseEntityService<Quote> {

  private final QuoteRepository quoteRepository;
  private final AuthorRepository authorRepository;
  private final BookSourceRepository bookSourceRepository;
  private final BotMessageParser botMessageParser;
  private final ApplicationEventPublisher publisher;

  public QuoteService(final Logger log,
                      final QuoteRepository quoteRepository,
                      final AuthorRepository authorRepository,
                      final BookSourceRepository bookSourceRepository,
                      final BotMessageParser botMessageParser,
                      final ApplicationEventPublisher publisher) {
    super(log);
    this.quoteRepository = quoteRepository;
    this.authorRepository = authorRepository;
    this.bookSourceRepository = bookSourceRepository;
    this.botMessageParser = botMessageParser;
    this.publisher = publisher;
  }

  Quote handleBotMessage(final BotMessageCreatedEvent event) {
    log.debug("Handle BotMessageCreatedEvent: {}", event);
    var quoteResult = createQuoteFromBotMessage(event.getRawBotMessage().getText());
    log.debug("Created Quote from BotMessageCreatedEvent: {}", quoteResult);
    publisher.publishEvent(new BotMessageProcessedEvent(this, event.getRawBotMessage().getUuid(), Optional.ofNullable(quoteResult)));
    return quoteResult;
  }

  private Quote createQuoteFromBotMessage(final String messageText) {
    try {
      var parsed = botMessageParser.parse(messageText);
      var created = quoteRepository.saveAndFlush(
        new Quote(
          parsed.text(),
          getAuthor(parsed.author()),
          getBookSource(parsed.bookSource())));
      log.debug("Created quote from Telegram update: {}", created);
      return created;
    } catch(ParserException ex) {
      log.warn("Unable to create Quote: {}", ex.getMessage());
      return null;
    }
  }

  private Author getAuthor(String parsedAuthor) {
    return authorRepository.findByName(parsedAuthor).orElse(authorRepository.save(new Author(parsedAuthor)));
  }

  private BookSource getBookSource(String parsedBookSource) {
    return bookSourceRepository.findByShortcut(parsedBookSource)
      .orElseThrow(() -> new RuntimeException(String.format("No BookSource available for shortcut %s", parsedBookSource)));
  }


  @Override
  protected JpaRepository<Quote, UUID> getRepository() {
    return quoteRepository;
  }
}
