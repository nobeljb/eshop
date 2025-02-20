package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");
        product.setProductQuantity(10);

        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void testGetHome() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("homepage"));
    }

    @Test
    void testCreateProductForm() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"));
    }

    // ✅ Nama & kuantitas valid → Produk berhasil dibuat
    @Test
    void testCreateProduct_Success() throws Exception {
        mockMvc.perform(post("/product/create")
                        .param("productName", "New Product")
                        .param("productQuantity", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        verify(productService, times(1)).create(any(Product.class));
    }

    // ✅ Nama kosong, kuantitas valid → Error pada nama
    @Test
    void testCreateProduct_NameEmpty() throws Exception {
        mockMvc.perform(post("/product/create")
                        .param("productName", "") // Nama kosong
                        .param("productQuantity", "10")) // Kuantitas valid
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeExists("nameError"));

        verify(productService, never()).create(any(Product.class)); // Tidak boleh memanggil service.create()
    }

    // ✅ Kuantitas tidak valid, nama valid → Error pada kuantitas
    @Test
    void testCreateProduct_InvalidQuantity() throws Exception {
        mockMvc.perform(post("/product/create")
                        .param("productName", "Laptop") // Nama valid
                        .param("productQuantity", "0")) // Kuantitas tidak valid
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeExists("quantityError"));

        verify(productService, never()).create(any(Product.class)); // Tidak boleh memanggil service.create()
    }

    // ✅ Nama kosong & kuantitas tidak valid → Muncul dua error
    @Test
    void testCreateProduct_EmptyNameAndInvalidQuantity() throws Exception {
        mockMvc.perform(post("/product/create")
                        .param("productName", "")  // Nama kosong
                        .param("productQuantity", "-5")) // Kuantitas tidak valid
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeExists("nameError"))
                .andExpect(model().attributeExists("quantityError"));

        verify(productService, never()).create(any(Product.class)); // Tidak boleh memanggil service.create()
    }

    @Test
    void testListProducts() throws Exception {
        List<Product> productList = Arrays.asList(product, new Product());
        when(productService.findAll()).thenReturn(productList);

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("productList"))
                .andExpect(model().attributeExists("products"));

        verify(productService, times(1)).findAll();
    }

    @Test
    void testEditProductForm_ProductExists() throws Exception {
        when(productService.findById("1")).thenReturn(product);

        mockMvc.perform(get("/product/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeExists("product"));

        verify(productService, times(1)).findById("1");
    }

    @Test
    void testEditProductForm_ProductNotFound() throws Exception {
        when(productService.findById("1")).thenReturn(null);

        mockMvc.perform(get("/product/edit/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        verify(productService, times(1)).findById("1");
    }

    @Test
    void testEditProduct_Success() throws Exception {
        mockMvc.perform(post("/product/edit")
                        .param("productId", "1")
                        .param("productName", "Updated Product")
                        .param("productQuantity", "15"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        verify(productService, times(1)).update(any(Product.class));
    }

    @Test
    void testDeleteProduct() throws Exception {
        mockMvc.perform(get("/product/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        verify(productService, times(1)).delete("1");
    }

    @Test
    void testCreateProduct_NameIsNull() throws Exception {
        mockMvc.perform(post("/product/create")
                        .param("productName", (String) null) // Simulasi NULL
                        .param("productQuantity", "5"))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeExists("nameError"));

        verify(productService, never()).create(any(Product.class));
    }
}
