package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");
    }

    @Test
    void testCreateProduct() {
        // Act
        Product createdProduct = productService.create(product);

        // Assert
        assertNotNull(createdProduct);
        assertEquals(product, createdProduct);
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAllProducts() {
        // Arrange
        List<Product> mockProducts = Arrays.asList(product, new Product());
        Iterator<Product> iteratorMock = mockProducts.iterator();
        when(productRepository.findAll()).thenReturn(iteratorMock);

        // Act
        List<Product> result = productService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindProductById() {
        // Arrange
        when(productRepository.findById("1")).thenReturn(product);

        // Act
        Product foundProduct = productService.findById("1");

        // Assert
        assertNotNull(foundProduct);
        assertEquals("1", foundProduct.getProductId());
        verify(productRepository, times(1)).findById("1");
    }

    @Test
    void testUpdateProduct() {
        // Arrange
        when(productRepository.update(product)).thenReturn(product);

        // Act
        Product updatedProduct = productService.update(product);

        // Assert
        assertNotNull(updatedProduct);
        assertEquals(product, updatedProduct);
        verify(productRepository, times(1)).update(product);
    }

    @Test
    void testDeleteProduct() {
        // Act
        productService.delete("1");

        // Assert
        verify(productRepository, times(1)).delete("1");
    }
}
