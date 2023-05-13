package io.samanthatobias.bookcatalogue.service;

import java.util.List;
import java.util.Optional;

import javax.validation.ValidationException;

import io.samanthatobias.bookcatalogue.exception.ResourceNotFoundException;
import io.samanthatobias.bookcatalogue.model.Author;
import io.samanthatobias.bookcatalogue.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

	@Autowired
	private AuthorRepository authorRepository;

	public List<Author> getAllAuthors() {
		return authorRepository.findAll();
	}

	public void saveAuthor(Author author) {
		authorRepository.save(author);
	}

	public void deleteAuthor(Long id) {
		Optional<Author> author = authorRepository.findById(id);
		if (author.isPresent()) {
			if (author.get().getBooks().isEmpty()) {
				authorRepository.deleteById(id);
			} else {
				throw new ValidationException("Cannot delete author with existing books.");
			}
		} else {
			throw new ResourceNotFoundException(id);
		}
	}

}
