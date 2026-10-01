package com.java.gymtrackerui.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.gymtrackerui.Session;
import com.java.gymtrackerui.model.UpdateProfileRequest;
import com.java.gymtrackerui.model.UserProfile;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ProfileApiClient {

    private static final String BASE_URL = "http://localhost:8080/api/users";

    private final HttpClient httpClient = HttpClient.newHttpClient();

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public UserProfile getMyProfile() throws Exception {

        Long userId = Session.getInstance().getUserId();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + userId))
                .header("Authorization", "Bearer " + Session.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() == 200) {
            return objectMapper.readValue(response.body(), UserProfile.class);
        } else {
            throw new RuntimeException("Failed to fetch profile: " + response.body());
        }
    }

    public void updateMyProfile(UpdateProfileRequest updateRequest) throws Exception {

        String jsonBody = objectMapper.writeValueAsString(updateRequest);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/me"))
                .header("Authorization", "Bearer " + Session.getInstance().getToken())
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to update profile: " + response.body());
        }
    }
}