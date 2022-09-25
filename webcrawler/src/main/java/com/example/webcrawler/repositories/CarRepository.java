package com.example.webcrawler.repositories;

import com.example.webcrawler.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository <Car, Integer>, JpaSpecificationExecutor<Car> {

//     public List<Car> findByMakes(String makes);
//
//     @Query("SELECT car FROM Car car WHERE car.makes = :makes AND (:model is null or car.model= :model)")
//     public List<Car> findByMakesAndModel(@Param("makes") String makes,@Param("model") String model);
//
//     public List<Car> findByMakesAndModelAndFuelType(String makes, String model, String fuelType);
//
//     @Query("SELECT car FROM Car car WHERE car.makes = :makes AND car.model= :model AND car.fuelType = :fuelType AND" +
//             " car.age BETWEEN :minAge and :maxAge")
//     public List<Car> findByMakesAndModelAndFuelTypeAndAge(String makes, String model, String fuelType, String minAge,String maxAge);
//
//     @Query("SELECT car FROM Car car WHERE car.makes = :makes AND car.model= :model AND car.fuelType = :fuelType AND" +
//             " car.age BETWEEN :minAge and :maxAge")
//     public List<Car> findByMakesAndModelAndFuelTypeAndAgeAndPrice(String makes, String model, String fuelType, String minAge,String maxAge);
//
//     @Query("SELECT car FROM Car car WHERE car.age BETWEEN :minAge AND :maxAge")
//     public List<Car> findReferenceAgeBetween(String minAge, String maxAge);

}
