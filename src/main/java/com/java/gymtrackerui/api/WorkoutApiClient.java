package com.java.gymtrackerui.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.gymtrackerui.Session;
import com.java.gymtrackerui.model.Workout;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class WorkoutApiClient {

    private static final String BASE_URL = "http://localhost:8080/api/workouts";

    private final HttpClient httpClient = HttpClient.newHttpClient();

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public List<Workout> getMyWorkouts() throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Authorization", "Bearer " + Session.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() == 200) {
            Workout[] workouts = objectMapper.readValue(response.body(), Workout[].class);
            return Arrays.asList(workouts);
        } else {
            throw new RuntimeException("Failed to fetch workouts: " + response.body());
        }
    }

    public void createWorkout(Workout workout) throws Exception {

        String jsonBody = objectMapper.writeValueAsString(workout);

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
            throw new RuntimeException("Failed to create workout: " + response.body());
        }
    }

    public void deleteWorkout(Long workoutId) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + workoutId))
                .header("Authorization", "Bearer " + Session.getInstance().getToken())
                .DELETE()
                .build();

        HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 204) {
            throw new RuntimeException("Failed to delete workout: " + response.body());
        }
    }
    public List<String> getWorkoutDatesForMonth(int year, int month) throws Exception {

        String url = BASE_URL + "/calendar?year=" + year + "&month=" + month;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + Session.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() == 200) {
            String[] dates = objectMapper.readValue(response.body(), String[].class);
            return Arrays.asList(dates);
        } else {
            throw new RuntimeException("Failed to fetch calendar: " + response.body());
        }
    }
}