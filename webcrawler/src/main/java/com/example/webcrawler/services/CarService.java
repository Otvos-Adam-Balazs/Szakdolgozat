package com.example.webcrawler.services;

import com.example.webcrawler.entities.Car;
import com.example.webcrawler.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Component
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public void saveCar(Car car){
        carRepository.save(car);
    }

//   public List<Car> getCarByMake(String makes){
//        return carRepository.findByMakes(makes);
//   }
//
//    public List<Car> getCarByMakeAndModel(String makes, String model) {
//        return carRepository.findByMakesAndModel(makes, model);
//    }

//    public List<Car> getCarByMakeAndModelAndFuel(String makes, String model, String fuelType) {
//        return carRepository.findByMakesAndModelAndFuelType(makes, model, fuelType);
//    }

    public List<Car> getCarByMakeAndModelAndFuel(String makes, String model, String fuelType, String minAge, String maxAge, String minEngine, String maxEngine, String minPrice, String maxPrice) {
        return carRepository.findAll(new Specification<Car>() {
            @Override
            public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Predicate p = cb.conjunction();
                if(!StringUtils.isEmpty(makes)){
                    p = cb.and(p, cb.equal(root.get("makes"), makes));
                }
                if(!StringUtils.isEmpty(model)){
                    p = cb.and(p, cb.equal(root.get("model"), model));
                }
                if(!StringUtils.isEmpty(fuelType)){
                    p = cb.and(p, cb.equal(root.get("fuelType"), fuelType));
                }
                if(!StringUtils.isEmpty(minAge) && !StringUtils.isEmpty(maxAge) && Integer.parseInt(minAge)<Integer.parseInt(maxAge)){
                    p = cb.and(p, cb.between(root.get("age"), Integer.parseInt(minAge),Integer.parseInt(maxAge)));
                }
                if(!StringUtils.isEmpty(minEngine) && !StringUtils.isEmpty(maxEngine) && Integer.parseInt(minEngine)<Integer.parseInt(maxEngine)){
                    p = cb.and(p, cb.between(root.get("engine"), Integer.parseInt(minEngine),Integer.parseInt(maxEngine)));
                }
                if(!StringUtils.isEmpty(minPrice) && !StringUtils.isEmpty(maxPrice) && Integer.parseInt(minPrice)<Integer.parseInt(maxPrice)){
                    p = cb.and(p, cb.between(root.get("price"), Integer.parseInt(minPrice), Integer.parseInt(maxPrice)));
                }
                return p;
            }
        });
    }

//    public List<Car> getCarByNameAndModelAndFuelAndAge(String makes, String model, String fuelType, String minAge, String maxAge) {
//        return carRepository.findByMakesAndModelAndFuelTypeAndAge(makes,model,fuelType,minAge,maxAge);
//    }
//
//    public List<Car> getCarByAge(String minAge, String maxAge) {
//        return carRepository.findReferenceAgeBetween(minAge,maxAge);
//    }
}

