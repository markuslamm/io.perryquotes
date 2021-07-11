package io.perryquotes.api.quote;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.String.format;

@RestController
public class QuoteController {

  private final Logger log;
  private final QuoteService quoteService;

  public QuoteController(final Logger log, final QuoteService quoteService) {
    this.log = log;
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
    log.info(format("Created Quote at location '%s': %s", location, created));
    return ResponseEntity.created(location).body(created.toDTO());
  }

  @PutMapping("/quotes/{uuid}")
  public ResponseEntity<QuoteRecord> update(@PathVariable final UUID uuid,
                                            @RequestBody final QuoteRecord request) {
    var updated = quoteService.update(uuid, request);
    log.info(format("Updated Quote %s: %s", uuid, updated));
    return ResponseEntity.ok(updated.toDTO());
  }

  @PutMapping("/quotes/{uuid}/commit")
  public ResponseEntity<QuoteRecord> commitQuote(@PathVariable final UUID uuid) {
    var committed = quoteService.commitQuote(uuid);
    log.info(format("Committed Quote %s: %s", uuid, committed));
    return ResponseEntity.ok(committed.toDTO());
  }
}
