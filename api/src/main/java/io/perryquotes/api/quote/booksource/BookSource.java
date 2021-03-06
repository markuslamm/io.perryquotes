package io.perryquotes.api.quote.booksource;

import io.perryquotes.api.base.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

;

@Entity
@Table(name = "book_source")
public class BookSource extends BaseEntity {

  protected BookSource() {}

  public BookSource(final String name, final String shortcut) {
    this.name = name;
    this.shortcut = shortcut;
  }

  @NotEmpty
  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @NotEmpty
  @Pattern(regexp = "^[A-Z0-9]{3,5}")
  @Column(name = "shortcut", nullable = false, unique = true)
  private String shortcut;

  public String getName() {
    return name;
  }

  public BookSource setName(final String name) {
    this.name = name;
    return this;
  }

  public BookSourceRecord toDTO() {
    return new BookSourceRecord(uuid, name, shortcut, createdAt, lastModifiedAt);
  }

  public String getShortcut() {
    return shortcut;
  }

  public BookSource setShortcut(final String shortcut) {
    this.shortcut = shortcut;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof BookSource)) return false;
    var builder = new EqualsBuilder().appendSuper(super.equals(o));
    var that = (BookSource) o;
    return builder
      .append(name, that.name)
      .append(shortcut, that.shortcut)
      .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
      .appendSuper(super.hashCode())
      .append(name)
      .append(shortcut)
      .build();
  }

  @Override
  public void addToString(ToStringBuilder builder) {
    builder.append("name", name);
    builder.append("shortcut", shortcut);
  }
}
