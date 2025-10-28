package com.trombae.pathofbossingapi.utils.clients;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

@Slf4j
public class HttpGetClient {
    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Sends a GET request to the specified URL and returns the response body as a string.
     *
     * @param url The URL to send the GET request to
     * @return The response body as a string
     * @throws IOException If an error occurs during the request
     */
    private static String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        log.info("Sending GET request to {}", request.url());

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected HTTP status code: " + response.code());
        }

        return response.body() != null ? response.body().string() : "";
    }

    /**
     * Sends a GET request to the specified URL and returns the response body as a Json.
     *
     * @param url The URL to send the GET request to
     * @return The response body as a JsonNode
     */
    public static JsonNode getAsJson(String url) {
        try {
            String rawJson = get(url);
            return objectMapper.readTree(rawJson);
        } catch (IOException ex) {
            // TODO: Improve error handling
            return objectMapper.createObjectNode();
        }
    }

}
