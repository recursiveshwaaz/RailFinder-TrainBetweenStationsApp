package com.shwaaz.TrainBetweenStation.service;

import com.shwaaz.TrainBetweenStation.entity.TrainSchedule;
import com.shwaaz.TrainBetweenStation.repo.TrainScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainSearchService {
    private TrainScheduleRepository trainScheduleRepository ;

    public TrainSearchService(TrainScheduleRepository trainScheduleRepository){
        this.trainScheduleRepository = trainScheduleRepository ;
    }

    public List<TrainSchedule> findTrainByStationCode(String sourceCode, String destinatioCode) {
        return trainScheduleRepository.
                findBySource_StationCodeAndDestination_StationCode(sourceCode,destinatioCode);
    }

    public List<TrainSchedule> findTrainByStationName(String sourceName, String destinatioName) {

        return trainScheduleRepository.
                findBySource_StationNameAndDestination_StationName(sourceName,destinatioName) ;

    }
}
