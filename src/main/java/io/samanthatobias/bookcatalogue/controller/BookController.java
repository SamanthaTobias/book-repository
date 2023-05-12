package io.samanthatobias.bookcatalogue.controller;

import javax.validation.Valid;

import io.samanthatobias.bookcatalogue.model.Book;
import io.samanthatobias.bookcatalogue.service.AuthorService;
import io.samanthatobias.bookcatalogue.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookService bookService;

	@Autowired
	private AuthorService authorService;

	@GetMapping
	public String viewHomePage(Model model) {
		model.addAttribute("books", bookService.getAllBooks());
		return "books";
	}

	@GetMapping("/showNewBookForm")
	public String showNewBookForm(Model model) {
		Book book = new Book();
		model.addAttribute("book", book);
		model.addAttribute("authors", authorService.getAllAuthors());
		return "new_book";
	}

	@PostMapping("/saveBook")
	public String saveBook(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("authors", authorService.getAllAuthors());
			return "new_book";
		}
		bookService.saveBook(book);
		return "redirect:/books";
	}

}
