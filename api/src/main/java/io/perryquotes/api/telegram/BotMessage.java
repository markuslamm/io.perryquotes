package io.perryquotes.api.telegram;

import io.perryquotes.api.base.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Entity
@Table(name = "bot_message")
public class BotMessage extends BaseEntity {

  @NotNull
  @Column(name = "update_id", nullable = false, updatable = false)
  private long updateId;

  @NotNull
  @Column(name = "message_id", nullable = false, updatable = false)
  private long messageId;

  @NotNull
  @Column(name = "message_date", nullable = false, columnDefinition = "TIMESTAMP", updatable = false)
  private LocalDateTime messageDate;

  @NotEmpty
  @Column(name = "text", columnDefinition="clob", nullable = false)
  private String text;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "processing_status", nullable = false)
  private ProcessingStatus processingStatus= ProcessingStatus.UNPROCESSED;

  @Column(name = "quote_uuid", updatable = false)
  private UUID quoteUuid = null;

  public BotMessage(final long updateId, final long messageId,
                    final long timestamp, final String text) {
    this.updateId = updateId;
    this.messageId = messageId;
    this.messageDate = Instant.ofEpochSecond(timestamp)
      .atZone(ZoneId.of("UTC"))
      .toLocalDateTime();
    this.text = text;
  }

  protected BotMessage() {
  }

  public long getUpdateId() {
    return updateId;
  }

  public BotMessage setUpdateId(final long updateId) {
    this.updateId = updateId;
    return this;
  }

  public long getMessageId() {
    return messageId;
  }

  public BotMessage setMessageId(final long messageId) {
    this.messageId = messageId;
    return this;
  }

  public LocalDateTime getMessageDate() {
    return messageDate;
  }

  public BotMessage setMessageDate(final LocalDateTime incomingDate) {
    this.messageDate = incomingDate;
    return this;
  }

  public String getText() {
    return text;
  }

  public BotMessage setText(final String text) {
    this.text = text;
    return this;
  }

  public ProcessingStatus getProcessingStatus() {
    return processingStatus;
  }

  public BotMessage setProcessingStatus(final ProcessingStatus processingStatus) {
    this.processingStatus = processingStatus;
    return this;
  }

  public UUID getQuoteUuid() {
    return quoteUuid;
  }

  public BotMessage setQuoteUuid(final UUID quoteUuid) {
    this.quoteUuid = quoteUuid;
    return this;
  }

  @Override
  public void addToString(ToStringBuilder builder) {
    builder.append("updateId", updateId);
    builder.append("messageId", messageId);
    builder.append("incomingDate", messageDate);
    builder.append("text", text);
    builder.append("processingStatus", processingStatus);
  }
}
