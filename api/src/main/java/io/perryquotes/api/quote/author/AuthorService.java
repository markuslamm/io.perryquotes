package io.perryquotes.api.quote.author;

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
public class AuthorService extends BaseEntityService<Author> {

  private final AuthorRepository repository;

  public AuthorService(final Logger log, final AuthorRepository repository) {
    super(log);
    this.repository = repository;
  }

  @Transactional(readOnly = true)
  public Optional<Author> findByName(String name) {
    return repository.findByName(name);
  }

  @Transactional
  public Author create(@Valid final AuthorRecord request) {
    var name = request.name();
    if (repository.findByName(name).isPresent()) {
      throw new InvalidDataException(format("Author with name %s already exists", name));
    }
    var created = repository.save(new Author(name));
    log.debug(format("Created Author: %s", created));
    return created;
  }

  @Transactional
  public Author update(UUID uuid, @Valid final AuthorRecord authorRecord) {
    var updated = repository.findByUuid(uuid)
      .map(author -> repository.save(author.setName(authorRecord.name())))
      .orElseThrow(() -> new EntityNotFoundException(Author.class, "uuid", uuid.toString()));
    log.debug(format("Author %s updated: %s", uuid, updated));
    return updated;
  }

  @Override
  protected AuthorRepository getRepository() {
    return repository;
  }
}
