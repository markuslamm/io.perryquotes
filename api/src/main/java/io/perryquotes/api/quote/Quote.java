package io.perryquotes.api.quote;

import io.perryquotes.api.base.BaseEntity;
import io.perryquotes.api.quote.author.Author;
import io.perryquotes.api.quote.booksource.BookSource;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "quote")
public class Quote extends BaseEntity {

  @NotEmpty
  @Column(name = "text", columnDefinition="clob", nullable = false)
  private String text;


  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "quote_author",
    joinColumns = @JoinColumn(name = "quote_uuid"),
    inverseJoinColumns = @JoinColumn(name = "author_uuid")
  )
  private Set<Author> authors = new HashSet<>();

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @Fetch(FetchMode.JOIN)
  @JoinColumn(name = "book_source_uuid", nullable = false)
  private BookSource bookSource;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "quote_state", nullable = false)
  private QuoteState quoteState = QuoteState.REVIEW;

  protected Quote() {}

  Quote(final String text, final Set<Author> authors, final BookSource bookSource) {
    this.text = text;
    this.authors = authors;
    this.bookSource = bookSource;
  }

  public String getText() {
    return text;
  }

  public Quote setText(final String text) {
    this.text = text;
    return this;
  }

  public Set<Author> getAuthors() {
    return authors;
  }

  public Quote setAuthors(final Set<Author> authors) {
    this.authors = authors;
    return this;
  }

  public Quote addAuthor(final Author author) {
    authors.add(author);
    author.getQuotes().add(this);
    return this;
  }

  public Quote addAuthors(final Set<Author> authors) {
    authors.forEach(this::addAuthor);
    return this;
  }

  public Quote removeAuthor(final Author author) {
    authors.remove(author);
    author.getQuotes().remove(this);
    return this;
  }

  public Quote removeAuthors(final Set<Author> authors) {
    authors.forEach(this::removeAuthor);
    return this;
  }

  public BookSource getBookSource() {
    return bookSource;
  }

  public Quote setBookSource(final BookSource bookSource) {
    this.bookSource = bookSource;
    return this;
  }

  public QuoteState getQuoteState() {
    return quoteState;
  }

  public Quote setQuoteState(final QuoteState quoteState) {
    this.quoteState = quoteState;
    return this;
  }

  public boolean isDialogue() {
    return authors.size() > 1;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Quote)) return false;
    var builder = new EqualsBuilder().appendSuper(super.equals(o));
    var that = (Quote) o;
    return builder
      .append(text, that.text)
      .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
      .appendSuper(super.hashCode())
      .append(text)
      .build();
  }

  @Override
  public void addToString(ToStringBuilder builder) {
    builder.append("text", text);
    builder.append("authors", authors.stream().map(Author::getName));
    builder.append("bookSource", bookSource.getName());
    builder.append("quoteState", quoteState);
  }

  public QuoteRecord toDTO() {
    return new QuoteRecord(uuid, text,
      authors.stream().map(Author::toDTO).collect(Collectors.toSet()),
      bookSource.toDTO(), quoteState, createdAt, lastModifiedAt);
  }
}
