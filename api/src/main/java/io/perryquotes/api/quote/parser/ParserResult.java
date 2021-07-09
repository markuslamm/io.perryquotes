package io.perryquotes.api.quote.parser;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record ParserResult(String text, String author, String bookSource) {

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("text", text)
      .append("author", author)
      .append("bookSource", bookSource)
      .build();
  }
}
