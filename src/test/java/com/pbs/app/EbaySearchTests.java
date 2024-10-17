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
import com.pbs.app.Search.EbaySearch.EbaySearch;

class EbaySearchTests {

    private EbaySearch ebaySearch;
    private HttpClient mockHttpClient;
    private HttpResponse<String> mockHttpResponse;

    @BeforeEach
    void setUp() throws IOException, InterruptedException {
        // Mock HttpClient and HttpResponse
        mockHttpClient = mock(HttpClient.class);
        mockHttpResponse = mock(HttpResponse.class);

        // Mocking a response from eBay API
        when(mockHttpResponse.body()).thenReturn("""
            {
              "itemSummaries": [
                {
                  "title": "Product 1",
                  "price": {"value": "25.00", "currency": "USD"},
                  "image": {"imageUrl": "http://example.com/image1.jpg"},
                  "categories": [{"categoryName": "Electronics"}],
                  "seller": {"username": "Seller1"},
                  "commission": "10%",
                  "itemHref": "http://example.com/product1",
                  "itemAffiliateWebUrl": "http://example.com/affiliate1",
                  "itemCreationDate": "2024-01-01T12:00:00.000Z",
                  "availableCoupons": false,
                  "topRatedBuyingExperience": true,
                  "priorityListing": true
                }
              ]
            }
        """);
        when(mockHttpResponse.statusCode()).thenReturn(200);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockHttpResponse);

        // Initialize the EbaySearch object with mocks
        ebaySearch = new EbaySearch("123456789012", "barcode") {
            protected HttpClient getClient() {
                return mockHttpClient;
            }
        };
    }

    @Test
    void testFetchProductDetailsUsingGtin_Success() throws IOException, InterruptedException {
        List<Product> productList = ebaySearch.toList(); // Invoke the method

        // Assert that the product list is not null
        assertNotNull(productList);
        assertEquals(1, productList.size());  // Check that one product was returned

        Product product = productList.get(0);
        assertEquals("Product 1", product.getName());
        assertEquals("25.00", product.getPrice());
        assertEquals("USD", product.getCurrency());
        assertEquals("Seller1", product.getName());
        assertEquals("http://example.com/image1.jpg", product.getImageURL());
        assertEquals("http://example.com/affiliate1", product.getAffiliate());
        assertEquals("10%", product.getCommissionRate());
    }

    @Test
    void testFetchProductDetailsUsingName_Success() throws IOException, InterruptedException {
        ebaySearch = new EbaySearch("Laptop", "name") {
            protected HttpClient getClient() {
                return mockHttpClient;
            }
        };

        List<Product> productList = ebaySearch.toList(); // Invoke the method

        // Assert that the product list is not null
        assertNotNull(productList);
        assertEquals(1, productList.size());  // Check that one product was returned

        Product product = productList.get(0);
        assertEquals("Product 1", product.getName());
        assertEquals("25.00", product.getPrice());
        assertEquals("USD", product.getCurrency());
        assertEquals("Seller1", product.getName());
        assertEquals("http://example.com/image1.jpg", product.getImageURL());
        assertEquals("http://example.com/affiliate1", product.getAffiliate());
        assertEquals("10%", product.getCommissionRate());
    }

    @Test
    void testToAppProductList() throws IOException, InterruptedException {
        String appProductListJson = ebaySearch.toAppProductList();

        assertNotNull(appProductListJson);
        assertTrue(appProductListJson.contains("\"title\":\"Product 1\""));
        assertTrue(appProductListJson.contains("\"price\":\"25.00\""));
        assertTrue(appProductListJson.contains("\"currency\":\"USD\""));
    }

    @Test
    void testNoProductsFound() throws IOException, InterruptedException {
        // Mocking a response from eBay API with no items
        when(mockHttpResponse.body()).thenReturn("""
            {
              "itemSummaries": []
            }
        """);

        List<Product> productList = ebaySearch.toList(); // Invoke the method
        assertNull(productList);  // Verify that no products were found
    }
}
