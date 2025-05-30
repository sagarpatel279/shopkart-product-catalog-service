package com.shopkart.productcatalogueservice.controllers;

import com.shopkart.productcatalogueservice.dtos.records.ApiResponse;
import com.shopkart.productcatalogueservice.dtos.records.ProductResponseRecord;
import com.shopkart.productcatalogueservice.models.Category;
import com.shopkart.productcatalogueservice.models.Product;
import com.shopkart.productcatalogueservice.services.ProductService;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

        @Mock
        private ProductService productService;

        @InjectMocks
        private ProductController productController;

        // Positive Cases
        @Test
        @DisplayName("Should return 200 OK with single product")
        void testGetAllProducts_ReturnsSingleProduct_WithOK() {
            // Arrange
            Product product = createProduct(1L, "Product 1", "Description", 99.99, "Category 1");
            when(productService.getAllProducts()).thenReturn(List.of(product));

            // Act
            ResponseEntity<ApiResponse<List<ProductResponseRecord>>> response =
                    productController.getAllProducts();

            // Assert
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());

            ApiResponse<List<ProductResponseRecord>> apiResponse = response.getBody();
            assertNotNull(apiResponse);
            assertEquals(1, apiResponse.data().size());
            assertEquals("Products retrieved successfully", apiResponse.message());
            assertEquals(HttpStatus.OK.value(), apiResponse.status());

            ProductResponseRecord responseRecord = apiResponse.data().get(0);
            assertEquals(1L, responseRecord.id());
            assertEquals("Product 1", responseRecord.productName());
            assertEquals("Description", responseRecord.description());
            assertEquals(99.99, responseRecord.price());
            assertEquals("Category 1", responseRecord.categoryName());
        }

        @Test
        @DisplayName("Should return 200 OK with multiple products")
        void getAllProducts_ReturnsMultipleProducts_WithOK() {
            // Arrange
            List<Product> products = Arrays.asList(
                    createProduct(1L, "Product 1", "Desc 1", 99.99, "Category 1"),
                    createProduct(2L, "Product 2", "Desc 2", 149.99, "Category 2"),
                    createProduct(3L, "Product 3", "Desc 3", 199.99, "Category 1")
            );
            when(productService.getAllProducts()).thenReturn(products);

            // Act
            ResponseEntity<ApiResponse<List<ProductResponseRecord>>> response =
                    productController.getAllProducts();

            // Assert
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());

            ApiResponse<List<ProductResponseRecord>> apiResponse = response.getBody();
            assertNotNull(apiResponse);
            assertEquals(3, apiResponse.data().size());
            assertEquals("Products retrieved successfully", apiResponse.message());
            assertEquals(HttpStatus.OK.value(), apiResponse.status());
        }

        // Negative Cases
        @Test
        @DisplayName("Should return 204 NO_CONTENT when empty list")
        void getAllProducts_ReturnsNoContent_WhenEmptyList() {
            // Arrange
            when(productService.getAllProducts()).thenReturn(Collections.emptyList());

            // Act
            ResponseEntity<ApiResponse<List<ProductResponseRecord>>> response =
                    productController.getAllProducts();

            // Assert
            assertNotNull(response);
            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

            ApiResponse<List<ProductResponseRecord>> apiResponse = response.getBody();
            assertNotNull(apiResponse);
            assertTrue(apiResponse.data().isEmpty());
            assertEquals("No products found", apiResponse.message());
            assertEquals(HttpStatus.NO_CONTENT.value(), apiResponse.status());
        }

        @Test
        @DisplayName("Should return 500 when service returns null")
        void getAllProducts_ReturnsInternalServerError_WhenServiceReturnsNull() {
            // Arrange
            when(productService.getAllProducts()).thenReturn(null);

            // Act
            ResponseEntity<ApiResponse<List<ProductResponseRecord>>> response =
                    productController.getAllProducts();

            // Assert
            assertNotNull(response);
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

            ApiResponse<List<ProductResponseRecord>> apiResponse = response.getBody();
            assertNotNull(apiResponse);
            assertNull(apiResponse.data());
            assertEquals("Error retrieving products", apiResponse.message());
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), apiResponse.status());
        }

        // Edge Cases
        @Test
        @DisplayName("Should handle products with null values")
        void getAllProducts_HandlesProductsWithNullValues() {
            // Arrange
            Product product = new Product();
            product.setId(1L);
            // All other fields are null
            when(productService.getAllProducts()).thenReturn(List.of(product));

            // Act
            ResponseEntity<ApiResponse<List<ProductResponseRecord>>> response =
                    productController.getAllProducts();

            // Assert
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());

            ApiResponse<List<ProductResponseRecord>> apiResponse = response.getBody();
            assertNotNull(apiResponse);
            assertEquals(1, apiResponse.data().size());

            ProductResponseRecord record = apiResponse.data().get(0);
            assertEquals(1L, record.id());
            assertNull(record.productName());
            assertNull(record.description());
            assertNull(record.price());
            assertNull(record.categoryName());
        }

        @Test
        @DisplayName("Should handle large number of products")
        void getAllProducts_HandlesLargeNumberOfProducts() {
            // Arrange
            List<Product> products = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                products.add(createProduct((long) i, "Product " + i,
                        "Desc " + i, 99.99 + i, "Category " + (i % 5)));
            }
            when(productService.getAllProducts()).thenReturn(products);

            // Act
            ResponseEntity<ApiResponse<List<ProductResponseRecord>>> response =
                    productController.getAllProducts();

            // Assert
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(1000, response.getBody().data().size());
        }

        @Test
        @DisplayName("Should handle special characters in product data")
        void getAllProducts_HandlesSpecialCharacters() {
            // Arrange
            Product product = createProduct(1L, "Product!@#$%",
                    "Description with üñíçødé", 99.99, "Category & Special < > ' \"");
            when(productService.getAllProducts()).thenReturn(List.of(product));

            // Act
            ResponseEntity<ApiResponse<List<ProductResponseRecord>>> response =
                    productController.getAllProducts();

            // Assert
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            ProductResponseRecord record = response.getBody().data().get(0);
            assertEquals("Product!@#$%", record.productName());
            assertEquals("Description with üñíçødé", record.description());
            assertEquals("Category & Special < > ' \"", record.categoryName());
        }

        @Test
        @DisplayName("Should verify service interaction")
        void getAllProducts_VerifyServiceInteraction() {
            // Arrange
            when(productService.getAllProducts()).thenReturn(Collections.emptyList());

            // Act
            productController.getAllProducts();

            // Assert
            verify(productService, times(1)).getAllProducts();
            verifyNoMoreInteractions(productService);
        }

        private Product createProduct(Long id, String name, String description,
                                      Double price, String categoryName) {
            Product product = new Product();
            product.setId(id);
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);

            Category category = new Category(categoryName);
            product.setCategory(category);

            return product;
        }

    @Test
    @DisplayName("Should complete request within acceptable time")
    void getAllProducts_PerformanceTest() {
        // Arrange
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            products.add(createProduct((long) i, "Product " + i,
                    "Desc " + i, 99.99 + i, "Category " + (i % 5)));
        }
        when(productService.getAllProducts()).thenReturn(products);

        // Act
        long startTime = System.currentTimeMillis();
        ResponseEntity<ApiResponse<List<ProductResponseRecord>>> response =
                productController.getAllProducts();
        long endTime = System.currentTimeMillis();

        // Assert
        assertTrue((endTime - startTime) < 1000); // Should complete within 1 second
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Should handle concurrent requests")
    void getAllProducts_ConcurrentAccess() throws InterruptedException {
        // Arrange
        when(productService.getAllProducts())
                .thenReturn(List.of(createProduct(1L, "Test", "Desc", 99.99, "Category")));
        int numberOfThreads = 10;
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        AtomicInteger successCount = new AtomicInteger(0);

        // Act
        for (int i = 0; i < numberOfThreads; i++) {
            new Thread(() -> {
                try {
                    ResponseEntity<ApiResponse<List<ProductResponseRecord>>> response =
                            productController.getAllProducts();
                    if (response.getStatusCode() == HttpStatus.OK) {
                        successCount.incrementAndGet();
                    }
                } finally {
                    latch.countDown();
                }
            }).start();
        }

        // Assert
        latch.await(5, TimeUnit.SECONDS);
        assertEquals(numberOfThreads, successCount.get());
    }

}