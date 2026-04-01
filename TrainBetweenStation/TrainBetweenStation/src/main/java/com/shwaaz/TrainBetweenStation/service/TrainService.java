package com.shwaaz.TrainBetweenStation.service;

import com.shwaaz.TrainBetweenStation.entity.Train;
import com.shwaaz.TrainBetweenStation.repo.TrainRepsitory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainService {

    private TrainRepsitory trainRepsitory ;
    public TrainService(TrainRepsitory trainRepsitory){
        this.trainRepsitory = trainRepsitory ;
    }


    public List<Train> getAllService() {
        return trainRepsitory.findAll() ;
    }

    public Train addTrain(Train train) {
       return trainRepsitory.save(train) ;
    }
}
