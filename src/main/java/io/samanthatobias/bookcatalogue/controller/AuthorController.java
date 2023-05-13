package io.samanthatobias.bookcatalogue.controller;

import javax.validation.Valid;
import javax.validation.ValidationException;

import io.samanthatobias.bookcatalogue.model.Author;
import io.samanthatobias.bookcatalogue.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/authors")
public class AuthorController {

	@Autowired
	private AuthorService authorService;

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
	public String saveAuthor(@Valid @ModelAttribute("author") Author author, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "add_author";
		}
		authorService.save(author);
		return "redirect:/authors";
	}

	@GetMapping("/delete/{id}")
	public String deleteAuthor(@PathVariable("id") Long id, RedirectAttributes ra) {
		try {
			authorService.delete(id);
		} catch (ValidationException e) {
			ra.addFlashAttribute("error", e.getMessage());
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
	public String updateAuthor(@Valid @ModelAttribute("author") Author author, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "edit_author";
		}
		authorService.save(author);
		return "redirect:/authors";
	}

}
