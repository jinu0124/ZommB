package com.ssafy.commb.common.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Collections;

@Service
public class FcmInitializer {

    @Value("${fcm.service-account-file}")
    private String FIREBASE_CONFIG_PATH;

    @Getter
    private String FcmAccessToken;

    @PostConstruct
    public String initializer() throws IOException {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(FIREBASE_CONFIG_PATH).getInputStream())).build();

        FirebaseApp.initializeApp(options);                                 // SDK


        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(FIREBASE_CONFIG_PATH)
                        .getInputStream()).createScoped(Collections.singleton("https://www.googleapis.com/auth/cloud-platform")); // credential 키 인증

        googleCredentials.refreshIfExpired();

        FcmAccessToken = googleCredentials.getAccessToken().getTokenValue();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}
