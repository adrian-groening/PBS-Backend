package com.pbs.app;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.pbs.app.Entities.Product;
import com.pbs.app.Search.ImpactSearch.ImpactSearch;
import com.pbs.app.Services.Data;

class ImpactSearchTests {

    private Data data;
    
    private ImpactSearch impactSearch;
    private HttpClient mockHttpClient;
    private HttpResponse<String> mockHttpResponse;

    @BeforeEach
    void setUp() throws IOException, InterruptedException {
        // Mock HttpClient and HttpResponse
        mockHttpClient = mock(HttpClient.class);
        mockHttpResponse = mock(HttpResponse.class);

        // Mocking a response from Impact API
        when(mockHttpResponse.body()).thenReturn("""
            {
              "results": [
                {
                  "name": "Product 1",
                  "imageUrl": "http://example.com/image1.jpg",
                  "category": {"name": "Electronics"},
                  "manufacturer": {"name": "Manufacturer1"},
                  "offers": [
                    {
                      "currentPrice": "25.00",
                      "currency": "USD",
                      "minCommissionPercentage": "2.0",
                      "maxCommissionPercentage": "10.0",
                      "program": {"logoUri": "http://example.com/logo1.png"},
                      "url": "http://example.com/product1",
                      "originalUrl": "http://example.com/original1",
                      "description": "Description for Product 1",
                      "programEarningsPerClick": "5.00",
                      "totalSalesVolume": "1000",
                      "lastUpdated": "2024-01-01T12:00:00.000Z",
                      "new": true
                    }
                  ]
                }
              ]
            }
        """);
        when(mockHttpResponse.statusCode()).thenReturn(200);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockHttpResponse);

        // Initialize the ImpactSearch object with mocks
        impactSearch = new ImpactSearch("123456789012", "barcode", "", data) {
            protected HttpClient getClient() {
                return mockHttpClient;
            }
        };
    }

    @Test
    void testToAppProductList() {
        String appProductListJson = impactSearch.toAppProductList();

        assertNotNull(appProductListJson);
        assertTrue(appProductListJson.contains("\"name\":\"Product 1\""));
        assertTrue(appProductListJson.contains("\"currentPrice\":\"25.00\""));
        assertTrue(appProductListJson.contains("\"currency\":\"USD\""));
    }

    @Test
    void testToList() {
        List<Product> productList = impactSearch.toList();

        assertNotNull(productList);
        assertEquals(1, productList.size());  // Ensure we have one product

        Product product = productList.get(0);
        assertEquals("Product 1", product.getName());
        assertEquals("25.00", product.getPrice());
        assertEquals("USD", product.getCurrency());
        assertEquals("http://example.com/image1.jpg", product.getImageURL());
        assertEquals("Manufacturer1", product.getName());
    }

    @Test
    void testNoProductsFound() throws IOException, InterruptedException {
        // Mocking a response from Impact API with no results
        when(mockHttpResponse.body()).thenReturn("""
            {
              "results": []
            }
        """);

        List<Product> productList = impactSearch.toList();
        assertNull(productList);  // Verify that no products were found
    }
}
