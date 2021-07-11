package io.perryquotes.api.quote.booksource;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BookSourceRepository extends JpaRepository<BookSource, UUID> {

  Optional<BookSource> findByUuid(UUID uuid);

  Optional<BookSource> findByName(String name);

  Optional<BookSource> findByShortcut(String shortcut);
}
