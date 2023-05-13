package io.samanthatobias.bookcatalogue.controller;

import java.util.Arrays;
import java.util.List;

import io.samanthatobias.bookcatalogue.model.Author;
import io.samanthatobias.bookcatalogue.model.Book;
import io.samanthatobias.bookcatalogue.service.AuthorService;
import io.samanthatobias.bookcatalogue.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class BookControllerTest {

	@InjectMocks
	private BookController bookController;

	@Mock
	private BookService bookService;

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
	public void testViewHomePage() {
		List<Book> books = Arrays.asList(new Book(), new Book());
		when(bookService.getAll()).thenReturn(books);

		String viewName = bookController.viewHomePage(model);

		verify(bookService, times(1)).getAll();
		verify(model, times(1)).addAttribute("books", books);
		assertThat(viewName).isEqualTo("books");
	}

	@Test
	public void testShowNewBookForm() {
		List<Author> authors = Arrays.asList(new Author(), new Author());
		when(authorService.getAll()).thenReturn(authors);

		String viewName = bookController.showNewBookForm(model);

		verify(authorService, times(1)).getAll();
		verify(model, times(1)).addAttribute(eq("authors"), any());
		verify(model, times(1)).addAttribute(eq("book"), any());
		assertThat(viewName).isEqualTo("new_book");
	}

	@Test
	public void testSaveBookWithErrors() {
		Book book = new Book();
		when(bindingResult.hasErrors()).thenReturn(true);
		List<Author> authors = Arrays.asList(new Author(), new Author());
		when(authorService.getAll()).thenReturn(authors);

		String viewName = bookController.saveBook(book, bindingResult, model);

		verify(bookService, never()).save(any(Book.class));
		verify(model, times(1)).addAttribute("authors", authors);
		assertThat(viewName).isEqualTo("new_book");
	}

	@Test
	public void testSaveBookWithoutErrors() {
		Book book = new Book();
		when(bindingResult.hasErrors()).thenReturn(false);

		String viewName = bookController.saveBook(book, bindingResult, model);

		verify(bookService, times(1)).save(any(Book.class));
		assertThat(viewName).isEqualTo("redirect:/books");
	}

	@Test
	public void testDeleteBook() {
		Long id = 1L;

		String viewName = bookController.deleteBook(id, model);

		verify(bookService, times(1)).delete(id);
		assertThat(viewName).isEqualTo("redirect:/books");
	}

}