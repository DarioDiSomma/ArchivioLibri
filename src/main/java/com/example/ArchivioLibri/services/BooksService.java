package com.example.ArchivioLibri.services;

import com.example.ArchivioLibri.entities.Book;
import com.example.ArchivioLibri.exceptions.ResourceAlreadyExistsException;
import com.example.ArchivioLibri.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BooksService {

    private final BookRepository bookRepository;

    public BooksService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(String isbn) {
        return bookRepository.findById(isbn);
    }

    public void deleteById(String isbn) {
        bookRepository.deleteById(isbn);
    }


    public Book create(Book book) {
        if (bookRepository.existsById(book.getIsbn())) {
            throw new ResourceAlreadyExistsException();
        }
        return bookRepository.save(book);
    }

    public Optional<Book> update(Book book) {
        if (!bookRepository.existsById(book.getIsbn())) {
            return Optional.empty();
        }
        return Optional.of(bookRepository.save(book));
    }

}
