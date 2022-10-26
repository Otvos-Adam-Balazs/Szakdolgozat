package com.example.webcrawler.services;

import com.example.webcrawler.dto.StatisticDto;
import com.example.webcrawler.entities.Data;
import com.example.webcrawler.entities.User;
import com.example.webcrawler.repositories.DataRepository;
import com.example.webcrawler.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.sql.Date;

@Service
public class DataService  {
    @Autowired
    DataRepository dataRepository;

    @Autowired
    UserRepository userRepository;


    public void setData(String makes , String name) {
        User user = new User();
        if(!ObjectUtils.isEmpty((name))){
             user= userRepository.findUserByLogin(name);
        }


        if(!ObjectUtils.isEmpty(makes)){
            Data data = new Data();
            data.setMakes(makes);
            data.setDate(Date.valueOf(LocalDate.now()));
            data.setUser(user);
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
