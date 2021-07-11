package io.perryquotes.api.quote;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public record QuoteRecord(UUID uuid,
                          @NotEmpty String text,
                          @NotNull UUID authorUuid,
                          @NotNull UUID bookSourceUuid,
                          QuoteState state,
                          LocalDateTime createdAt,
                          LocalDateTime lastModifiedAt
                          ) {

  public QuoteRecord(String text, UUID author, UUID bookSource) {
    this(null, text, author, bookSource, null, null, null);
  }

  public QuoteRecord(UUID uuid, String text, UUID author, UUID bookSource) {
    this(uuid, text, author, bookSource, null, null, null);
  }
}
