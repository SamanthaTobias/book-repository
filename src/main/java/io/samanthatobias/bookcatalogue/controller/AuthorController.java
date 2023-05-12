package io.samanthatobias.bookcatalogue.controller;

import javax.validation.Valid;

import io.samanthatobias.bookcatalogue.model.Author;
import io.samanthatobias.bookcatalogue.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authors")
public class AuthorController {

	@Autowired
	private AuthorService authorService;

	@GetMapping
	public String viewAuthorPage(Model model) {
		model.addAttribute("authors", authorService.getAllAuthors());
		return "authors";
	}

	@GetMapping("/showNewAuthorForm")
	public String showNewAuthorForm(Model model) {
		Author author = new Author();
		model.addAttribute("author", author);
		return "new_author";
	}

	@PostMapping("/saveAuthor")
	public String saveAuthor(@Valid @ModelAttribute("author") Author author, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "add_author";
		}
		authorService.saveAuthor(author);
		return "redirect:/authors";
	}

}
