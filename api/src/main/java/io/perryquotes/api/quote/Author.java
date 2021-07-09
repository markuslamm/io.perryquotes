package io.perryquotes.api.quote;

import io.perryquotes.api.base.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "author")
class Author extends BaseEntity {

  protected Author() {}

  Author(final String name) {
    this.name = name;
  }

  @NotEmpty
  @Pattern(regexp = "^.{2,50}")
  @Column(name = "name", nullable = false, unique = true)
  private String name;

  String getName() {
    return name;
  }

  void setName(final String name) {
    this.name = name;
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
