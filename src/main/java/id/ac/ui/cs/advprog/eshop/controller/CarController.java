package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarReadService;
import id.ac.ui.cs.advprog.eshop.service.CarWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/car")
class CarController{
    @Autowired
    private CarReadService carReadService;

    @Autowired
    private CarWriteService carWriteService;


    @GetMapping("/createCar")
    public String createCarPage(Model model) {
        Car car = new Car();
        model.addAttribute("car", car);
        return "createCar";
    }

    @PostMapping("/createCar")
    public String createCarPost(@ModelAttribute Car car, Model model) {
        boolean hasError = false;

        if (car.getCarName() == null || car.getCarName().trim().isEmpty()) {
            model.addAttribute("nameError", "Nama mobil tidak boleh kosong.");
            hasError = true;
        }

        if (car.getCarColor() == null || car.getCarColor().trim().isEmpty()) {
            model.addAttribute("colorError", "Warna mobil tidak boleh kosong.");
            hasError = true;
        }

        if (car.getCarQuantity() <= 0) {
            model.addAttribute("quantityError", "Kuantitas mobil harus lebih dari 0.");
            hasError = true;
        }

        if (hasError) {
            return "createCar"; // Kembali ke halaman form dengan pesan error
        }

        carWriteService.create(car);
        return "redirect:/car/listCar";
    }

    @GetMapping("/listCar")
    public String carListPage(Model model) {
        List<Car> allCars = carReadService.findAll();
        model.addAttribute("cars", allCars);
        return "carList";
    }

    @GetMapping("/editCar/{carId}")
    public String editCarPage(@PathVariable String carId, Model model) {
        Car car = carReadService.findById(carId);
        model.addAttribute("car", car);
        return "editCar";
    }

    @PostMapping("/editCar")
    public String editCarPost(@ModelAttribute Car car, Model model) {
        System.out.println(car.getCarId());
        carWriteService.update(car.getCarId(), car);

        return "redirect:/car/listCar";
    }

    @PostMapping("/deleteCar")
    public String deleteCar(@RequestParam("carId") String carId) {
        carWriteService.deleteCarById(carId);
        return "redirect:/car/listCar";
    }

}
