package com.pbs.app;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.pbs.app.Entities.Product;
import com.pbs.app.Repositories.AppProductList;

class AppProductListTests {

    private AppProductList appProductList;
    private Product product1;
    private Product product2;
    private Product product3;

    @BeforeEach
    void setUp() {
        product1 = new Product();
        product1.setProductID("P1");
        product1.setName("Product 1");
        product1.setPrice("20.00");
        product1.setBarcode("123");
        product1.setCurrency("USD");
        product1.setCommissionRate("10");

        product2 = new Product();
        product2.setProductID("P2");
        product2.setName("Product 2");
        product2.setPrice("15.00");
        product2.setBarcode("456");
        product2.setCurrency("USD");
        product2.setCommissionRate("20");

        product3 = new Product();
        product3.setProductID("P3");
        product3.setName("Product 3");
        product3.setPrice("30.00");
        product3.setBarcode("789");
        product3.setCurrency("USD");
        product3.setCommissionRate("5");

        appProductList = new AppProductList(Arrays.asList(product1, product2, product3));
    }

    @Test
    void testSortByPrice() {
        appProductList.sortByPrice();
        List<Product> sortedProducts = appProductList.getProducts();

        assertEquals(product2, sortedProducts.get(0));
        assertEquals(product1, sortedProducts.get(1));
        assertEquals(product3, sortedProducts.get(2));
    }

    @Test
    void testSortByCommission() {
        appProductList.sortByCommission();
        List<Product> sortedProducts = appProductList.getProducts();

        assertEquals(product2, sortedProducts.get(0)); // Highest commission
        assertEquals(product1, sortedProducts.get(1)); // Middle commission
        assertEquals(product3, sortedProducts.get(2)); // Lowest commission
    }

    @Test
    void testSynthesizePoints() {
        appProductList.synthesizePoints();
        Map<Product, Integer> productTally = appProductList.getProductTally();

        assertNotNull(productTally);
        assertTrue(productTally.containsKey(product1));
        assertTrue(productTally.containsKey(product2));
        assertTrue(productTally.containsKey(product3));

        assertTrue(productTally.get(product2) > productTally.get(product3)); // Product 2 should have higher points
    }

    @Test
    void testSortByValue() {
        appProductList.sortByValue();
        Map<Product, Integer> productTally = appProductList.getProductTally();

        assertNotNull(productTally);
        assertEquals(3, productTally.size());
        assertTrue(productTally.containsKey(product1));
    }

    @Test
    void testAddProducts() {
        Product newProduct = new Product();
        newProduct.setProductID("P4");
        newProduct.setName("Product 4");
        newProduct.setPrice("25.00");
        newProduct.setBarcode("987");
        newProduct.setCurrency("USD");
        newProduct.setCommissionRate("8");

        appProductList.addProducts(List.of(newProduct));

        List<Product> updatedProducts = appProductList.getProducts();
        assertEquals(4, updatedProducts.size());
        assertTrue(updatedProducts.contains(newProduct));
    }

    @SuppressWarnings("unchecked")
    @Test
    void testConvertToUSD() throws IOException, InterruptedException {
        // Mocking HttpClient and HttpResponse for the convertToUSD() method
        HttpClient mockClient = mock(HttpClient.class);
        HttpResponse<String> mockResponse = mock(HttpResponse.class);
        when(mockResponse.body()).thenReturn("""
            {
                "data": {
                    "USD": 1.0,
                    "EUR": 0.85,
                    "JPY": 110.0
                }
            }
        """);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        // Injecting the mocked HttpClient directly into the AppProductList class
        appProductList = new AppProductList(Arrays.asList(product1, product2, product3)) {
            public HttpClient createHttpClient() {
                return mockClient;
            }
        };

        appProductList.convertToUSD();

        assertEquals("USD", product1.getCurrency());
        assertEquals("USD", product2.getCurrency());
        assertEquals("USD", product3.getCurrency());
    }

    @Test
    void testFetchAndMapCurrencies() throws IOException, InterruptedException {
        // Mocking HttpClient and HttpResponse for the fetchAndMapCurrencies() method
        HttpClient mockClient = mock(HttpClient.class);
        HttpResponse<String> mockResponse = mock(HttpResponse.class);
        when(mockResponse.body()).thenReturn("""
            {
                "data": {
                    "USD": 1.0,
                    "EUR": 0.85,
                    "JPY": 110.0
                }
            }
        """);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        // Injecting the mocked HttpClient directly into the AppProductList class
        appProductList = new AppProductList(Arrays.asList(product1, product2, product3)) {
            public HttpClient createHttpClient() {
                return mockClient;
            }
        };

        AppProductList.Currencies currencies = appProductList.fetchAndMapCurrencies();

        assertEquals(1.0, currencies.getConversionRate("USD"));
        assertEquals(0.85, currencies.getConversionRate("EUR"));
        assertEquals(110.0, currencies.getConversionRate("JPY"));
    }
}
