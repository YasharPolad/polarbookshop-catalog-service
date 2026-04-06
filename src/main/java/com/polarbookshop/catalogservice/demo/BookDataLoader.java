package com.polarbookshop.catalogservice.demo;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Profile("testdata")
public class BookDataLoader {

  private final BookRepository bookRepository;
  private final List<Book> books = Arrays.asList(
      Book.of("0123456789", "War and Peace", "Leo Tolstoy", 20.0),
      Book.of("0123456789123", "Crime and Punishment", "Fyodor Dostoevsky", 18.0)
  );

  public BookDataLoader(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void loadBookTestData(){
    bookRepository.deleteAll();
    bookRepository.saveAll(books);
  }
}
