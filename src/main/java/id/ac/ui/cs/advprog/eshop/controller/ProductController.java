package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.CarServiceImpl;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/")
    public String getHome(Model model) {
        return "homepage";
    }

    @GetMapping("/product/create")
    public String createProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "createProduct";
    }

    @PostMapping("/product/create")
    public String createProduct(@ModelAttribute Product product, Model model) {
        boolean hasError = false;

        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            model.addAttribute("nameError", "Nama produk tidak boleh kosong.");
            hasError = true;
        }

        if (product.getProductQuantity() <= 0) {
            model.addAttribute("quantityError", "Kuantitas harus lebih dari 0.");
            hasError = true;
        }

        if (hasError) {
            return "createProduct"; // Kembali ke halaman form dengan pesan error
        }

        service.create(product);
        return "redirect:/product/list";
    }

    @GetMapping("/product/list")
    public String listProducts(Model model) {
        List<Product> allProducts = service.findAll();
        model.addAttribute("products", allProducts);
        return "productList";
    }

    // --- Fitur Edit ---

    // Menampilkan form edit produk berdasarkan id
    @GetMapping("/product/edit/{id}")
    public String editProductForm(@PathVariable("id") String id, Model model) {
        Product product = service.findById(id);
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
        service.update(product);
        return "redirect:/product/list";
    }

    // --- Fitur Delete ---

    // Menghapus produk berdasarkan id
    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") String id) {
        service.delete(id);
        return "redirect:/product/list";
    }
}

@Controller
@RequestMapping("/car")
class CarController extends ProductController {
    @Autowired
    private CarServiceImpl carservice;

    @GetMapping("/createCar")
    public String createCarPage(Model model) {
        Car car = new Car();
        model.addAttribute("car", car);
        return "createCar";
    }

    @PostMapping("/createCar")
    public String createCarPost(@ModelAttribute Car car, Model model) {
        carservice.create(car);
        return "redirect:listCar";
    }

    @GetMapping("/listCar")
    public String carListPage(Model model) {
        List<Car> allCars = carservice.findAll();
        model.addAttribute("cars", allCars);
        return "carList";
    }

    @GetMapping("/editCar/{carId}")
    public String editCarPage(@PathVariable String carId, Model model) {
        Car car = carservice.findById(carId);
        model.addAttribute("car", car);
        return "editCar";
    }

    @PostMapping("/editCar")
    public String editCarPost(@ModelAttribute Car car, Model model) {
        System.out.println(car.getCarId());
        carservice.update(car.getCarId(), car);

        return "redirect:listCar";
    }

    @PostMapping("/deleteCar")
    public String deleteCar(@RequestParam("carId") String carId) {
        carservice.deleteCarById(carId);
        return "redirect:listCar";
    }

}