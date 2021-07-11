package io.perryquotes.api.quote.author;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class AuthorController {

  private final AuthorService authorService;

  public AuthorController(AuthorService authorService) {
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
}
