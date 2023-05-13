package io.samanthatobias.bookcatalogue.validator;

import java.util.ArrayList;
import java.util.List;

import io.samanthatobias.bookcatalogue.model.Author;
import org.springframework.stereotype.Service;

@Service
public class AuthorValidator {

	public List<String> validate(Author author) {
		List<String> errors = new ArrayList<>();
		if (author.getName() == null || author.getName().trim().isEmpty()) {
			errors.add("Author name is required.");
		}
		return errors;
	}

}
