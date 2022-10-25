package com.example.webcrawler.services;

import com.example.webcrawler.dto.StatisticDto;
import com.example.webcrawler.entities.Data;
import com.example.webcrawler.repositories.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.sql.Date;

@Service
public class DataService {
    @Autowired
    DataRepository dataRepository;

    public void setData(String makes) {
        System.out.println(makes);
        if(!ObjectUtils.isEmpty(makes)){
            Data data = new Data();
            data.setMakes(makes);
            data.setDate(Date.valueOf(LocalDate.now()));
            dataRepository.save(data);
        }else{
        }

    }
    public StatisticDto[] getDatas(Date start, Date end){
        String[] datas= dataRepository.findByDateBetween(start,end);
        StatisticDto[] statisticDtos = new StatisticDto[datas.length];

        for(int i=0; i<datas.length; i++){
            String data[] = datas[i].split(",");
            statisticDtos[i] = new StatisticDto();
            statisticDtos[i].setMakes(data[0]);
            statisticDtos[i].setSearchNumber(data[1]);
        }
        return statisticDtos;
    }
}
