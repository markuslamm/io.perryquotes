package io.perryquotes.api.quote;

import io.perryquotes.api.AbstractIntegrationTest;
import io.perryquotes.api.quote.author.Author;
import io.perryquotes.api.quote.booksource.BookSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class QuoteRepositoryIT extends AbstractIntegrationTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private QuoteRepository quoteRepository;

  @BeforeEach
  public void setUp() {

    var author1 = entityManager.persist(new Author("Author1"));
    var author2 = entityManager.persist(new Author("Author2"));
    var author3 = entityManager.persist(new Author("Author3"));
    var author4 = entityManager.persist(new Author("Author4"));

    var bookSource1 = entityManager.persist(new BookSource("Book source1", "BS1"));
    var bookSource2 = entityManager.persist(new BookSource("Book source2", "BS2"));

    entityManager.persist(new Quote("quote text1", Set.of(author1), bookSource1));
    entityManager.persist(new Quote("quote text2", Set.of(author2, author3), bookSource1));
    entityManager.persist(new Quote("quote text3", Set.of(author4), bookSource2));

    entityManager.flush();
  }

  @Test
  public void testFindByUuid() {
    getExistingQuotes().forEach(existing -> {
      var result = quoteRepository.findByUuid(existing.getUuid());
      assertTrue(result.isPresent());
      assertEquals(existing, result.get());
    });
  }

  @Test
  public void testFindByUuidNotFound() {
    assertTrue(quoteRepository.findByUuid(UUID.randomUUID()).isEmpty());
  }

  @Test
  public void testFindByAuthorUuid() {
    getExistingQuotes().forEach(quote -> {
      var authorUuids = quote.getAuthors().stream().map(Author::getUuid).collect(Collectors.toSet());
      authorUuids.forEach(uuid -> {
        assertTrue(quoteRepository.findByAuthorUuid(uuid).contains(quote));
      });
    });
  }

  @Test
  public void testFindByAuthorUuidNotFound() {
    assertTrue(quoteRepository.findByAuthorUuid(UUID.randomUUID()).isEmpty());
  }

  @Test
  public void testFindByBookSourceUuid() {
    getExistingQuotes().forEach(quote -> {
      assertTrue(quoteRepository.findByBookSourceUuid(quote.getBookSource().getUuid()).contains(quote));
    });
  }

  @Test
  public void testFindByBookSourceUuidNotFound() {
    assertTrue(quoteRepository.findByBookSourceUuid(UUID.randomUUID()).isEmpty());
  }

  @Test
  public void testFindByQuoteState() {
    assertEquals(getExistingQuotes().size(), quoteRepository.findByQuoteState(QuoteState.REVIEW).size()); //Default state
  }

  @Test
  public void testFindByQuoteStateNotFound() {
    assertTrue(quoteRepository.findByQuoteState(QuoteState.COMMITTED).isEmpty());

  }

  private List<Quote> getExistingQuotes() {
    return entityManager.getEntityManager().createQuery("SELECT q FROM Quote q", Quote.class).getResultList();
  }
}
