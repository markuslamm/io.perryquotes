package io.perryquotes.api.quote.author;

import io.perryquotes.api.base.BaseEntityService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService extends BaseEntityService<Author> {

  private final AuthorRepository repository;

  public AuthorService(final Logger log, final AuthorRepository repository) {
    super(log);
    this.repository = repository;
  }

  @Transactional(readOnly = true)
  public Optional<Author> findByName(final String name) {
    return repository.findByName(name);
  }

  @Transactional
  public Author create(final AuthorRecord authorData) {
    //TODO
    return null;
  }

  @Transactional
  public Author update(final UUID uuid, final AuthorRecord authorData) {
    //TODO
    return null;
  }

  @Transactional
  public UUID delete(final UUID uuid) {
    //TODO
    return null;
  }

  @Override
  protected AuthorRepository getRepository() {
    return repository;
  }
}
