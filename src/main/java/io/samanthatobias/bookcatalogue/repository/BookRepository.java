package io.samanthatobias.bookcatalogue.repository;

import io.samanthatobias.bookcatalogue.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
