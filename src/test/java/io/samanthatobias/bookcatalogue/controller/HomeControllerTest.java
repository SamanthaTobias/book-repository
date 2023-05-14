package io.samanthatobias.bookcatalogue.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import static org.assertj.core.api.Assertions.assertThat;

public class HomeControllerTest {

	@Test
	public void testHomePage() {
		HomeController controller = new HomeController();
		Model model = new BindingAwareModelMap();
		String result = controller.viewHomePage(model);
		assertThat(result).isEqualTo("home");
	}

}
