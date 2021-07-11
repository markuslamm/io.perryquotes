package io.perryquotes.api.quote;

public enum QuoteSearchParam {

  AUTHOR_UUID("authorUuid"), BOOKSOURCE_UUID("bookSourceUuid");

  private final String desc;

  QuoteSearchParam(final String desc) {
    this.desc = desc;
  }
}
