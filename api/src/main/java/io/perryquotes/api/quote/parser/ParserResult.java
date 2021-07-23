package io.perryquotes.api.quote.parser;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Set;

public record ParserResult(String text, Set<String> authors, String bookSource) {

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("text", text)
      .append("authors", authors)
      .append("bookSource", bookSource)
      .build();
  }
}
