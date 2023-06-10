package io.samanthatobias.bookcatalogue;

import java.util.Arrays;
import java.util.Base64;

import io.samanthatobias.bookcatalogue.service.GoogleSecretService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.HandlerInterceptor;

public class BasicAuthInterceptor implements HandlerInterceptor {

	private final Environment environment;
	private final String adminName;
	private final String adminPassword;

	@Autowired
	private GoogleSecretService googleSecretService;

	public BasicAuthInterceptor(Environment environment) {
		this.environment = environment;
		if (isCloudEnvironment()) {
			System.out.println("Cloud profile, getting username and password from Google Secret Service.");
			adminName = googleSecretService.getAdminUsername();
			adminPassword = googleSecretService.getAdminPassword();
		} else {
			System.out.println("No cloud profile, setting username and password to null.");
			adminName = null;
			adminPassword = null;
		}
	}

	@Override
	public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
		if (isLocalEnvironment()) {
			System.out.println("User is local, skipping authentication step.");
			return true;
		}
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null) {
			String[] authHeaderParts = authHeader.split(" ");
			if (authHeaderParts.length == 2 && "Basic".equals(authHeaderParts[0])) {
				String decodedAuthHeader = new String(Base64.getDecoder().decode(authHeaderParts[1]));
				String[] credentials = decodedAuthHeader.split(":");
				if (matchAdminCredentials(credentials)) {
					return true;
				}
			}
		}

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setHeader("WWW-Authenticate", "Basic realm=\"Access to the staging site\", charset=\"UTF-8\"");
		return false;
	}

	private boolean isLocalEnvironment() {
		return Arrays.stream(environment.getActiveProfiles()).anyMatch(p -> p.equalsIgnoreCase("local"));
	}

	private boolean isCloudEnvironment() {
		return Arrays.stream(environment.getActiveProfiles()).anyMatch(p -> p.equalsIgnoreCase("cloud"));
	}

	private boolean matchAdminCredentials(String[] credentials) {
		boolean correct = false;
		System.out.println("checking credentials");
		System.out.printf("name=\"%s\", phrase=\"%s\"%n", adminName, adminPassword);
		if (credentials.length == 2 && adminName != null && adminPassword != null) {
			if (adminName.equals(credentials[0]) && adminPassword.equals(credentials[1])) {
				correct = true;
			} else {
				System.out.printf("""
								Credentials incorrect.
								Is: "%s:%s"
								Should be: "%s:%s"%n""",
						credentials[0], credentials[1], adminName, adminPassword);
			}
		} else {
			System.out.println("Missing credential data");
			System.out.printf("BASIC_AUTH_USERNAME:%s%n", System.getenv("BASIC_AUTH_USERNAME"));
			System.out.printf("BASIC_AUTH_PASSWORD:%s%n", System.getenv("BASIC_AUTH_PASSWORD"));
		}
		System.out.println("Credentials correct: " + correct);
		return correct;
	}

}
