package io.perryquotes.api.quote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuoteRepository extends JpaRepository<Quote, UUID> {

  Optional<Quote> findByUuid(final UUID uuid);

  @Query("select quote from Quote quote join quote.authors a where a.uuid = :uuid")
  List<Quote> findByAuthorUuid(final UUID uuid);

  @Query("select quote from Quote quote where quote.bookSource.uuid = :uuid")
  List<Quote> findByBookSourceUuid(final UUID uuid);

  List<Quote> findByQuoteState(final QuoteState state);
}
