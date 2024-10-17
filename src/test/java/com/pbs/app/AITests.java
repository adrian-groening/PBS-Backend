package com.pbs.app;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.pbs.app.Entities.CreatorAttributes;
import com.pbs.app.Llama.AI;

public class AITests {

    private AI ai;
    private HttpClient mockHttpClient;
    private HttpResponse<String> mockHttpResponse;

    @BeforeEach
    void setUp() throws Exception {
        // Mock HttpClient and HttpResponse
        mockHttpClient = mock(HttpClient.class);
        mockHttpResponse = mock(HttpResponse.class);

        // Initialize AI (unmodified) for testing
        ai = new AI();
    }

    @Test
    void testGenerateInstagramCaptionForProduct() throws Exception {
        String mockResponse = "{\"model\": \"llama3\", \"response\":\"This is an Instagram caption\"}";
        mockHttpInteraction(mockResponse);

        CreatorAttributes creatorAttributes = new CreatorAttributes();
        creatorAttributes.setAge("30");
        String jsonProduct = "{ \"productID\": \"12345\" }";

        String result = ai.generateInstagramCaptionForProduct(jsonProduct, creatorAttributes);

        assertEquals("This is an Instagram caption", result);
    }

    @Test
    void testGenerateTwitterCaptionForProduct() throws Exception {
        String mockResponse = "{\"model\": \"llama3\", \"response\":\"This is a Twitter caption\"}";
        mockHttpInteraction(mockResponse);

        CreatorAttributes creatorAttributes = new CreatorAttributes();
        creatorAttributes.setAge("25");
        String jsonProduct = "{ \"productID\": \"54321\" }";

        String result = ai.generateTwitterCaptionForProduct(jsonProduct, creatorAttributes);

        assertEquals("This is a Twitter caption", result);
    }

    @Test
    void testGenerateFacebookCaptionForProduct() throws Exception {
        String mockResponse = "{\"model\": \"llama3\", \"response\":\"This is a Facebook caption\"}";
        mockHttpInteraction(mockResponse);

        CreatorAttributes creatorAttributes = new CreatorAttributes();
        creatorAttributes.setAge("35");
        String jsonProduct = "{ \"productID\": \"67890\" }";

        String result = ai.generateFacebookCaptionForProduct(jsonProduct, creatorAttributes);

        assertEquals("This is a Facebook caption", result);
    }

    @Test
    void testGenerateCaptionForProductComparison() throws Exception {
        String mockResponse = "{\"model\": \"llama3\", \"response\":\"Best product comparison caption\"}";
        mockHttpInteraction(mockResponse);

        String jsonProduct = "{ \"productID\": \"11122\" }";

        String result = ai.generateCaptionForProductComparison(jsonProduct);

        assertEquals("Best product comparison caption", result);
    }

    private void mockHttpInteraction(String mockResponse) throws Exception {
        // Mock the behavior of the HttpResponse to return the mock response body
        when(mockHttpResponse.body()).thenReturn(mockResponse);

        // Mock HttpClient's send() method to return the mock HttpResponse
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockHttpResponse);

        // Use reflection to inject the mock HttpClient into the AI class
        injectHttpClientIntoAI(ai, mockHttpClient);
    }

    private void injectHttpClientIntoAI(AI ai, HttpClient mockHttpClient) throws Exception {
        // Use reflection to change the private field 'client' in AI class
        java.lang.reflect.Field clientField = AI.class.getDeclaredField("client");
        clientField.setAccessible(true); // Bypass the private visibility
        clientField.set(ai, mockHttpClient); // Set the mock HttpClient instance
    }
}