package io.samanthatobias.bookcatalogue.validator;

import java.util.List;

import io.samanthatobias.bookcatalogue.model.Author;
import io.samanthatobias.bookcatalogue.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BookValidatorTest {

	private BookValidator bookValidator;

	@BeforeEach
	public void setUp() {
		this.bookValidator = new BookValidator();
	}

	@Test
	public void validate_givenNullTitle_returnsTitleError() {
		Book book = new Book();
		book.setTitle(null);
		Author author = new Author();
		author.setId(1L);
		book.setAuthor(author);

		List<String> errors = bookValidator.validate(book);

		assertThat(errors).containsExactly("Title is required");
	}

	@Test
	public void validate_givenEmptyTitle_returnsTitleError() {
		Book book = new Book();
		book.setTitle("");
		Author author = new Author();
		author.setId(1L);
		book.setAuthor(author);

		List<String> errors = bookValidator.validate(book);

		assertThat(errors).containsExactly("Title is required");
	}

	@Test
	public void validate_givenNullAuthor_returnsAuthorError() {
		Book book = new Book();
		book.setTitle("Title");
		book.setAuthor(null);

		List<String> errors = bookValidator.validate(book);

		assertThat(errors).containsExactly("Author is required");
	}

	@Test
	public void validate_givenAuthorWithoutId_returnsAuthorError() {
		Book book = new Book();
		book.setTitle("Title");
		Author author = new Author();
		author.setId(null);
		book.setAuthor(author);

		List<String> errors = bookValidator.validate(book);

		assertThat(errors).containsExactly("Author is required");
	}

	@Test
	public void validate_givenValidBook_returnsNoErrors() {
		Book book = new Book();
		book.setTitle("Title");
		Author author = new Author();
		author.setId(1L);
		book.setAuthor(author);

		List<String> errors = bookValidator.validate(book);

		assertThat(errors).isEmpty();
	}

}
