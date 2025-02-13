package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.List;

public interface ProductService {
    public Product create(Product product);
    public List<Product> findAll();

    // Method tambahan untuk fitur edit dan delete
    public Product findById(String id);
    public Product update(Product product);
    public void delete(String id);
}