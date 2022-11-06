package com.example.webcrawler.controller;

import com.example.webcrawler.dto.MakesDto;
import com.example.webcrawler.dto.ModelDto;
import com.example.webcrawler.entities.Car;
import com.example.webcrawler.repositories.CarRepository;
import com.example.webcrawler.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
public class CarController {
    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    @PostMapping("/set")
    @PreAuthorize("hasRole('ADMIN')")
    public String getData() throws InterruptedException, IOException {
      return carService.saveCar();

    }

    @GetMapping("/get")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public List<Car> getCars(@RequestParam(name="makes") String makes ,
                                                 @RequestParam(name="model") String model,
                                                 @RequestParam("fuelType") String fuelType,
                                                 @RequestParam("minAge") String minAge,
                                                 @RequestParam("maxAge") String maxAge,
                                                 @RequestParam("minEngine") String minEngine,
                                                 @RequestParam("maxEngine") String maxEngine,
                                                 @RequestParam("minPrice") String minPrice,
                                                 @RequestParam("maxPrice") String maxPrice){
        return carService.getCars(makes, model, fuelType, minAge,maxAge,minEngine,maxEngine,minPrice,maxPrice);
    }

    @GetMapping("/getMakes")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public MakesDto[] getCarMakes(){
        return  carService.getCarMakes();
    }

    @GetMapping("/getModels")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ModelDto[] getModelByName(@RequestParam(name = "makes") String makes){
        return  carService.getModelByName(makes);
    }


}
