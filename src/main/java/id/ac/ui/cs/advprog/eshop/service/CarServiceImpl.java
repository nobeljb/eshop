package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CarServiceImpl implements CarReadService, CarWriteService {
    @Autowired
    private CarRepository carRepository;

    @Override
    public Car create(Car car) {
        // TODO Auto-generated method stub
        carRepository.createCar(car);
        return car;
    }

    @Override
    public List<Car> findAll() {
        Iterator<Car> carIterator = carRepository.findAll();
        List<Car> allcar = new ArrayList<>();
        carIterator.forEachRemaining(allcar::add);
        return allcar;
    }

    @Override
    public Car findById(String id) {
        return carRepository.findById(id);
    }

    @Override
    public void update(String carId, Car car) {
        // TODO Auto-generated method stub
        carRepository.update(carId, car);
    }

    @Override
    public void deleteCarById(String carId) {
        // TODO Auto-generated method stub
        carRepository.delete(carId);
    }
}