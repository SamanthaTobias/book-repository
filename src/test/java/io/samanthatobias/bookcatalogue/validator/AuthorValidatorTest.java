package io.samanthatobias.bookcatalogue.validator;

import java.util.List;

import io.samanthatobias.bookcatalogue.model.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorValidatorTest {

	private AuthorValidator authorValidator;

	@BeforeEach
	public void setUp() {
		this.authorValidator = new AuthorValidator();
	}

	@Test
	public void validate_givenNullName_returnsNameError() {
		Author author = new Author();
		author.setName(null);

		List<String> errors = authorValidator.validate(author);

		assertThat(errors).containsExactly("Author name is required.");
	}

	@Test
	public void validate_givenEmptyName_returnsNameError() {
		Author author = new Author();
		author.setName("");

		List<String> errors = authorValidator.validate(author);

		assertThat(errors).containsExactly("Author name is required.");
	}

	@Test
	public void validate_givenNameWithOnlySpaces_returnsNameError() {
		Author author = new Author();
		author.setName("   ");

		List<String> errors = authorValidator.validate(author);

		assertThat(errors).containsExactly("Author name is required.");
	}

	@Test
	public void validate_givenValidName_returnsNoErrors() {
		Author author = new Author();
		author.setName("Author Name");

		List<String> errors = authorValidator.validate(author);

		assertThat(errors).isEmpty();
	}

}
