package com.shwaaz.TrainBetweenStation.repo;

import com.shwaaz.TrainBetweenStation.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainRepsitory extends JpaRepository<Train,Long> {


}
