package com.java.gymtrackerui.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.gymtrackerui.Session;
import com.java.gymtrackerui.model.BodyMeasurement;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class BodyMeasurementApiClient {

    private static final String BASE_URL = "http://localhost:8080/api/body-measurements";

    private final HttpClient httpClient = HttpClient.newHttpClient();

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public List<BodyMeasurement> getMyMeasurements() throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Authorization", "Bearer " + Session.getInstance().getToken())
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() == 200) {
            BodyMeasurement[] measurements =
                    objectMapper.readValue(response.body(), BodyMeasurement[].class);
            return Arrays.asList(measurements);
        } else {
            throw new RuntimeException("Failed to fetch measurements: " + response.body());
        }
    }

    public void createMeasurement(BodyMeasurement measurement) throws Exception {

        String jsonBody = objectMapper.writeValueAsString(measurement);

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
            throw new RuntimeException("Failed to add measurement: " + response.body());
        }
    }

    public void deleteMeasurement(Long measurementId) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + measurementId))
                .header("Authorization", "Bearer " + Session.getInstance().getToken())
                .DELETE()
                .build();

        HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 204) {
            throw new RuntimeException("Failed to delete measurement: " + response.body());
        }
    }
}