package io.samanthatobias.bookcatalogue.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

	@GetMapping("/")
	public String viewHomePage() {
		return "home";
	}

	// SQL Injection example
	@GetMapping("/user")
	public void getUser(@RequestParam String name) {
		String query = "SELECT * FROM users WHERE name = '" + name + "'";
		try (Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
			 Statement statement = connection.createStatement()) {
			statement.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// XSS example
	@GetMapping("/hello")
	public void helloUser(@RequestParam String user, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><body>Hello, " + user + "</body></html>");
	}

	// Path traversal example
	@GetMapping("/serveFile")
	public void serveFile(@RequestParam String filename, HttpServletResponse response) throws IOException {
		File file = new File("/var/www/" + filename);
		response.getWriter().println("Serving file: " + file.getAbsolutePath());
	}

}
