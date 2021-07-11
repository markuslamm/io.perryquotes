package io.perryquotes.api.quote.author;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.UUID;

public record AuthorRecord(UUID uuid,
                           @NotEmpty @Pattern(regexp = "^.{2,50}") String name,
                           LocalDateTime createdAt,
                           LocalDateTime lastModifiedAt) {

  public AuthorRecord(String name) {
    this(null, name, null, null);
  }
}
