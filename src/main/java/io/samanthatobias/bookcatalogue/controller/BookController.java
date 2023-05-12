package io.samanthatobias.bookcatalogue.controller;

import io.samanthatobias.bookcatalogue.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
//@RequestMapping("/books")
public class BookController {

	@Autowired
	BookService bookService;

	// TODO CRUD endpoints

}
