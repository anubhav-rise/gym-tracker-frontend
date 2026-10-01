package com.java.gymtrackerui;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Base64;

public class JwtUtils {

    public static Long extractUserId(String token) throws Exception {

        String[] parts = token.split("\\.");
        String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(payloadJson);

        // Adjust "userId" below if your JwtService uses a different claim name
        return node.get("userId").asLong();
    }
}