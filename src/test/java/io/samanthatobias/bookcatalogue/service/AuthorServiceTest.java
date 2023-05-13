package io.samanthatobias.bookcatalogue.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import javax.validation.ValidationException;

import io.samanthatobias.bookcatalogue.exception.ResourceNotFoundException;
import io.samanthatobias.bookcatalogue.model.Author;
import io.samanthatobias.bookcatalogue.model.Book;
import io.samanthatobias.bookcatalogue.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class AuthorServiceTest {

	@InjectMocks
	private AuthorService authorService;

	@Mock
	private AuthorRepository authorRepository;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void getAllAuthorsTest() {
		when(authorRepository.findAll()).thenReturn(new ArrayList<>());
		assertThat(authorService.getAll()).isEmpty();
		verify(authorRepository, times(1)).findAll();
	}

	@Test
	public void saveAuthorTest() {
		Author author = new Author();
		authorService.save(author);
		verify(authorRepository, times(1)).save(author);
	}

	@Test
	public void deleteAuthorWithoutBooksTest() {
		Author author = mock(Author.class);
		when(author.getBooks()).thenReturn(new ArrayList<>());
		when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));
		authorService.delete(1L);
		verify(authorRepository, times(1)).deleteById(anyLong());
	}

	@Test
	public void deleteAuthorWithBooksTest() {
		Author author = mock(Author.class);
		when(author.getBooks()).thenReturn(Collections.singletonList(new Book()));
		when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));
		assertThatThrownBy(() -> authorService.delete(1L))
				.isInstanceOf(ValidationException.class)
				.hasMessage("Cannot delete author with existing books.");
	}

	@Test
	public void deleteAuthorNotFoundTest() {
		when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());
		assertThatThrownBy(() -> authorService.delete(1L))
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("Resource (1) not found");
	}

}
