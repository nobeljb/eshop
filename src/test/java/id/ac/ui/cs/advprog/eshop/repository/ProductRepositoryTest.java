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
        productRepository = new ProductRepository(); // Ensure fresh repository for each test
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

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

    // ================ NEGATIVE SCENARIOS ====================

    @Test
    void testCreateProductWithNullOrEmptyId() {
        Product product = new Product();
        product.setProductId(null);  // Tidak di-set ID-nya
        product.setProductName("Generated ID Product");
        product.setProductQuantity(30);

        Product savedProduct = productRepository.create(product);
        assertNotNull(savedProduct.getProductId(), "Product ID should be generated if null");
        assertFalse(savedProduct.getProductId().trim().isEmpty(), "Generated Product ID should not be empty");
    }

    @Test
    void testUpdateNonExistentProduct() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("non-existent-id");
        updatedProduct.setProductName("Does not exist");
        updatedProduct.setProductQuantity(50);

        Product result = productRepository.update(updatedProduct);
        assertNull(result, "Updating a non-existent product should return null");
    }

    @Test
    void testDeleteNonExistentProduct() {
        int initialSize = getRepositorySize();
        productRepository.delete("non-existent-id");
        int finalSize = getRepositorySize();

        assertEquals(initialSize, finalSize, "Deleting a non-existent product should not change the repository size");
    }

    @Test
    void testFindDeletedProduct() {
        Product product = new Product();
        product.setProductId("temp-id");
        product.setProductName("Temporary Product");
        product.setProductQuantity(5);
        productRepository.create(product);

        productRepository.delete("temp-id");
        assertNull(productRepository.findById("temp-id"), "Deleted product should not be found");
    }

    // Helper method untuk menghitung jumlah produk di repository
    private int getRepositorySize() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEachRemaining(products::add);
        return products.size();
    }

    @Test
    void testUpdateFirstProduct() {
        Product product1 = new Product();
        product1.setProductId("first-id");
        product1.setProductName("First Product");
        product1.setProductQuantity(10);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("second-id");
        product2.setProductName("Second Product");
        product2.setProductQuantity(20);
        productRepository.create(product2);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("first-id");
        updatedProduct.setProductName("Updated First Product");
        updatedProduct.setProductQuantity(99);

        productRepository.update(updatedProduct);

        Product retrieved = productRepository.findById("first-id");
        assertNotNull(retrieved);
        assertEquals("Updated First Product", retrieved.getProductName());
        assertEquals(99, retrieved.getProductQuantity());
    }

    @Test
    void testUpdateLastProduct() {
        Product product1 = new Product();
        product1.setProductId("first-id");
        product1.setProductName("First Product");
        product1.setProductQuantity(10);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("second-id");
        product2.setProductName("Second Product");
        product2.setProductQuantity(20);
        productRepository.create(product2);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("second-id");
        updatedProduct.setProductName("Updated Second Product");
        updatedProduct.setProductQuantity(50);

        productRepository.update(updatedProduct);

        Product retrieved = productRepository.findById("second-id");
        assertNotNull(retrieved);
        assertEquals("Updated Second Product", retrieved.getProductName());
        assertEquals(50, retrieved.getProductQuantity());
    }

    @Test
    void testCreateProductWithEmptyId() {
        Product product = new Product();
        product.setProductId("");  // ID kosong
        product.setProductName("No ID Product");
        product.setProductQuantity(25);

        Product savedProduct = productRepository.create(product);
        assertNotNull(savedProduct.getProductId(), "Product ID should be generated if empty");
        assertFalse(savedProduct.getProductId().trim().isEmpty(), "Generated Product ID should not be empty");
    }

    @Test
    void testCreateProductWithSpacesAsId() {
        Product product = new Product();
        product.setProductId("   ");  // ID hanya spasi
        product.setProductName("Space ID Product");
        product.setProductQuantity(30);

        Product savedProduct = productRepository.create(product);
        assertNotNull(savedProduct.getProductId(), "Product ID should be generated if only spaces");
        assertFalse(savedProduct.getProductId().trim().isEmpty(), "Generated Product ID should not be empty");
    }
}
