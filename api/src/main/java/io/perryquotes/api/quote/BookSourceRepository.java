package io.perryquotes.api.quote;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BookSourceRepository extends JpaRepository<BookSource, UUID> {

  Optional<BookSource> findByUuid(UUID uuid);

  Optional<BookSource> findByShortcut(String shortcut);
}
