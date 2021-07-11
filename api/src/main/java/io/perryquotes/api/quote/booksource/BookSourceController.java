package io.perryquotes.api.quote.booksource;

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
public class BookSourceController {

  private final Logger log;
  private final BookSourceService bookSourceService;

  public BookSourceController(final Logger log, final BookSourceService bookSourceService) {
    this.log = log;
    this.bookSourceService = bookSourceService;
  }

  @GetMapping("/books/{uuid}")
  public ResponseEntity<BookSourceRecord> findByUuid(@PathVariable final UUID uuid) {
    return bookSourceService.findByUuid(uuid)
      .map(bookSource -> ResponseEntity.ok(bookSource.toDTO()))
      .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/books")
  public ResponseEntity<List<BookSourceRecord>> findAll() {
    var result = bookSourceService.findAll()
      .stream()
      .map(BookSource::toDTO)
      .collect(Collectors.toList());
    return ResponseEntity.ok(result);
  }

  @GetMapping("/books/search")
  public ResponseEntity<BookSourceRecord> findByShortcut(@RequestParam(name = "shortcut") final String shortcut) {
    return bookSourceService.findByShortcut(shortcut)
      .map(bookSource -> ResponseEntity.ok(bookSource.toDTO()))
      .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping("/books")
  public ResponseEntity<BookSourceRecord> create(@Valid @RequestBody final BookSourceRecord request,
                                                 final UriComponentsBuilder uriBuilder)
  {
    var created = bookSourceService.create(request);
    var location = uriBuilder.path("/books/{uuid}").buildAndExpand(created.getUuid()).toUri();
    log.info(format("Created BookSource at location '%s': %s", location, created));
    return ResponseEntity.created(location).body(created.toDTO());
  }

  @PutMapping("/books/{uuid}")
  public ResponseEntity<BookSourceRecord> update(@PathVariable final UUID uuid,
                                                 @RequestBody final BookSourceRecord request) {
    var updated = bookSourceService.update(uuid, request);
    log.info(format("Updated BookSource %s: %s", uuid, updated));
    return ResponseEntity.ok(updated.toDTO());
  }
}
