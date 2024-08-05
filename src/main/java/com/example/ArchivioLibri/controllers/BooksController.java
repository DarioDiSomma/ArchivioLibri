package com.example.ArchivioLibri.controllers;

import com.example.ArchivioLibri.entities.Book;
import com.example.ArchivioLibri.services.BooksService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;

    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping
    public List<Book> findAll() {
        return booksService.findAll();
    }

    @GetMapping("/{isbn}")
    public Book findByIsbn(@PathVariable String isbn) {
        Optional<Book> optionalBook = booksService.findById(isbn);
        if (optionalBook.isPresent()) {
            return optionalBook.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody @Valid Book book) {
        Book newBook = booksService.create(book);
        return newBook;
    }

    @PutMapping("/{isbn}")
    public Book update(
            @PathVariable @Valid @Size(min = 13, max = 13) String isbn,
            @RequestBody @Valid Book dettagliLibro
    ) {
        if (!Objects.equals(isbn, dettagliLibro.getIsbn())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ISBN doesn't match");
        }
        Optional<Book> updatedBook = booksService.update(dettagliLibro);
        if (updatedBook.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return updatedBook.get();
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<String> deleteByIsbn(@PathVariable String isbn) {
        booksService.deleteById(isbn);
        return new ResponseEntity<>("Libro eliminato", HttpStatus.OK);
    }


}
