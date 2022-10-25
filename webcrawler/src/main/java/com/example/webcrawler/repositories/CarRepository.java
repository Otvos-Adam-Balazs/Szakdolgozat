package com.example.webcrawler.repositories;

import com.example.webcrawler.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CarRepository extends JpaRepository <Car, Integer>, JpaSpecificationExecutor<Car> {

       @Query("SELECT DISTINCT car.makes FROM Car car ORDER BY car.makes ")
       public String[] getCarMakes();

       @Query("SELECT DISTINCT car.model FROM Car car WHERE car.makes =:makes ")
       public String[] getModelByName(String makes);

}
