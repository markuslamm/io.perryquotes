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

;

@Entity
@Table(name = "quote")
public class Quote extends BaseEntity {

  @NotEmpty
  @Column(name = "text", columnDefinition="clob", nullable = false)
  private String text;

  @ManyToOne(optional = false)
  @JoinColumn(name = "author_uuid", nullable = false)
  private Author author;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @Fetch(FetchMode.JOIN)
  @JoinColumn(name = "book_source_uuid", nullable = false)
  private BookSource bookSource;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "quote_state", nullable = false)
  private QuoteState quoteState = QuoteState.REVIEW;

  protected Quote() {}

  Quote(final String text, final Author author, final BookSource bookSource) {
    this.text = text;
    this.author = author;
    this.bookSource = bookSource;
  }

  public String getText() {
    return text;
  }

  public Quote setText(final String text) {
    this.text = text;
    return this;
  }

  public Author getAuthor() {
    return author;
  }

  public Quote setAuthor(final Author author) {
    this.author = author;
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
    builder.append("author", author.getName());
    builder.append("bookSource", bookSource.getName());
    builder.append("quoteState", quoteState);
  }
}
