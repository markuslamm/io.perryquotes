package io.perryquotes.api.quote;

import io.perryquotes.api.base.BaseEntityService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Validated
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

  @Override
  protected BookSourceRepository getRepository() {
    return bookSourceRepository;
  }
}
