package com.mathvideos.api.config;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;

@Configuration
public class FirebaseConfig {

	@Bean
	public FirebaseApp firebase() throws IOException {
		InputStream serviceAccount = getClass().getResourceAsStream("/firebase.json");
		
		FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
	            .setStorageBucket("mathvideos-87e44.appspot.com")
				.build();
		
		return FirebaseApp.initializeApp(options);
	}

    @Bean
    public StorageClient storageClient() {
        return StorageClient.getInstance();
    }
    
}
