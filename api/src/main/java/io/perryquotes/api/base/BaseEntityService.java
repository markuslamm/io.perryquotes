package io.perryquotes.api.base;

import org.slf4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class BaseEntityService<T extends BaseEntity> {

  protected final Logger log;

  public BaseEntityService(final Logger log) {
    this.log = log;
  }

  protected abstract JpaRepository<T, UUID> getRepository();

  public Optional<T> findByUuid(final UUID uuid) {
    return getRepository().findById(uuid);
  }

  public List<T> findAll() {
    return getRepository().findAll();
  }
}
