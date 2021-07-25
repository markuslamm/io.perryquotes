package io.perryquotes.api.quote.author;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {

  Optional<Author> findByUuid(final UUID uuid);

  Optional<Author> findByName(final String name);

  Set<Author> findByNameIn(final Set<String> names);

  Set<Author> findByUuidIn(final Set<UUID> uuids);
}
