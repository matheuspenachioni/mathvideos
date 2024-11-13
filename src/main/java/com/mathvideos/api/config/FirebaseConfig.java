package com.mathvideos.api.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class FirebaseConfig {
    Dotenv dotenv = Dotenv.load();

	@Bean
	public FirebaseApp firebase() throws IOException {
		Map<String, Object> serviceAccount = new HashMap<>();
		serviceAccount.put("type", dotenv.get("FIREBASE_TYPE"));
		serviceAccount.put("project_id", dotenv.get("FIREBASE_PROJECT_ID"));
		serviceAccount.put("private_key_id", dotenv.get("FIREBASE_PRIVATE_KEY_ID"));
		serviceAccount.put("private_key", dotenv.get("FIREBASE_PRIVATE_KEY").replace("\\n", "\n"));
		serviceAccount.put("client_email", dotenv.get("FIREBASE_CLIENT_EMAIL"));
		serviceAccount.put("client_id", dotenv.get("FIREBASE_CLIENT_ID"));
		serviceAccount.put("auth_uri", dotenv.get("FIREBASE_AUTH_URI"));
		serviceAccount.put("token_uri", dotenv.get("FIREBASE_TOKEN_URI"));
		serviceAccount.put("auth_provider_x509_cert_url", dotenv.get("FIREBASE_AUTH_PROVIDER_X509_CERT_URL"));
		serviceAccount.put("client_x509_cert_url", dotenv.get("FIREBASE_CLIENT_X509_CERT_URL"));
		
		
		FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(new ByteArrayInputStream(
						new ObjectMapper().writeValueAsBytes(serviceAccount)))
				).setStorageBucket("mathvideos-87e44.appspot.com")
				.build();
		
		return FirebaseApp.initializeApp(options);
	}

    @Bean
    public StorageClient storageClient() {
        return StorageClient.getInstance();
    }
    
}
