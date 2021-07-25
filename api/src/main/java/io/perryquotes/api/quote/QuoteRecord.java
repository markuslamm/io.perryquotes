package io.perryquotes.api.quote;

import io.perryquotes.api.quote.author.AuthorRecord;
import io.perryquotes.api.quote.booksource.BookSourceRecord;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record QuoteRecord(UUID uuid,
                          @NotEmpty String text,
                          @NotNull Set<AuthorRecord> authors,
                          @NotNull BookSourceRecord bookSource,
                          QuoteState state,
                          LocalDateTime createdAt,
                          LocalDateTime lastModifiedAt
                          ) {

  public QuoteRecord(String text, Set<AuthorRecord> authors, BookSourceRecord bookSource) {
    this(null, text, authors, bookSource, null, null, null);
  }

  public QuoteRecord(UUID uuid, String text, Set<AuthorRecord> authors, BookSourceRecord bookSource) {
    this(uuid, text, authors, bookSource, null, null, null);
  }
}
