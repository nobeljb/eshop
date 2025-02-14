package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {

    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository(); // Ensure a fresh repository for each test
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Convert iterator to list for easier assertions
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEachRemaining(products::add);

        assertEquals(1, products.size());
        Product savedProduct = products.get(0);
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext(), "findAll() should return empty iterator");
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEachRemaining(products::add);

        assertEquals(2, products.size());
        assertEquals(product1.getProductId(), products.get(0).getProductId());
        assertEquals(product2.getProductId(), products.get(1).getProductId());
    }

    @Test
    void testFindById() {
        Product product = new Product();
        product.setProductId("test-id");
        product.setProductName("Test Product");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product foundProduct = productRepository.findById("test-id");
        assertNotNull(foundProduct, "findById should return a product");
        assertEquals("Test Product", foundProduct.getProductName());

        assertNull(productRepository.findById("nonexistent-id"), "findById should return null for unknown ID");
    }

    @Test
    void testUpdateProduct() {
        Product product = new Product();
        product.setProductId("update-id");
        product.setProductName("Old Name");
        product.setProductQuantity(5);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("update-id");
        updatedProduct.setProductName("New Name");
        updatedProduct.setProductQuantity(15);
        productRepository.update(updatedProduct);

        Product retrievedProduct = productRepository.findById("update-id");
        assertNotNull(retrievedProduct, "Updated product should still exist");
        assertEquals("New Name", retrievedProduct.getProductName());
        assertEquals(15, retrievedProduct.getProductQuantity());
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product();
        product.setProductId("delete-id");
        product.setProductName("To be deleted");
        product.setProductQuantity(20);
        productRepository.create(product);

        productRepository.delete("delete-id");
        assertNull(productRepository.findById("delete-id"), "Deleted product should not be found");
    }
}
