package io.samanthatobias.bookcatalogue.service;

import java.util.ArrayList;
import java.util.Optional;

import io.samanthatobias.bookcatalogue.exception.ResourceNotFoundException;
import io.samanthatobias.bookcatalogue.model.Book;
import io.samanthatobias.bookcatalogue.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class BookServiceTest {

	@InjectMocks
	private BookService bookService;

	@Mock
	private BookRepository bookRepository;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void getAllBooksTest() {
		when(bookRepository.findAll()).thenReturn(new ArrayList<>());
		assertThat(bookService.getAllBooks()).isEmpty();
		verify(bookRepository, times(1)).findAll();
	}

	@Test
	public void saveBookTest() {
		Book book = new Book();
		bookService.saveBook(book);
		verify(bookRepository, times(1)).save(book);
	}

	@Test
	public void deleteBookFoundTest() {
		when(bookRepository.findById(anyLong())).thenReturn(Optional.of(new Book()));
		bookService.deleteBook(1L);
		verify(bookRepository, times(1)).deleteById(1L);
	}

	@Test
	public void deleteBookNotFoundTest() {
		when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
		assertThatThrownBy(() -> bookService.deleteBook(1L))
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("Resource (1) not found");
	}

}
