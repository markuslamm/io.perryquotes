package io.perryquotes.api.quote;

import io.perryquotes.api.base.LoggableComponent;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class QuoteController extends LoggableComponent {

  private final QuoteService quoteService;

  public QuoteController(final Logger log, final QuoteService quoteService) {
    super(log);
    this.quoteService = quoteService;
  }

  @GetMapping("/quotes/{uuid}")
  public ResponseEntity<QuoteRecord> findByUuid(@PathVariable final UUID uuid) {
    return quoteService.findByUuid(uuid)
      .map(quote -> ResponseEntity.ok(quote.toDTO()))
      .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/quotes")
  public ResponseEntity<List<QuoteRecord>> findAll() {
    var result = quoteService.findAll()
      .stream()
      .map(Quote::toDTO)
      .collect(Collectors.toList());
    return ResponseEntity.ok(result);
  }

  //TODO single search endpoint

  @GetMapping("/quotes/author/{authorUuid}")
  public ResponseEntity<List<QuoteRecord>> findByAuthorUuid(@PathVariable final UUID authorUuid) {
    var result = quoteService.findByAuthorUuid(authorUuid)
      .stream()
      .map(Quote::toDTO).collect(Collectors.toList());
    return ResponseEntity.ok(result);
  }

  @GetMapping("/quotes/book/{bookSourceUuid}")
  public ResponseEntity<List<QuoteRecord>> findByBookSourceUuid(@PathVariable final UUID bookSourceUuid) {
    var result = quoteService.findByBookSourceUuid(bookSourceUuid)
      .stream()
      .map(Quote::toDTO).collect(Collectors.toList());
    return ResponseEntity.ok(result);
  }

  @PostMapping("/quotes")
  public ResponseEntity<QuoteRecord> create(@Valid @RequestBody final QuoteRecord request,
                                            final UriComponentsBuilder uriBuilder) {
    var created = quoteService.create(request);
    var location = uriBuilder.path("/quotes/{uuid}").buildAndExpand(created.getUuid()).toUri();
    log.info("Created Quote at location '{}': {}", location, created);
    return ResponseEntity.created(location).body(created.toDTO());
  }

  @PutMapping("/quotes/{uuid}")
  public ResponseEntity<QuoteRecord> update(@PathVariable final UUID uuid,
                                            @RequestBody final QuoteRecord request) {
    var updated = quoteService.update(uuid, request);
    log.info("Updated Quote {}: {}", uuid, updated);
    return ResponseEntity.ok(updated.toDTO());
  }

  @PutMapping("/quotes/{uuid}/commit")
  public ResponseEntity<QuoteRecord> commitQuote(@PathVariable final UUID uuid) {
    var committed = quoteService.commitQuote(uuid);
    log.info("Committed Quote {}: {}", uuid, committed);
    return ResponseEntity.ok(committed.toDTO());
  }
}
