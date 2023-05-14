package io.samanthatobias.bookcatalogue.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.ValidationException;

import io.samanthatobias.bookcatalogue.BasicAuthInterceptor;
import io.samanthatobias.bookcatalogue.model.Author;
import io.samanthatobias.bookcatalogue.service.AuthorService;
import io.samanthatobias.bookcatalogue.validator.AuthorValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthorService authorService;

	@MockBean
	private AuthorValidator authorValidator;

	@MockBean
	private BasicAuthInterceptor basicAuthInterceptor;

	@BeforeEach
	void setUp() {
		when(basicAuthInterceptor.preHandle(any(), any(), any())).thenReturn(true);
	}

	@Test
	void viewAuthorPage() throws Exception {
		List<Author> authors = Arrays.asList(new Author(), new Author());
		given(authorService.getAll()).willReturn(authors);

		mockMvc.perform(get("/authors"))
				.andExpect(status().isOk())
				.andExpect(view().name("authors"));

		verify(authorService, times(1)).getAll();
	}

	@Test
	void showNewAuthorForm() throws Exception {
		mockMvc.perform(get("/authors/showNewAuthorForm"))
				.andExpect(status().isOk())
				.andExpect(view().name("new_author"));
	}

	@Test
	void saveAuthorWithErrors() throws Exception {
		List<String> errors = Arrays.asList("Error1", "Error2");
		given(authorValidator.validate(any())).willReturn(errors);

		mockMvc.perform(post("/authors/save"))
				.andExpect(status().isOk())
				.andExpect(view().name("new_author"));

		verify(authorService, times(0)).save(any());
	}

	@Test
	void saveAuthorWithoutErrors() throws Exception {
		given(authorValidator.validate(any())).willReturn(new ArrayList<>());

		mockMvc.perform(post("/authors/save"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/authors"));

		verify(authorService, times(1)).save(any());
	}

	@Test
	void deleteAuthorWithException() throws Exception {
		ValidationException exception = new ValidationException("error");

		doThrow(exception).when(authorService).delete(anyLong());

		MvcResult result = mockMvc.perform(get("/authors/delete/1"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/authors"))
				.andReturn();

		verify(authorService, times(1)).delete(anyLong());

		// Check that the flash attribute has been set
		Object flashAttributeErrors = result.getFlashMap().get("errors");
		assertThat(flashAttributeErrors).isEqualTo(List.of(exception.getMessage()));
	}


	@Test
	void deleteAuthorWithoutException() throws Exception {
		doNothing().when(authorService).delete(anyLong());

		mockMvc.perform(get("/authors/delete/1"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/authors"));

		verify(authorService, times(1)).delete(anyLong());
	}

	@Test
	void showEditForm() throws Exception {
		Author author = new Author();
		given(authorService.getById(anyLong())).willReturn(author);

		mockMvc.perform(get("/authors/edit/1"))
				.andExpect(status().isOk())
				.andExpect(view().name("edit_author"));

		verify(authorService, times(1)).getById(anyLong());
	}

	@Test
	void updateAuthorWithErrors() throws Exception {
		List<String> errors = Arrays.asList("Error1", "Error2");
		given(authorValidator.validate(any())).willReturn(errors);

		mockMvc.perform(post("/authors/edit"))
				.andExpect(status().isOk())
				.andExpect(view().name("edit_author"));

		verify(authorService, times(0)).save(any());
	}

	@Test
	void updateAuthorWithoutErrors() throws Exception {
		given(authorValidator.validate(any())).willReturn(new ArrayList<>());

		mockMvc.perform(post("/authors/save"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/authors"));

		verify(authorService, times(1)).save(any());
	}
}
