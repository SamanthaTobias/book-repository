package io.samanthatobias.bookcatalogue.service;

import io.samanthatobias.bookcatalogue.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

	@Autowired
	BookRepository bookRepository;

	// TODO CRUD endpoints

}
