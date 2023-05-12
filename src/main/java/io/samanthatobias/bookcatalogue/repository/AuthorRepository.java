package io.samanthatobias.bookcatalogue.repository;

import io.samanthatobias.bookcatalogue.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
