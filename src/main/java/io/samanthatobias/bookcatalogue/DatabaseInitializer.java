package io.samanthatobias.bookcatalogue;

import io.samanthatobias.bookcatalogue.model.Author;
import io.samanthatobias.bookcatalogue.model.Book;
import io.samanthatobias.bookcatalogue.repository.AuthorRepository;
import io.samanthatobias.bookcatalogue.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseInitializer {

	@Bean
	public CommandLineRunner initDatabase(AuthorRepository authorRepository, BookRepository bookRepository) {
		return args -> {
			Author martin = new Author();
			martin.setName("Robert C. Martin");
			authorRepository.save(martin);

			Author bloch = new Author();
			bloch.setName("Joshua Bloch");
			authorRepository.save(bloch);

			Author fowler = new Author();
			fowler.setName("Martin Fowler");
			authorRepository.save(fowler);

			Author evans = new Author();
			evans.setName("Eric Evans");
			authorRepository.save(evans);

			Author sierra = new Author();
			sierra.setName("Kathy Sierra");
			authorRepository.save(sierra);

			Book cleanCode = new Book();
			cleanCode.setTitle("Clean Code: A Handbook of Agile Software Craftsmanship");
			cleanCode.setIsbn("978-0132350884");
			cleanCode.setAuthor(martin);
			bookRepository.save(cleanCode);

			Book cleanArchitecture = new Book();
			cleanArchitecture.setTitle("Clean Architecture: A Craftsman's Guide to Software Structure and Design");
			// isbn optional
			cleanArchitecture.setAuthor(martin);
			bookRepository.save(cleanArchitecture);

			Book effectiveJava = new Book();
			effectiveJava.setTitle("Effective Java");
			effectiveJava.setIsbn("978-0134685991");
			effectiveJava.setAuthor(bloch);
			bookRepository.save(effectiveJava);

			Book refactoring = new Book();
			refactoring.setTitle("Refactoring: Improving the Design of Existing Code");
			refactoring.setIsbn("978-0201485677");
			refactoring.setAuthor(fowler);
			bookRepository.save(refactoring);

			Book headFirstJava = new Book();
			headFirstJava.setTitle("Head First Java");
			headFirstJava.setIsbn("978-0596009205");
			headFirstJava.setAuthor(sierra);
			bookRepository.save(headFirstJava);

			Book headFirstJava2ndEdition = new Book();
			headFirstJava2ndEdition.setTitle("Head First Java 2nd Edition");
			headFirstJava2ndEdition.setIsbn("978-0596009205");
			headFirstJava2ndEdition.setAuthor(sierra);
			bookRepository.save(headFirstJava2ndEdition);

			Book headFirstDesignPatterns = new Book();
			headFirstDesignPatterns.setTitle("Head First Design Patterns");
			headFirstDesignPatterns.setIsbn("978-0596007126");
			headFirstDesignPatterns.setAuthor(sierra);
			bookRepository.save(headFirstDesignPatterns);
		};
	}
}
