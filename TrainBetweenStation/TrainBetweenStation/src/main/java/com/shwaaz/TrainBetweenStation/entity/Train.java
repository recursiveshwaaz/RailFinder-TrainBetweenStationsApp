package com.shwaaz.TrainBetweenStation.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String trainName ;

    private String trainNumber ;

    @OneToMany(mappedBy = "train",cascade = CascadeType.ALL)
    //,fetch = FetchType.LAZY
   // @JsonManagedReference  // mean parent refrence krega schedule ko..
    @JsonBackReference
    // yahi parent manage krega tumhe serialize karne ki jrurat nhi hai,serialize nhi hogi toh train ka data nhi aayega..

    private List<TrainSchedule> scheduleList ;

    public Train(){

    }

    public Train(Long id, String trainName, String trainNumber, List<TrainSchedule> scheduleList) {
        this.id = id;
        this.trainName = trainName;
        this.trainNumber = trainNumber;
        this.scheduleList = scheduleList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public List<TrainSchedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<TrainSchedule> scheduleList) {
        this.scheduleList = scheduleList;
    }
}
