package com.example.webcrawler.repositories;

import com.example.webcrawler.entities.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface DataRepository extends JpaRepository<Data, Integer> {


    @Query("SELECT makes, COUNT(d) FROM Data d Where d.date BETWEEN :start and :end group by makes")
    public String[] findByDateBetween(Date start, Date end);

}
