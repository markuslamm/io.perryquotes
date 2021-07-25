package io.perryquotes.api.quote.author;

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
public class AuthorController extends LoggableComponent {

  private final AuthorService authorService;

  public AuthorController(final Logger log,
                          final AuthorService authorService) {
    super(log);
    this.authorService = authorService;
  }

  @GetMapping("/authors/{uuid}")
  public ResponseEntity<AuthorRecord> findByUuid(@PathVariable final UUID uuid) {
    return authorService.findByUuid(uuid)
      .map(author -> ResponseEntity.ok(author.toDTO()))
      .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/authors/search")
  public ResponseEntity<AuthorRecord> findByName(@RequestParam final String name) {
    return authorService.findByName(name)
      .map(author -> ResponseEntity.ok(author.toDTO()))
      .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/authors")
  public ResponseEntity<List<AuthorRecord>> findAll() {
    var result = authorService.findAll()
      .stream()
      .map(Author::toDTO)
      .collect(Collectors.toList());
    return ResponseEntity.ok(result);
  }

  @PostMapping("/authors")
  public ResponseEntity<AuthorRecord> create(@Valid @RequestBody final AuthorRecord request,
                                                 final UriComponentsBuilder uriBuilder)
  {
    var created = authorService.create(request);
    var location = uriBuilder.path("/authors/{uuid}").buildAndExpand(created.getUuid()).toUri();
    log.info("Created Author at location '{}': {}}", location, created);
    return ResponseEntity.created(location).body(created.toDTO());
  }

  @PutMapping("/authors/{uuid}")
  public ResponseEntity<AuthorRecord> update(@PathVariable final UUID uuid,
                                                 @RequestBody final AuthorRecord request) {
    var updated = authorService.update(uuid, request);
    log.info("Updated Author {}: {}", uuid, updated);
    return ResponseEntity.ok(updated.toDTO());
  }
}
