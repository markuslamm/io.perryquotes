package io.perryquotes.api.quote;

import io.perryquotes.api.base.BaseEntityService;
import io.perryquotes.api.error.EntityNotFoundException;
import io.perryquotes.api.error.InvalidDataException;
import io.perryquotes.api.events.BotMessageCreatedEvent;
import io.perryquotes.api.events.BotMessageProcessedEvent;
import io.perryquotes.api.quote.author.Author;
import io.perryquotes.api.quote.author.AuthorRecord;
import io.perryquotes.api.quote.author.AuthorService;
import io.perryquotes.api.quote.booksource.BookSource;
import io.perryquotes.api.quote.booksource.BookSourceService;
import io.perryquotes.api.quote.parser.BotMessageParser;
import io.perryquotes.api.quote.parser.ParserException;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Validated
public class QuoteService extends BaseEntityService<Quote> {

  private final QuoteRepository quoteRepository;
  private final AuthorService authorService;
  private final BookSourceService bookSourceService;
  private final BotMessageParser botMessageParser;
  private final ApplicationEventPublisher publisher;

  public QuoteService(final Logger log,
                      final QuoteRepository quoteRepository,
                      final AuthorService authorService,
                      final BookSourceService bookSourceService,
                      final BotMessageParser botMessageParser,
                      final ApplicationEventPublisher publisher) {
    super(log);
    this.quoteRepository = quoteRepository;
    this.authorService = authorService;
    this.bookSourceService = bookSourceService;
    this.botMessageParser = botMessageParser;
    this.publisher = publisher;
  }

  private record AuthorsBookSourceTuple(Set<Author> authors, BookSource bookSource) {
  }

  @Transactional
  Quote handleBotMessage(final BotMessageCreatedEvent event) {
    log.debug("Handle BotMessageCreatedEvent: {}", event);
    var quoteResult = createQuoteFromBotMessage(event.getRawBotMessage().getText());
    log.debug("Created Quote from BotMessageCreatedEvent: {}", quoteResult);
    publisher.publishEvent(new BotMessageProcessedEvent(this, event.getRawBotMessage().getUuid(), Optional.ofNullable(quoteResult)));
    return quoteResult;
  }

  @Transactional
  public Quote create(@Valid final QuoteRecord quoteData) {

    var authorAndBookSourceTuple = getAuthorsAndBookSource(quoteData);
    var created = quoteRepository.save(
      new Quote(quoteData.text(), authorAndBookSourceTuple.authors(), authorAndBookSourceTuple.bookSource()));
    log.debug(String.format("Created Quote: %s", created));
    return created;
  }

  @Transactional
  public Quote update(final UUID uuid, final QuoteRecord quoteData) {
    var authorAndBookSourceTuple = getAuthorsAndBookSource(quoteData);

    var updated = quoteRepository.findByUuid(uuid).map(
      quote -> quoteRepository.save(quote
        .setText(quoteData.text())
        .addAuthors(authorAndBookSourceTuple.authors())
        .setBookSource(authorAndBookSourceTuple.bookSource())
        .setQuoteState(quoteData.state())))
      .orElseThrow(() -> new EntityNotFoundException(Quote.class, "uuid", uuid.toString()));
    log.debug(String.format("Quote %s updated: %s", uuid, updated));

    return updated;
  }

  @Transactional
  Quote commitQuote(final UUID quoteUuid) {
    return quoteRepository.findByUuid(quoteUuid).map(q -> quoteRepository.save(
      q.setQuoteState(QuoteState.COMMITTED)))
      .orElseThrow(() -> new EntityNotFoundException(Quote.class, "uuid", quoteUuid.toString()));
  }

  @Transactional(readOnly = true)
  List<Quote> findByAuthorUuid(final UUID authorUuid) {
    return quoteRepository.findByAuthorUuid(authorUuid);
  }

  @Transactional(readOnly = true)
  List<Quote> findByBookSourceUuid(final UUID bookSourceUuid) {
    return quoteRepository.findByBookSourceUuid(bookSourceUuid);
  }

  private Quote createQuoteFromBotMessage(final String messageText) {
    try {
      var parsed = botMessageParser.parse(messageText);
      var created = quoteRepository.saveAndFlush(
        new Quote(
          parsed.text(),
          getAuthor(parsed.authors()),
          getBookSource(parsed.bookSource())));
      log.debug("Created quote from Telegram update: {}", created);
      return created;
    } catch (ParserException ex) {
      log.warn("Unable to create Quote: {}", ex.getMessage());
      return null;
    }
  }

  private Set<Author> getAuthor(final Set<String> parsedAuthors) {
    return parsedAuthors.stream()
      .map(a -> authorService.findByName(a).orElse(authorService.create(new AuthorRecord(a))))
      .collect(Collectors.toSet());


  }

  private BookSource getBookSource(String parsedBookSource) {
    return bookSourceService.findByShortcut(parsedBookSource)
      .orElseThrow(() ->
        new InvalidDataException(String.format("No BookSource available for shortcut %s", parsedBookSource)));
  }

  private AuthorsBookSourceTuple getAuthorsAndBookSource(final QuoteRecord quoteData) {
    var authors = authorService.findByUuids(quoteData.authorUuids());
    if (authors.isEmpty()) {
      throw new EntityNotFoundException(Author.class, "uuid", quoteData.authorUuids());
    }
    var bookSource = bookSourceService.findByUuid(quoteData.bookSourceUuid())
      .orElseThrow(() -> new EntityNotFoundException(BookSource.class, "uuid", quoteData.bookSourceUuid().toString()));
    return new AuthorsBookSourceTuple(authors, bookSource);
  }

  @Override
  protected JpaRepository<Quote, UUID> getRepository() {
    return quoteRepository;
  }
}
