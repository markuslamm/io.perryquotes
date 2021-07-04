package io.perryquotes.api.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record IncomingBotMessage(@NotNull @JsonProperty("update_id") Long updateId,
                                 @Valid @NotNull Message message) {

  public static record Message(@NotNull @JsonProperty("message_id") Long messageId,
                                @NotNull @JsonProperty("date") Long messageDate,
                                @NotEmpty String text) {
  }
}
