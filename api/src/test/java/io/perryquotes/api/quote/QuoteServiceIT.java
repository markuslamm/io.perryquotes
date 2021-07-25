package io.perryquotes.api.quote;

import io.perryquotes.api.AbstractIntegrationTest;
import io.perryquotes.api.quote.author.Author;
import io.perryquotes.api.quote.author.AuthorRecord;
import io.perryquotes.api.quote.author.AuthorRepository;
import io.perryquotes.api.quote.booksource.BookSource;
import io.perryquotes.api.quote.booksource.BookSourceRecord;
import io.perryquotes.api.quote.booksource.BookSourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional //necessary for automatic rollback after each test
public class QuoteServiceIT extends AbstractIntegrationTest {

  @Autowired
  private QuoteService quoteService;

  @Autowired
  private QuoteRepository quoteRepository;

  @Autowired
  private AuthorRepository authorRepository;

  @Autowired
  private BookSourceRepository bookSourceRepository;

  @BeforeEach
  public void setUp() {
    var author1 = authorRepository.save(new Author("Author1"));
    var author2 = authorRepository.save(new Author("Author2"));
    var author3 = authorRepository.save(new Author("Author3"));
    var author4 = authorRepository.save(new Author("Author4"));
    var bookSource1 = bookSourceRepository.save(new BookSource("Book source1", "SB111"));
    var bookSource2 = bookSourceRepository.save(new BookSource("Book source2", "SB222"));
    quoteRepository.save(new Quote("quote text1", Set.of(author1), bookSource1));
    quoteRepository.save(new Quote("quote text2", Set.of(author2, author3), bookSource1));
    quoteRepository.save(new Quote("quote text3", Set.of(author4), bookSource2));
  }

  @Test
  public void testValidCreateSingleAuthor() {
    var quoteRecord = new QuoteRecord(
      "Quote text here...",
      Set.of(new AuthorRecord("Author 123")),
      new BookSourceRecord("SB111")
    );

    var result = quoteService.create(quoteRecord);
    assertNotNull(result);
    assertNotNull(result.getUuid());
    assertNotNull(result.getCreatedAt());
    assertNotNull(result.getLastModifiedAt());
    assertTrue(quoteRepository.findByUuid(result.getUuid()).isPresent());
    assertEquals("Book source1", result.getBookSource().getName());
    assertEquals("SB111", result.getBookSource().getShortcut());
    assertEquals(1, result.getAuthors().size());
  }

  @Test
  public void testValidCreateMultiple0Authors() {
    var quoteRecord = new QuoteRecord(
      "Quote text here...",
      Set.of(new AuthorRecord("Author 123"), new AuthorRecord("Author 789")),
      new BookSourceRecord("SB111")
    );

    var result = quoteService.create(quoteRecord);
    assertNotNull(result);
    assertNotNull(result.getUuid());
    assertNotNull(result.getCreatedAt());
    assertNotNull(result.getLastModifiedAt());
    assertTrue(quoteRepository.findByUuid(result.getUuid()).isPresent());
    assertEquals("Book source1", result.getBookSource().getName());
    assertEquals("SB111", result.getBookSource().getShortcut());
    assertEquals(2, result.getAuthors().size());
  }

  @Test
  public void testValidUpdate() {
    //TODO
  }

  @Test
  public void testCommit() {
    //TODO
  }
}
