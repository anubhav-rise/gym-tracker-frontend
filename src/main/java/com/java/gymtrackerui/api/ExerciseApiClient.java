package com.java.gymtrackerui.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.gymtrackerui.Session;
import com.java.gymtrackerui.model.Exercise;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class ExerciseApiClient {

    private static final String BASE_URL = "http://localhost:8080/api/exercises";

    private final HttpClient httpClient = HttpClient.newHttpClient();

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public List<Exercise> getVisibleExercises() throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Authorization", "Bearer " + Session.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() == 200) {
            Exercise[] exercises = objectMapper.readValue(response.body(), Exercise[].class);
            return Arrays.asList(exercises);
        } else {
            throw new RuntimeException("Failed to fetch exercises: " + response.body());
        }
    }
}