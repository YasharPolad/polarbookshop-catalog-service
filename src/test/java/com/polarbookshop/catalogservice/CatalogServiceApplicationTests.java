package com.polarbookshop.catalogservice;

import com.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("integration")
class CatalogServiceApplicationTests {

  @Autowired
  private WebTestClient webTestClient;

  @Test
  void contextLoads() {
  }

  @Test
  void whenPostRequestThenBookCreated(){
    var isbn = "1231231231";
    var expectedBook = Book.of(isbn, "Title", "Author", 9.90, "Publisher");

    webTestClient
        .post()
        .uri("/books")
        .bodyValue(expectedBook)
        .exchange()
        .expectStatus().isCreated()
        .expectBody(Book.class).value(actualBook -> {
          assertThat(actualBook).isNotNull();
          assertThat(actualBook.isbn())
              .isEqualTo(expectedBook.isbn());
        });

    var updatedBook = Book.of(isbn, "Title-Updated", "Author-Updated", 10.00, "Publisher-Updated");

    webTestClient
        .put()
        .uri("/books/" + isbn)
        .bodyValue(updatedBook)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Book.class).value(actualBook -> {
          assertThat(actualBook).isNotNull();
          assertThat(actualBook.isbn())
              .isEqualTo(expectedBook.isbn());
          assertThat(actualBook.title())
              .isEqualTo("Title-Updated");
          assertThat(actualBook.author())
              .isEqualTo("Author-Updated");
          assertThat(actualBook.price())
              .isEqualTo(10.00);
          assertThat(actualBook.publisher())
              .isEqualTo("Publisher-Updated");
        });
  }
}
