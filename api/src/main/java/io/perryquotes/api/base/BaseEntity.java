package io.perryquotes.api.base;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected UUID uuid;

  @CreatedDate
  @Column(name = "created_at", nullable = false)
  protected LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "last_modified_at", nullable = false)
  protected LocalDateTime lastModifiedAt;

  @Version
  protected long version;

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(final UUID uuid) {
    this.uuid = uuid;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(final LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getLastModifiedAt() {
    return lastModifiedAt;
  }

  public void setLastModifiedAt(final LocalDateTime lastModifiedAt) {
    this.lastModifiedAt = lastModifiedAt;
  }

  public long getVersion() {
    return version;
  }

  public void setVersion(final long version) {
    this.version = version;
  }

  @PrePersist
  public void onPrePersist() {
    var now = LocalDateTime.now();
    this.createdAt = now;
    this.lastModifiedAt = now;
  }

  @PreUpdate
  public void onPreUpdate() {
    this.lastModifiedAt = LocalDateTime.now();
  }

  public abstract void addToString(ToStringBuilder builder);

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    var that = (BaseEntity) o;
    if(uuid == null && that.uuid == null) {
      return System.identityHashCode(this) == System.identityHashCode(that);
    } else {
      var builder = new EqualsBuilder();
      builder.append(uuid, that.uuid);
      return builder.isEquals();
    }
  }

  @Override
  public int hashCode() {
    return (uuid == null) ?
      System.identityHashCode(this) :
      new HashCodeBuilder().append(uuid).build();
  }

  @Override
  public String toString() {
    ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
    builder.append("uuid", uuid);
    addToString(builder);
    return builder.build();
  }
}
