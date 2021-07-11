package io.perryquotes.api.quote.booksource;

import io.perryquotes.api.base.BaseEntityService;
import io.perryquotes.api.error.EntityNotFoundException;
import io.perryquotes.api.error.InvalidDataException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

import static java.lang.String.format;

@Service
@Validated
public class BookSourceService extends BaseEntityService<BookSource> {

  private final BookSourceRepository bookSourceRepository;

  BookSourceService(Logger log, BookSourceRepository bookSourceRepository) {
    super(log);
    this.bookSourceRepository = bookSourceRepository;
  }

  @Transactional(readOnly = true)
  public Optional<BookSource> findByShortcut(final String shortcut) {
    return bookSourceRepository.findByShortcut(shortcut);
  }

  @Transactional
  public BookSource create(@Valid final BookSourceRecord bookSourceData) {
    var name = bookSourceData.name();
    var shortcut = bookSourceData.shortcut();
    if (bookSourceRepository.findByName(name).isPresent()) {
      throw new InvalidDataException(format("Book with name %s already exists", name));
    }
    if (bookSourceRepository.findByShortcut(shortcut).isPresent()) {
      throw new InvalidDataException(format("Book with abbreviation %s already exists", shortcut));
    }
    var created = bookSourceRepository.save(new BookSource(name, shortcut));
    log.debug(format("Created BookSource: %s", created));
    return created;
  }

  @Transactional
  public BookSource update(final UUID uuid, @Valid final BookSourceRecord bookSourceData) {
    var updated = bookSourceRepository.findByUuid(uuid).map(
      bookSource -> bookSourceRepository.save(
        bookSource
          .setName(bookSourceData.name())
          .setShortcut(bookSourceData.shortcut())))
      .orElseThrow(() -> new EntityNotFoundException(BookSource.class, "uuid", uuid.toString()));
    log.debug(format("BookSource %s updated: %s", uuid, updated));
    return updated;
  }

  @Override
  protected BookSourceRepository getRepository() {
    return bookSourceRepository;
  }
}
