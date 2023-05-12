package io.samanthatobias.bookcatalogue.service;

import java.util.List;

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

}
