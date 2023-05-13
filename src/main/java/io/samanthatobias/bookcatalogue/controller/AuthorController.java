package io.samanthatobias.bookcatalogue.controller;

import java.util.List;

import javax.validation.ValidationException;

import io.samanthatobias.bookcatalogue.model.Author;
import io.samanthatobias.bookcatalogue.service.AuthorService;
import io.samanthatobias.bookcatalogue.validator.AuthorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/authors")
public class AuthorController {

	@Autowired
	private AuthorService authorService;

	@Autowired
	private AuthorValidator authorValidator;

	@GetMapping
	public String viewAuthorPage(Model model) {
		model.addAttribute("authors", authorService.getAll());
		return "authors";
	}

	@GetMapping("/showNewAuthorForm")
	public String showNewAuthorForm(Model model) {
		Author author = new Author();
		model.addAttribute("author", author);
		return "new_author";
	}

	@PostMapping("/saveAuthor")
	public String saveAuthor(@ModelAttribute("author") Author author, Model model) {
		List<String> errors = authorValidator.validate(author);
		if (!errors.isEmpty()) {
			model.addAttribute("errors", errors);
			return "new_author";
		}
		authorService.save(author);
		return "redirect:/authors";
	}

	@GetMapping("/delete/{id}")
	public String deleteAuthor(@PathVariable("id") Long id, RedirectAttributes ra) {
		try {
			authorService.delete(id);
		} catch (ValidationException e) {
			ra.addFlashAttribute("errors", List.of(e.getMessage()));
		}
		return "redirect:/authors";
	}

	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable("id") Long id, Model model) {
		Author author = authorService.getById(id);
		model.addAttribute("author", author);
		return "edit_author";
	}

	@PostMapping("/updateAuthor")
	public String updateAuthor(@ModelAttribute("author") Author author, Model model) {
		List<String> errors = authorValidator.validate(author);
		if (!errors.isEmpty()) {
			model.addAttribute("errors", errors);
			return "edit_author";
		}
		authorService.save(author);
		return "redirect:/authors";
	}

}
