package com.example.webcrawler.controller;

import com.example.webcrawler.dto.StatisticDto;
import com.example.webcrawler.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.ParseException;


@RestController
public class DataController {
    @Autowired
    DataService dataService;

    @GetMapping("/getData")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public StatisticDto[] getData(@RequestParam(name="startDate") String startDate ,
                                @RequestParam(name="endDate") String endDate) throws ParseException {
        Date start = Date.valueOf(startDate);
        Date end = Date.valueOf(endDate);
        System.out.println(start);
       return dataService.getDatas(start, end);
    }

    @GetMapping("/postData")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public void setData(@RequestParam(name="make")String makes,
                        @RequestParam(name="name")String name){
        dataService.setData(makes,name);
    }

}
