package com.java.gymtrackerui.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.gymtrackerui.Session;
import com.java.gymtrackerui.model.GoalProgress;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class GoalProgressApiClient {

    private static final String BASE_URL = "http://localhost:8080/api/goals";

    private final HttpClient httpClient = HttpClient.newHttpClient();

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public List<GoalProgress> getProgress(Long goalId) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + goalId + "/progress"))
                .header("Authorization", "Bearer " + Session.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() == 200) {
            GoalProgress[] progress = objectMapper.readValue(response.body(), GoalProgress[].class);
            return Arrays.asList(progress);
        } else {
            throw new RuntimeException("Failed to fetch progress: " + response.body());
        }
    }

    public void addProgress(Long goalId, GoalProgress progress) throws Exception {

        String jsonBody = objectMapper.writeValueAsString(progress);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + goalId + "/progress"))
                .header("Authorization", "Bearer " + Session.getInstance().getToken())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to add progress: " + response.body());
        }
    }

    public void deleteProgress(Long progressId) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/progress/" + progressId))
                .header("Authorization", "Bearer " + Session.getInstance().getToken())
                .DELETE()
                .build();

        HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 204) {
            throw new RuntimeException("Failed to delete progress: " + response.body());
        }
    }
}