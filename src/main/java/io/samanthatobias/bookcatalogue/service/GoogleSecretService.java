package io.samanthatobias.bookcatalogue.service;

import java.io.IOException;

import com.google.cloud.secretmanager.v1.AccessSecretVersionResponse;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.google.cloud.secretmanager.v1.SecretVersionName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GoogleSecretService {

	@Value("${google.cloud.platform.project.id}")
	private String PROJECT_ID;

	public String getAdminUsername() {
		return getSecret("BASIC_AUTH_USERNAME");
	}

	public String getAdminPassword() {
		return getSecret("BASIC_AUTH_PASSWORD");
	}

	private String getSecret(String secretId) {
		try (SecretManagerServiceClient client = SecretManagerServiceClient.create()) {
			SecretVersionName secretVersionName = SecretVersionName.of(PROJECT_ID, secretId, "latest");
			AccessSecretVersionResponse response = client.accessSecretVersion(secretVersionName);
			return response.getPayload().getData().toStringUtf8();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
