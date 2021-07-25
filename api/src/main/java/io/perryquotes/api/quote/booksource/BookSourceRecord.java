package io.perryquotes.api.quote.booksource;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.UUID;

public record BookSourceRecord(UUID uuid,
                               String name,
                               @NotEmpty @Pattern(regexp = "^[A-Z0-9]{3,5}") String shortcut,
                               LocalDateTime createdAt,
                               LocalDateTime lastModifiedAt) {

  public BookSourceRecord(String name, String shortcut) {
    this(null, name, shortcut, null, null);
  }

  public BookSourceRecord(String shortcut) {
    this(null, null, shortcut, null, null);
  }

  public BookSourceRecord(UUID uuid, String name, String shortcut) {
    this(uuid, name, shortcut, null, null);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("uuid", uuid)
      .append("name", name)
      .append("shortcut", shortcut)
      .append("createdAt", createdAt)
      .append("lastModifiedAt", lastModifiedAt)
      .build();
  }
}
