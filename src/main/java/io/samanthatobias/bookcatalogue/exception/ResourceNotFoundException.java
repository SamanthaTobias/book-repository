package io.samanthatobias.bookcatalogue.exception;

import java.util.NoSuchElementException;

import lombok.Getter;

public class ResourceNotFoundException extends NoSuchElementException {

	@Getter
	private final long id;

	public ResourceNotFoundException(long id) {
		super("Resource (" + id + ") not found");
		this.id = id;
	}

}
