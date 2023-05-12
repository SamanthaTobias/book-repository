package io.samanthatobias.bookcatalogue.controller;

import io.samanthatobias.bookcatalogue.model.Author;
import io.samanthatobias.bookcatalogue.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.mockito.Mockito.*;

public class AuthorControllerTest {

	@InjectMocks
	private AuthorController authorController;

	@Mock
	private AuthorService authorService;

	@Mock
	private Model model;

	@Mock
	private BindingResult bindingResult;

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
		assert (viewName.equals("add_author"));
	}

	@Test
	public void testSaveAuthorWithoutErrors() {
		Author author = new Author();
		when(bindingResult.hasErrors()).thenReturn(false);

		String viewName = authorController.saveAuthor(author, bindingResult, model);

		verify(authorService, times(1)).saveAuthor(any(Author.class));
		assert (viewName.equals("redirect:/authors"));
	}

}
