package io.perryquotes.api.quote.author;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.UUID;

public record AuthorRecord(UUID uuid,
                           @NotEmpty String name,
                           LocalDateTime createdAt,
                           LocalDateTime lastModifiedAt) {

  public AuthorRecord(String name) {
    this(null, name, null, null);
  }
}
