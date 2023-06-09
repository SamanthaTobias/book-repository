package io.samanthatobias.bookcatalogue.service;

import java.util.List;
import java.util.Optional;

import io.samanthatobias.bookcatalogue.exception.ResourceNotFoundException;
import io.samanthatobias.bookcatalogue.model.Book;
import io.samanthatobias.bookcatalogue.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	public List<Book> getAll() {
		return bookRepository.findAll();
	}

	public Book getById(Long id) {
		Optional<Book> book = bookRepository.findById(id);
		if (book.isEmpty()) {
			throw new ResourceNotFoundException(id);
		} else {
			return book.get();
		}
	}

	public void save(Book book) {
		bookRepository.save(book);
	}

	public void delete(Long id) {
		Optional<Book> book = bookRepository.findById(id);
		if (book.isPresent()) {
			bookRepository.deleteById(id);
		} else {
			throw new ResourceNotFoundException(id);
		}
	}

}
