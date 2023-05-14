package io.samanthatobias.bookcatalogue.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@Value("${git.commit.id.abbrev:unknown}")
	private String commitId;

	@Value("${git.commit.time:unknown}")
	private String commitTime;

	@GetMapping("/")
	public String viewHomePage(Model model) {
		model.addAttribute("commitId", commitId);
		model.addAttribute("commitTime", commitTime);
		return "home";
	}

}
