package com.pbs.app.Llama;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;



public class AI {

    

    public AI() {
        
    }

    public String generateInstagramCaptionForProduct(String json) throws Exception {
        return Post("Generate an instagram caption for the following json of products; Only include the output: " +json);
    }

    



    private static String escapeJson(String str) {
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\b", "\\b")
                  .replace("\f", "\\f")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
    
    public class LogEntry {
        String model;
        String created_at;
        String response;
        boolean done;
        String done_reason; 
    }

    public String Post(String prompt) throws Exception {
        //System.out.println("Prompt: " + prompt);

        String url = "http://localhost:11434/api/generate";
        String jsonPayload = "{\"model\": \"llama3\", \"prompt\":\"" + escapeJson(prompt) + "\"}";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request;
        request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();

        System.out.println("Response body: " + responseBody);

        // Process the response body to concatenate the log entries
        String concatenatedResponse = processLogEntries(responseBody);

        return concatenatedResponse;
    }

    private static String processLogEntries(String responseBody) {
        List<LogEntry> logs = new ArrayList<>();
        Gson gson = new Gson();

        // Split the response by lines to handle each JSON object separately
        String[] lines = responseBody.split("\\R");

        for (String line : lines) {
            try {
                LogEntry entry = gson.fromJson(line, LogEntry.class);
                logs.add(entry);
            } catch (JsonSyntaxException e) {
                System.out.println("Error parsing log entry: " + line);
                e.printStackTrace();
            }
        }

        System.out.println("Parsed log entries count: " + logs.size());

        // Concatenate the response fields
        StringBuilder fullResponse = new StringBuilder();
        for (LogEntry log : logs) {
            fullResponse.append(log.response);
        }

        return fullResponse.toString();
    }
}
