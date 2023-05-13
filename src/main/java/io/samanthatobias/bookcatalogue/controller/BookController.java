package io.samanthatobias.bookcatalogue.controller;

import java.util.List;

import javax.validation.Valid;

import io.samanthatobias.bookcatalogue.model.Author;
import io.samanthatobias.bookcatalogue.model.Book;
import io.samanthatobias.bookcatalogue.service.AuthorService;
import io.samanthatobias.bookcatalogue.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookService bookService;

	@Autowired
	private AuthorService authorService;

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
	public String saveBook(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
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
	public String updateBook(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("authors", authorService.getAll());
			return "edit_book";
		}
		bookService.save(book);
		return "redirect:/books";
	}

}
