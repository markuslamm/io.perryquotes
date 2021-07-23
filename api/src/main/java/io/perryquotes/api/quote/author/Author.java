package io.perryquotes.api.quote.author;

import io.perryquotes.api.base.BaseEntity;
import io.perryquotes.api.quote.Quote;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "author")
public class Author extends BaseEntity {

  protected Author() {}

  public Author(final String name) {
    this.name = name;
  }

  @NotEmpty
  @Pattern(regexp = "^.{2,50}")
  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @ManyToMany(mappedBy = "authors")
  private Set<Quote> quotes = new HashSet<>();

  public String getName() {
    return name;
  }

  public Author setName(final String name) {
    this.name = name;
    return this;
  }

  public Set<Quote> getQuotes() {
    return quotes;
  }

  public Author setQuotes(final Set<Quote> quotes) {
    this.quotes = quotes;
    return this;
  }

  public AuthorRecord toDTO() {
    return new AuthorRecord(uuid, name, createdAt, lastModifiedAt);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Author)) return false;
    var builder = new EqualsBuilder().appendSuper(super.equals(o));
    var that = (Author) o;
    return builder
      .append(name, that.name)
      .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
      .appendSuper(super.hashCode())
      .append(name)
      .build();
  }

  @Override
  public void addToString(ToStringBuilder builder) {
    builder.append("name", name);
  }
}
