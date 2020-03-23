package com.zarembski.covid.repository;

import com.zarembski.covid.model.CovidData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CovidDataRepository extends CrudRepository<CovidData, Long> {
    CovidData findFirstByOrderByDateDesc();
}
