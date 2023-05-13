package io.samanthatobias.bookcatalogue.service;

import java.util.List;
import java.util.Optional;

import io.samanthatobias.bookcatalogue.model.Book;
import io.samanthatobias.bookcatalogue.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	public void saveBook(Book book) {
		bookRepository.save(book);
	}

	public void deleteBook(Long id) throws Exception {
		Optional<Book> book = bookRepository.findById(id);
		if (book.isPresent()) {
			bookRepository.deleteById(id);
		} else {
			throw new Exception("Book not found.");
		}
	}

}
