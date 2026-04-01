package com.shwaaz.TrainBetweenStation.controller;

import com.shwaaz.TrainBetweenStation.entity.Train;
import com.shwaaz.TrainBetweenStation.service.TrainService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trains") //base url
public class TrainController {

    // ek he var ho to autowire lagane ki jrurat nhi hai

    private TrainService trainService ;
   public TrainController(TrainService trainService){
        this.trainService = trainService ;
    }


    @GetMapping
    public List<Train> getAllTrains(){
        return trainService.getAllService();
    }

    @PostMapping
    public Train addTrain(@RequestBody Train train){
        return trainService.addTrain(train) ;
    }

}
