package io.perryquotes.api.quote.booksource;

import io.perryquotes.api.base.BaseEntityService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
class BookSourceService extends BaseEntityService<BookSource> {

  private final BookSourceRepository bookSourceRepository;

  BookSourceService(Logger log, BookSourceRepository bookSourceRepository) {
    super(log);
    this.bookSourceRepository = bookSourceRepository;
  }

  @Transactional(readOnly = true)
  Optional<BookSource> findByShortcut(String shortcut) {
    return bookSourceRepository.findByShortcut(shortcut);
  }

  @Transactional
  public BookSource create(final BookSourceRecord bookSourceData) {
    //TODO
    return null;
  }

  @Transactional
  public BookSource update(final UUID uuid, final BookSourceRecord bookSourceData) {
    //TODO
    return null;
  }

  @Override
  protected BookSourceRepository getRepository() {
    return bookSourceRepository;
  }
}
