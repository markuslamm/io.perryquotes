package io.perryquotes.api.quote.author;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {

  Optional<Author> findByUuid(UUID uuid);

  Optional<Author> findByName(String name);
}
