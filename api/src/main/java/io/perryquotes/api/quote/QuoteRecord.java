package io.perryquotes.api.quote;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record QuoteRecord(UUID uuid,
                          @NotEmpty String text,
                          @NotNull Set<UUID> authorUuids,
                          @NotNull UUID bookSourceUuid,
                          QuoteState state,
                          LocalDateTime createdAt,
                          LocalDateTime lastModifiedAt
                          ) {

  public QuoteRecord(String text, Set<UUID> authors, UUID bookSource) {
    this(null, text, authors, bookSource, null, null, null);
  }

  public QuoteRecord(UUID uuid, String text, Set<UUID> authors, UUID bookSource) {
    this(uuid, text, authors, bookSource, null, null, null);
  }
}
