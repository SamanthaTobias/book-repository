package io.samanthatobias.bookcatalogue.controller;

import java.util.List;
import java.util.Objects;

import io.samanthatobias.bookcatalogue.BasicAuthInterceptor;
import io.samanthatobias.bookcatalogue.model.Author;
import io.samanthatobias.bookcatalogue.model.Book;
import io.samanthatobias.bookcatalogue.service.AuthorService;
import io.samanthatobias.bookcatalogue.service.BookService;
import io.samanthatobias.bookcatalogue.validator.BookValidator;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookService bookService;

	@MockBean
	private AuthorService authorService;

	@MockBean
	private BookValidator bookValidator;

	@MockBean
	private BasicAuthInterceptor basicAuthInterceptor;

	private Book book;

	@BeforeEach
	void setUp() {
		book = new Book();
		book.setId(1L);
		book.setTitle("Book Title");

		Author author = new Author();
		author.setId(1L);
		author.setName("Author Name");
		book.setAuthor(author);

		List<Author> authors = List.of(author);

		when(bookService.getAll()).thenReturn(List.of(book));
		when(authorService.getAll()).thenReturn(authors);
		when(basicAuthInterceptor.preHandle(any(), any(), any())).thenReturn(true);
	}

	@Test
	void viewHomePage() throws Exception {
		when(bookService.getAll()).thenReturn(List.of(book));

		mockMvc.perform(get("/books"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("books"))
				.andExpect(view().name("books"));
	}

	@Test
	void showNewBookForm() throws Exception {
		mockMvc.perform(get("/books/showNewBookForm"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("book"))
				.andExpect(model().attributeExists("authors"))
				.andExpect(view().name("new_book"));
	}

	@Test
	void saveBookWithErrors() throws Exception {
		when(bookValidator.validate(any(Book.class))).thenReturn(List.of("error"));

		MvcResult result = mockMvc.perform(post("/books/save")
						.flashAttr("book", book))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("errors"))
				.andExpect(model().attributeExists("authors"))
				.andExpect(view().name("new_book"))
				.andReturn();

		Object modelAttributeErrors = Objects.requireNonNull(result.getModelAndView()).getModel().get("errors");
		assertThat(modelAttributeErrors).isEqualTo(List.of("error"));

		verify(bookService, times(0)).save(any(Book.class));
	}

	@Test
	void saveBook() throws Exception {
		when(bookValidator.validate(any(Book.class))).thenReturn(List.of());

		mockMvc.perform(post("/books/save")
						.flashAttr("book", book))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/books"));

		verify(bookService, times(1)).save(any(Book.class));
	}

	@Test
	void deleteBook() throws Exception {
		mockMvc.perform(get("/books/delete/1"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/books"));

		verify(bookService, times(1)).delete(anyLong());
	}

	@Test
	void showEditForm() throws Exception {
		when(bookService.getById(anyLong())).thenReturn(book);

		mockMvc.perform(get("/books/edit/1"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("book"))
				.andExpect(model().attributeExists("authors"))
				.andExpect(view().name("edit_book"));
	}

	@Test
	void updateBookWithErrors() throws Exception {
		when(bookValidator.validate(any(Book.class))).thenReturn(List.of("error"));

		MvcResult result = mockMvc.perform(post("/books/edit")
						.flashAttr("book", book))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("errors"))
				.andExpect(model().attributeExists("authors"))
				.andExpect(view().name("edit_book"))
				.andReturn();

		Object modelAttributeErrors = Objects.requireNonNull(result.getModelAndView()).getModel().get("errors");
		assertThat(modelAttributeErrors).isEqualTo(List.of("error"));

		verify(bookService, times(0)).save(any(Book.class));
	}

	@Test
	void updateBook() throws Exception {
		when(bookValidator.validate(any(Book.class))).thenReturn(List.of());

		mockMvc.perform(post("/books/edit")
						.flashAttr("book", book))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/books"));

		verify(bookService, times(1)).save(any(Book.class));
	}

}
