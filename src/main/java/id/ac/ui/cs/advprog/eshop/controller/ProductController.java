package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/product/create")
    public String createProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "createProduct"; // Pastikan view ini ada (misalnya createProduct.html)
    }

    @PostMapping("/product/create")
    public String createProduct(@ModelAttribute Product product, Model model) {
        service.create(product);
        return "redirect:list";
    }

    @GetMapping("/product/list")
    public String listProducts(Model model) {
        List<Product> allProducts = service.findAll();
        model.addAttribute("products", allProducts);
        return "productList"; // Pastikan view ini ada (misalnya productList.html)
    }

    // --- Fitur Edit ---

    // Menampilkan form edit produk berdasarkan id
    @GetMapping("/product/edit/{id}")
    public String editProductForm(@PathVariable("id") String id, Model model) {
        Product product = service.findById(id); // Pastikan service menyediakan method ini
        if (product == null) {
            // Jika produk tidak ditemukan, redirect ke halaman list
            return "redirect:/product/list";
        }
        model.addAttribute("product", product);
        return "createProduct";
    }

    // Menangani submit form edit produk
    @PostMapping("/product/edit")
    public String editProduct(@ModelAttribute Product product) {
        service.update(product); // Pastikan service menyediakan method update
        return "redirect:/product/list";
    }
}
