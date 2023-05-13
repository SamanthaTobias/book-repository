package io.samanthatobias.bookcatalogue.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

class HomeControllerTest {

	@InjectMocks
	private HomeController homeController;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testSaveAuthorWithoutErrors() {
		String viewName = homeController.viewHomePage();
		assertThat(viewName).isEqualTo("home");
	}

}