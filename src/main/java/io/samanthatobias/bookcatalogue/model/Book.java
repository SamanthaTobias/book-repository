package io.samanthatobias.bookcatalogue.model;

import javax.validation.constraints.NotBlank;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Title is mandatory")
	private String title;

	private String isbn;

	@ManyToOne
	@JoinColumn(name = "author_id", nullable = false)
	private Author author;

}
