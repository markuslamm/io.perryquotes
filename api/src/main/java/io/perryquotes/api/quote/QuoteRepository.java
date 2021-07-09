package io.perryquotes.api.quote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuoteRepository extends JpaRepository<Quote, UUID> {

  Optional<Quote> findByUuid(final UUID uuid);

  @Query("select quote from Quote quote where quote.author.name = :name")
  List<Quote> findByAuthorName(final String name);

  @Query("select quote from Quote quote where quote.bookSource.shortcut = :shortcut")
  List<Quote> findByBookSourceShortcut(final String shortcut);

  List<Quote> findByQuoteState(final QuoteState state);
}
