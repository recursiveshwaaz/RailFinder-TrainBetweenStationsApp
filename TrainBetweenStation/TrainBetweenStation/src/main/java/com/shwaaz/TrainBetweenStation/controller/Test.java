package com.shwaaz.TrainBetweenStation.controller;

import com.shwaaz.TrainBetweenStation.entity.Station;
import com.shwaaz.TrainBetweenStation.entity.Train;
import com.shwaaz.TrainBetweenStation.entity.TrainSchedule;
import com.shwaaz.TrainBetweenStation.repo.StationRepository;
import com.shwaaz.TrainBetweenStation.repo.TrainRepsitory;
import com.shwaaz.TrainBetweenStation.repo.TrainScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class Test {

    @Autowired
    StationRepository stationRepository ;

    @Autowired
    TrainRepsitory trainRepsitory ;

    @Autowired
    TrainScheduleRepository trainScheduleRepository ;


    @GetMapping
    public void test(){
        Station delhi = new Station(null,"New Delhi","NDLS") ;
        Station mumbai = new Station(null,"Mumbai central","CST") ;
        Station kolkata = new Station(null,"Kolkata","KOAA") ;
        Station banglore = new Station(null,"Banglore","SMVT") ;

        stationRepository.saveAll(List.of(delhi,mumbai,kolkata,banglore));

        Train rajdhani = new Train(null,"Rajdhani Express","23245",null) ;
        Train dhurunto = new Train(null,"Dhurunto Express","23342",null) ;
        Train shatabdi = new Train(null,"Shatabdi Express","23888",null) ;
        Train angaexpress = new Train(null,"Anga Express","99882",null) ;

        trainRepsitory.saveAll(List.of(rajdhani,dhurunto,shatabdi,angaexpress)) ;


        TrainSchedule sc1 = new TrainSchedule(null,rajdhani,delhi,mumbai,"6:00","14:00") ;
        TrainSchedule sc2 = new TrainSchedule(null,dhurunto,mumbai,kolkata,"8:00","16:00") ;
        TrainSchedule sc3 = new TrainSchedule(null,shatabdi,kolkata,banglore,"10:00","18:00") ;
        TrainSchedule sc4 = new TrainSchedule(null,angaexpress,banglore,delhi,"12:00","22:00") ;

        trainScheduleRepository.saveAll(List.of(sc1,sc2,sc3,sc4)) ;

        System.out.println("Data Inserted in database");
    }
}
