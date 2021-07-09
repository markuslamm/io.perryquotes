package io.perryquotes.api.quote;//package net.perryquotes.api.quote;

import io.perryquotes.api.AbstractIntegrationTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional //necessary for automatic rollback after each test
public class QuoteServiceIT extends AbstractIntegrationTest {

  @Autowired
  private QuoteService quoteService;

  @Autowired
  private EntityManager entityManager;


//
//  @Test
//  public void testCreateFromTelegramSingleAuthor() {
//
//    var telegramMessage = new TelegramMessage(1, 10, Instant.now().getEpochSecond(), "Quote text@@Author@@SB1");
//    quoteService.createFromTelegramMessage(telegramMessage);
//    entityManager.persist(telegramMessage);
//
//    quoteService.createFromTelegramMessage(telegramMessage);
//
//    var quoteResult = entityManager.createQuery("SELECT q FROM Quote q", Quote.class);
//
//
//
//
//
//
//    assertNotNull(quoteResult);
////    var bookSource = new BookSource("SilberBand 1", "SB1");
////    entityManager.persist(bookSource);
////
////    var updateId = 1;
////    var messageId = 10;
////    var messageDateAsLong = Instant.now().getEpochSecond();
////    var validText = "Quote text, here?!";
////    var validAuthor = "Author";
////    var validSource = bookSource.get
////    Abbreviation();
////    var messageTemplate = "%s@@%s@@%s";
////    var messageText = String.format(messageTemplate, validText, validAuthor, validSource);
////
////    var telegramUpdate = new IncomingTelegramUpdate(
////      updateId,
////      new IncomingTelegramUpdate.Message(
////        messageId,
////        messageDateAsLong,
////        messageText));
////
////    var quoteResult = quoteService.createFromTelegram(telegramUpdate);
////    assertNotNull(quoteResult);
////    assertEquals(quoteResult.getTelegramUpdateId(), updateId);
////    assertEquals(quoteResult.getTelegramMessageId(), messageId);
////    assertNotNull(quoteResult.getTelegramDate());
////    assertEquals(quoteResult.getQuoteText(), validText);
////    assertEquals(quoteResult.getAuthors().size(), 1);
////    assertNotNull(quoteResult.getBookSource());
////
////    assertEquals(quoteResult.getBookSource().getAbbreviation(), validSource);
//  }
//
//  @Test
//  public void testCreateFromTelegramMultipleAuthors() {
////    var bookSource = new BookSource("SilberBand 1", "SB1");
////    entityManager.persist(bookSource);
////
////    var updateId = 1;
////    var messageId = 10;
////    var messageDateAsLong = Instant.now().getEpochSecond();
////    var validText = "Quote text, here?!";
////    var validAuthor = "Author1,Author2";
////    var validSource = bookSource.getAbbreviation();
////    var messageTemplate = "%s@@%s@@%s";
////    var messageText = String.format(messageTemplate, validText, validAuthor, validSource);
////
////    var telegramUpdate = new IncomingTelegramUpdate(
////      updateId,
////      new IncomingTelegramUpdate.Message(
////        messageId,
////        messageDateAsLong,
////        messageText));
////
////    var quoteResult = quoteService.createFromTelegram(telegramUpdate);
////    assertNotNull(quoteResult);
////    assertEquals(quoteResult.getTelegramUpdateId(), updateId);
////    assertEquals(quoteResult.getTelegramMessageId(), messageId);
////    assertNotNull(quoteResult.getTelegramDate());
////    assertEquals(quoteResult.getQuoteText(), validText);
////    assertEquals(quoteResult.getAuthors().size(), 2);
////    assertNotNull(quoteResult.getBookSource());
////    assertEquals(quoteResult.getBookSource().getAbbreviation(), validSource);
//  }
//
//  @Test
//  public void testCreateFromTelegramBookSourceNotExists() {
////    var updateId = 1;
////    var messageId = 10;
////    var messageDateAsLong = Instant.now().getEpochSecond();
////    var validText = "Quote text, here?!";
////    var validAuthor = "Author1,Author2";
////    var validSource = "SB23";
////    var messageTemplate = "%s@@%s@@%s";
////    var messageText = String.format(messageTemplate, validText, validAuthor, validSource);
////
////    var telegramUpdate = new IncomingTelegramUpdate(
////      updateId,
////      new IncomingTelegramUpdate.Message(
////        messageId,
////        messageDateAsLong,
////        messageText));
////
////    assertThrows(EntityNotFoundException.class, () -> quoteService.createFromTelegram(telegramUpdate));
//  }
}
