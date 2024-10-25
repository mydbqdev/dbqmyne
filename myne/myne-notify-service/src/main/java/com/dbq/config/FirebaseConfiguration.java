package com.dbq.config;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import com.google.api.client.http.HttpTransport;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;

@Configuration
@EnableConfigurationProperties(FirebaseProperties.class)
public class FirebaseConfiguration {
	

	@Value("${app.firebase.config.filepath}")
	private String fileConfig;
	
	 private final FirebaseProperties firebaseProperties;

	    public FirebaseConfiguration(FirebaseProperties firebaseProperties) {
	        this.firebaseProperties = firebaseProperties;
	    }

//	    @Bean
//	    GoogleCredentials googleCredentials() {
//	        try {
//	            if (firebaseProperties.getServiceAccount() != null) {
//	                try( InputStream is = firebaseProperties.getServiceAccount().getInputStream()) {
//	                    return GoogleCredentials.fromStream(is);
//	                }                
//	            } 
//	            else {
//	                // Use standard credentials chain. Useful when running inside GKE
//	                return GoogleCredentials.getApplicationDefault();
//	            }
//	        } 
//	        catch (IOException ioe) {
//	            throw new RuntimeException(ioe);
//	        }
//	    }

//	    @Bean
//	    FirebaseApp firebaseApp(GoogleCredentials credentials) {
//	        FirebaseOptions options = FirebaseOptions.builder()
//	          .setCredentials(credentials)
//	          .build();
//
//	        return FirebaseApp.initializeApp(options);
//	    }
	    
	    @Bean
	    FirebaseApp firebaseApp() throws IOException {
	    	
	    	File file = ResourceUtils.getFile("file://"  + fileConfig);
	    	FileInputStream serviceAccount =new FileInputStream(file);
	    	FirebaseOptions options = new FirebaseOptions.Builder()
	    			  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
	    			  .setDatabaseUrl("https://zoy-mobile-app-default-rtdb.asia-southeast1.firebasedatabase.app")
	    			  .build();
	        return FirebaseApp.initializeApp(options);
	    }

	    @Bean
	    FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
	        return FirebaseMessaging.getInstance(firebaseApp);
	    }
}
