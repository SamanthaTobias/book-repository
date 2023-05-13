package io.samanthatobias.bookcatalogue.controller;

import javax.validation.ValidationException;

import io.samanthatobias.bookcatalogue.model.Author;
import io.samanthatobias.bookcatalogue.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AuthorControllerTest {

	@InjectMocks
	private AuthorController authorController;

	@Mock
	private AuthorService authorService;

	@Mock
	private Model model;

	@Mock
	private BindingResult bindingResult;

	@Mock
	private RedirectAttributes ra;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testSaveAuthorWithErrors() {
		Author author = new Author();
		when(bindingResult.hasErrors()).thenReturn(true);

		String viewName = authorController.saveAuthor(author, bindingResult, model);
		verify(authorService, never()).saveAuthor(any(Author.class));
		assertThat(viewName).isEqualTo("add_author");
	}

	@Test
	public void testSaveAuthorWithoutErrors() {
		Author author = new Author();
		when(bindingResult.hasErrors()).thenReturn(false);

		String viewName = authorController.saveAuthor(author, bindingResult, model);
		verify(authorService, times(1)).saveAuthor(any(Author.class));
		assertThat(viewName).isEqualTo("redirect:/authors");
	}

	@Test
	public void testViewAuthorPage() {
		String viewName = authorController.viewAuthorPage(model);
		verify(authorService, times(1)).getAllAuthors();
		verify(model, times(1)).addAttribute(anyString(), any());
		assertThat(viewName).isEqualTo("authors");
	}

	@Test
	public void testShowNewAuthorForm() {
		String viewName = authorController.showNewAuthorForm(model);
		verify(model, times(1)).addAttribute(anyString(), any());
		assertThat(viewName).isEqualTo("new_author");
	}

	@Test
	public void testDeleteAuthorWithoutErrors() {
		Long id = 1L;
		String viewName = authorController.deleteAuthor(id, ra);
		verify(authorService, times(1)).deleteAuthor(id);
		assertThat(viewName).isEqualTo("redirect:/authors");
	}

	@Test
	public void testDeleteAuthorWithValidationError() {
		Long id = 1L;
		doThrow(new ValidationException("Error")).when(authorService).deleteAuthor(id);
		String viewName = authorController.deleteAuthor(id, ra);
		verify(authorService, times(1)).deleteAuthor(id);
		verify(ra, times(1)).addFlashAttribute(anyString(), any());
		assertThat(viewName).isEqualTo("redirect:/authors");
	}

}
