package io.samanthatobias.bookcatalogue.controller;

import java.util.List;

import io.samanthatobias.bookcatalogue.model.Author;
import io.samanthatobias.bookcatalogue.model.Book;
import io.samanthatobias.bookcatalogue.service.AuthorService;
import io.samanthatobias.bookcatalogue.service.BookService;
import io.samanthatobias.bookcatalogue.validator.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookService bookService;

	@Autowired
	private AuthorService authorService;

	@Autowired
	private BookValidator bookValidator;

	@GetMapping
	public String viewHomePage(Model model) {
		model.addAttribute("books", bookService.getAll());
		return "books";
	}

	@GetMapping("/showNewBookForm")
	public String showNewBookForm(Model model) {
		Book book = new Book();
		model.addAttribute("book", book);
		model.addAttribute("authors", authorService.getAll());
		return "new_book";
	}

	@PostMapping("/saveBook")
	public String saveBook(@ModelAttribute("book") Book book, Model model) {
		List<String> errors = bookValidator.validate(book);
		if (!errors.isEmpty()) {
			model.addAttribute("errors", errors);
			model.addAttribute("authors", authorService.getAll());
			return "new_book";
		}
		bookService.save(book);
		return "redirect:/books";
	}

	@GetMapping("/delete/{id}")
	public String deleteBook(@PathVariable("id") Long id, Model model) {
		bookService.delete(id);
		return "redirect:/books";
	}

	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable("id") Long id, Model model) {
		Book book = bookService.getById(id);
		List<Author> authors = authorService.getAll();
		model.addAttribute("book", book);
		model.addAttribute("authors", authors);
		return "edit_book";
	}

	@PostMapping("/updateBook")
	public String updateBook(@ModelAttribute("book") Book book, Model model) {
		List<String> errors = bookValidator.validate(book);
		if (!errors.isEmpty()) {
			model.addAttribute("errors", errors);
			model.addAttribute("authors", authorService.getAll());
			return "edit_book";
		}
		bookService.save(book);
		return "redirect:/books";
	}

}
