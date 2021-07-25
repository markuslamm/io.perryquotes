package io.perryquotes.api.quote;

import io.perryquotes.api.base.BaseEntityService;
import io.perryquotes.api.error.EntityNotFoundException;
import io.perryquotes.api.quote.author.Author;
import io.perryquotes.api.quote.author.AuthorService;
import io.perryquotes.api.quote.booksource.BookSource;
import io.perryquotes.api.quote.booksource.BookSourceService;
import org.slf4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Validated
public class QuoteService extends BaseEntityService<Quote> {

  private final QuoteRepository quoteRepository;
  private final AuthorService authorService;
  private final BookSourceService bookSourceService;

  public QuoteService(final Logger log,
                      final QuoteRepository quoteRepository,
                      final AuthorService authorService,
                      final BookSourceService bookSourceService) {
    super(log);
    this.quoteRepository = quoteRepository;
    this.authorService = authorService;
    this.bookSourceService = bookSourceService;
  }

  private record AuthorsBookSourceTuple(Set<Author> authors, BookSource bookSource) {
  }

  @Transactional
  public Quote create(@Valid final QuoteRecord quoteData) {
    var authorAndBookSourceTuple = getAuthorsAndBookSource(quoteData);
    var created = quoteRepository.saveAndFlush(
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

  private AuthorsBookSourceTuple getAuthorsAndBookSource(final QuoteRecord quoteData) {
    var authors = quoteData.authors()
      .stream()
      .map(authorRecord ->
        authorService.findByName(authorRecord.name())
          .orElse(authorService.create(authorRecord)))
      .collect(Collectors.toSet());

    var bookSource = bookSourceService.findByShortcut(quoteData.bookSource().shortcut())
      .orElseThrow(() -> new EntityNotFoundException(
        BookSource.class,
        "shortcut",
        quoteData.bookSource().shortcut()));

    return new AuthorsBookSourceTuple(authors, bookSource);
  }

  @Override
  protected JpaRepository<Quote, UUID> getRepository() {
    return quoteRepository;
  }
}
