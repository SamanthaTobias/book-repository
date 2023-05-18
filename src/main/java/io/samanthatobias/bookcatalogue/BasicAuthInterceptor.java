package io.samanthatobias.bookcatalogue;

import java.util.Arrays;
import java.util.Base64;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.HandlerInterceptor;

public class BasicAuthInterceptor implements HandlerInterceptor {

	@Autowired
	Environment environment;

	@Value("${BASIC_AUTH_USERNAME:#{null}}")
	private String adminName;

	@Value("${BASIC_AUTH_PASSWORD:#{null}}")
	private String adminPassword;

	@Override
	public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null) {
			String[] authHeaderParts = authHeader.split(" ");
			if (authHeaderParts.length == 2 && "Basic".equals(authHeaderParts[0])) {
				String decodedAuthHeader = new String(Base64.getDecoder().decode(authHeaderParts[1]));
				String[] credentials = decodedAuthHeader.split(":");
				if (isLocalEnvironment() || matchAdminCredentials(credentials)) {
					return true;
				}
			}
		}

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setHeader("WWW-Authenticate", "Basic realm=\"Access to the staging site\", charset=\"UTF-8\"");
		return false;
	}

	private boolean isLocalEnvironment() {
		boolean local = Arrays.stream(environment.getActiveProfiles()).anyMatch(p -> p.equalsIgnoreCase("local"));
		if (local) {
			System.out.println("User is local.");
		}
		return local;
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
			System.out.printf("BASIC_AUTH_USERNAME:%s", System.getenv("BASIC_AUTH_USERNAME"));
			System.out.printf("BASIC_AUTH_PASSWORD:%s", System.getenv("BASIC_AUTH_PASSWORD"));
		}
		System.out.println("Credentials correct: " + correct);
		return correct;
	}

}
