package io.perryquotes.api.telegram;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BotWebhookController.class)
public class TelegramWebhookTest {

  @Autowired
  private MockMvc mockMvc;

//  @MockBean
//  private TelegramMessageService telegramMessageService;

//  @MockBean
//  private ApplicationEventPublisher publisher;

  @MockBean
  private Logger logger;

  @Test
  public void testTelegramWebhook() throws Exception {
    var now = Instant.now().getEpochSecond();
    var rawJson = String.format("{\"update_id\":55,\"message\":{\"message_id\":11,\"date\":%d,\"text\":\"MessageText\"}}", now);
    this.mockMvc.perform(post("/webhook")
      .content(rawJson)
      .contentType("application/json"))
      .andDo(print())
      .andExpect(status().isNoContent());
  }
//
//  @Test
//  public void testTelegramWebhookUpdateIdIsNull() throws Exception {
//    var now = Instant.now().getEpochSecond();
//    var rawJson = String.format("{\"update_id\":null,\"message\":{\"message_id\":11,\"date\":%d,\"text\":\"MessageText\"}}", now);
//    this.mockMvc.perform(post("/webhook")
//      .content(rawJson)
//      .contentType("application/json"))
//      .andDo(print())
//      .andExpect(status().isBadRequest());
//  }
//
//
//  @Test
//  public void testTelegramWebhookUpdateIdIsMissing() throws Exception {
//    var now = Instant.now().getEpochSecond();
//    var rawJson = String.format("{\"message\":{\"message_id\":11,\"date\":%d,\"text\":\"MessageText\"}}", now);
//    this.mockMvc.perform(post("/webhook")
//      .content(rawJson)
//      .contentType("application/json"))
//      .andDo(print())
//      .andExpect(status().isBadRequest());
//  }
//
//  @Test
//  public void testTelegramWebhookMessageIsNull() throws Exception {
//    var rawJson = "{\"update_id\":55,\"message\":null}";
//    this.mockMvc.perform(post("/webhook")
//      .content(rawJson)
//      .contentType("application/json"))
//      .andDo(print())
//      .andExpect(status().isBadRequest());
//  }
//
//  @Test
//  public void testTelegramWebhookMessageIsMissing() throws Exception {
//    var rawJson = "{\"update_id\":55}";
//    this.mockMvc.perform(post("/webhook")
//      .content(rawJson)
//      .contentType("application/json"))
//      .andDo(print())
//      .andExpect(status().isBadRequest());
//  }
//
//  @Test
//  public void testTelegramWebhookMessageIdIsNull() throws Exception {
//    var now = Instant.now().getEpochSecond();
//    var rawJson = String.format("{\"update_id\":55,\"message\":{\"message_id\":null,\"date\":%d,\"text\":\"MessageText\"}}", now);    this.mockMvc.perform(post("/webhook")
//      .content(rawJson)
//      .contentType("application/json"))
//      .andDo(print())
//      .andExpect(status().isBadRequest());
//  }
//
//  @Test
//  public void testTelegramWebhookMessageIdIsMissing() throws Exception {
//    var now = Instant.now().getEpochSecond();
//    var rawJson = String.format("{\"update_id\":55,\"message\":{\"date\":%d,\"text\":\"MessageText\"}}", now);    this.mockMvc.perform(post("/webhook")
//      .content(rawJson)
//      .contentType("application/json"))
//      .andDo(print())
//      .andExpect(status().isBadRequest());
//  }
//
//  @Test
//  public void testTelegramWebhookMessageDateIsNull() throws Exception {
//    var rawJson = "{\"update_id\":55,\"message\":{\"message_id\":11,\"date\":null,\"text\":\"MessageText\"}}";
//    this.mockMvc.perform(post("/webhook")
//      .content(rawJson)
//      .contentType("application/json"))
//      .andDo(print())
//      .andExpect(status().isBadRequest());
//  }
//
//  @Test
//  public void testTelegramWebhookMessageDateIsMissing() throws Exception {
//    var rawJson = "{\"update_id\":55,\"message\":{\"message_id\":11,\"text\":\"MessageText\"}}";
//    this.mockMvc.perform(post("/webhook")
//      .content(rawJson)
//      .contentType("application/json"))
//      .andDo(print())
//      .andExpect(status().isBadRequest());
//  }
//
//  @Test
//  public void testTelegramWebhookTextIsNull() throws Exception {
//    var now = Instant.now().getEpochSecond();
//    var rawJson = String.format("{\"update_id\":55,\"message\":{\"message_id\":11,\"date\":%d,\"text\":null}}", now);
//    this.mockMvc.perform(post("/webhook")
//      .content(rawJson)
//      .contentType("application/json"))
//      .andDo(print())
//      .andExpect(status().isBadRequest());
//  }
//
//  @Test
//  public void testTelegramWebhookTextIsMissing() throws Exception {
//    var now = Instant.now().getEpochSecond();
//    var rawJson = String.format("{\"update_id\":55,\"message\":{\"message_id\":11,\"date\":%d}}", now);
//    this.mockMvc.perform(post("/webhook")
//      .content(rawJson)
//      .contentType("application/json"))
//      .andDo(print())
//      .andExpect(status().isBadRequest());
//  }
}
