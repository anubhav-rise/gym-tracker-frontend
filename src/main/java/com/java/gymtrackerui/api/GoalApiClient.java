package com.java.gymtrackerui.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.gymtrackerui.Session;
import com.java.gymtrackerui.model.Goal;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class GoalApiClient {

    private static final String BASE_URL = "http://localhost:8080/api/goals";

    private final HttpClient httpClient = HttpClient.newHttpClient();

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public List<Goal> getMyGoals() throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Authorization", "Bearer " + Session.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() == 200) {
            Goal[] goals = objectMapper.readValue(response.body(), Goal[].class);
            return Arrays.asList(goals);
        } else {
            throw new RuntimeException("Failed to fetch goals: " + response.body());
        }
    }

    public void createGoal(Goal goal) throws Exception {

        String jsonBody = objectMapper.writeValueAsString(goal);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Authorization", "Bearer " + Session.getInstance().getToken())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to create goal: " + response.body());
        }
    }

    public void deleteGoal(Long goalId) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + goalId))
                .header("Authorization", "Bearer " + Session.getInstance().getToken())
                .DELETE()
                .build();

        HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 204) {
            throw new RuntimeException("Failed to delete goal: " + response.body());
        }
    }
}