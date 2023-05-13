package io.samanthatobias.bookcatalogue.validator;

import java.util.ArrayList;
import java.util.List;

import io.samanthatobias.bookcatalogue.model.Book;
import org.springframework.stereotype.Service;

@Service
public class BookValidator {

	public List<String> validate(Book book) {
		List<String> errors = new ArrayList<>();
		if (book.getTitle() == null || book.getTitle().isEmpty()) {
			errors.add("Title is required");
		}
		if (book.getAuthor() == null || book.getAuthor().getId() == null) {
			errors.add("Author is required");
		}
		return errors;
	}

}
